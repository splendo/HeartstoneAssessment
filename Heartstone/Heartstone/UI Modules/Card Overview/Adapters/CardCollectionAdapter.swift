import UIKit

internal final class CardCollectionAdapter: NSObject {
    private let favoritesManager: FavoritesManager
    private var sets: [HeartStoneCardSet] = []
    
    internal var sectionCount: Int { sets.count }
    internal var didSelect: ((IndexPath) -> Void)?
    
    internal init(favoritesManager: FavoritesManager) {
        self.favoritesManager = favoritesManager
    }
    
    internal func numberOfItems(inSection section: Int) -> Int { setAtSection(section)?.cards.count ?? 0 }
    internal func setAtSection(_ section: Int) -> HeartStoneCardSet? { sets[safe: section] }
    internal func itemAt(_ indexPath: IndexPath) -> HeartStoneCard? { setAtSection(indexPath.section)?.cards[safe: indexPath.item] }
    
    internal func setSets(_ sets: [HeartStoneCardSet]) { self.sets = sets }
    
    internal func softReload(_ collectionView: UICollectionView) {
        collectionView.visibleCells
            .compactMap { $0 as? CardOverviewCell }
            .forEach {
                if let indexPath = collectionView.indexPath(for: $0) {
                    $0.viewModel = viewModel(at: indexPath)
                }
            }
    }
}

// MARK: - CollectionAdapter
extension CardCollectionAdapter: CollectionAdapter {
    internal func configure(_ collectionView: UICollectionView) {
        collectionView.register(cell: CardOverviewCell.self)
        
        collectionView.register(view: CardOverviewSectionHeader.self, for: .header)
        
        collectionView.delegate = self
        collectionView.dataSource = self
    }
}

// MARK: - UICollectionViewDelegate
extension CardCollectionAdapter: UICollectionViewDelegate {
    internal func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        collectionView.deselectItem(at: indexPath, animated: true)
        
        didSelect?(indexPath)
    }
}

// MARK: - UICollectionViewDataSource
extension CardCollectionAdapter: UICollectionViewDataSource {
    private func viewModel(at indexPath: IndexPath) -> CardOverviewCell.ViewModel? {
        guard let item = itemAt(indexPath) else {
            return nil
        }
        
        return CardOverviewCell.ViewModel(title: item.name, imageURL: item.imageURL, isFavorite: favoritesManager.isFavorite(item))
    }
    
    internal func numberOfSections(in collectionView: UICollectionView) -> Int { sectionCount }
    
    internal func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        numberOfItems(inSection: section)
    }
    
    internal func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeReusableCell(for: CardOverviewCell.self, indexPath: indexPath)
        
        cell.viewModel = viewModel(at: indexPath)
        
        return cell
    }
    
    internal func collectionView(_ collectionView: UICollectionView,
                                 viewForSupplementaryElementOfKind kind: String,
                                 at indexPath: IndexPath) -> UICollectionReusableView {
        guard kind == UICollectionView.elementKindSectionHeader else {
            fatalError("Unsupported supplementary view: \(kind)")
        }
                
        let header = collectionView.dequeueReusableSupplementaryView(for: CardOverviewSectionHeader.self, kind: kind, indexPath: indexPath)
        
        if let section = setAtSection(indexPath.section) {
            header.title = section.title
        }
        
        return header
    }
}

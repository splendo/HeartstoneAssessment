import UIKit

internal final class CardCollectionAdapter: NSObject {
    private let favoritesManager: FavoritesManager
    private let dataSource = CardSetDataSource()
    
    internal var didSelect: ((IndexPath) -> Void)?
    internal var sets: [HearthstoneCardSet] {
        get { dataSource.sets }
        set { dataSource.sets = newValue }
    }
    
    internal init(favoritesManager: FavoritesManager) {
        self.favoritesManager = favoritesManager
    }
    
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
        collectionView.register(view: CardOverviewSectionHeader.self, for: .header)
        
        collectionView.register(cell: CardOverviewCell.self)
        
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
        guard let card = dataSource.cardAt(indexPath) else {
            return nil
        }
        
        return CardOverviewCell.ViewModel(title: card.name, imageURL: card.imageURL, isFavorite: favoritesManager.isFavorite(card))
    }
    
    internal func numberOfSections(in collectionView: UICollectionView) -> Int {
        dataSource.sectionCount
    }
    
    internal func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        dataSource.numberOfItems(inSection: section)
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
        
        if let section = dataSource.setAtSection(indexPath.section) {
            header.title = section.title
        }
        
        return header
    }
}

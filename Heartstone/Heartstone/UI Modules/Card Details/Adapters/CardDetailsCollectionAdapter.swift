import UIKit

internal final class CardDetailsCollectionAdapter: NSObject {
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
}

// MARK: - CollectionAdapter
extension CardDetailsCollectionAdapter: CollectionAdapter {
    internal func configure(_ collectionView: UICollectionView) {
        collectionView.register(cell: CardCollectionCell.self)
        
        collectionView.register(view: CardOverviewSectionHeader.self, for: .header)
        
        collectionView.delegate = self
        collectionView.dataSource = self
    }
}

// MARK: - UICollectionViewDelegate
extension CardDetailsCollectionAdapter: UICollectionViewDelegate {
    internal func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        collectionView.deselectItem(at: indexPath, animated: true)
        
        didSelect?(indexPath)
    }
}

// MARK: - UICollectionViewDataSource
extension CardDetailsCollectionAdapter: UICollectionViewDataSource {
    internal func numberOfSections(in collectionView: UICollectionView) -> Int { sectionCount }
    
    internal func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        numberOfItems(inSection: section)
    }
    
    internal func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeReusableCell(for: CardCollectionCell.self, indexPath: indexPath)
        
        guard let card = itemAt(indexPath) else {
            return cell
        }
        
        let isFavorite = { [favoritesManager] () -> Bool in
            favoritesManager.isFavorite(card)
        }
        
        let toggleFavorite = { [favoritesManager] () -> Void in
            if favoritesManager.isFavorite(card) {
                favoritesManager.removeFavorite(card)
            } else {
                favoritesManager.addFavorite(card)
            }
        }
        
        let viewModel = CardCollectionCell.ViewModel(card: card, isFavorite: isFavorite, toggleFavorite: toggleFavorite)
        
        cell.setViewModel(viewModel)
        
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

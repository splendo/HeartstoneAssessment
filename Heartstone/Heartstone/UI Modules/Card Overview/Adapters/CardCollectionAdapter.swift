import UIKit

internal final class CardCollectionAdapter: NSObject {
    private var items: [HeartStoneCard] = []
    
    internal var sectionCount: Int { 0 }
    internal var didSelect: ((IndexPath) -> Void)?
    
    internal func numberOfItems(inSection section: Int) -> Int {
        if section == 0 {
            return items.count
        } else {
            return 0
        }
    }
    
    internal func itemAt(_ indexPath: IndexPath) -> HeartStoneCard? {
        if indexPath.section == 0 {
            return items[safe: indexPath.item]
        } else {
            return nil
        }
    }
    
    internal func setItems(_ items: [HeartStoneCard]) {
        self.items = items
    }
}

// MARK: - CollectionAdapter
extension CardCollectionAdapter: CollectionAdapter {
    internal func configure(_ collectionView: UICollectionView) {
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
        guard let item = itemAt(indexPath) else {
            return nil
        }
        
        return CardOverviewCell.ViewModel(title: item.name, imageURL: item.imageURL)
    }
    
    internal func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        numberOfItems(inSection: section)
    }
    
    internal func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeReusableCell(for: CardOverviewCell.self, indexPath: indexPath)
        
        cell.viewModel = viewModel(at: indexPath)
        
        return cell
    }
}

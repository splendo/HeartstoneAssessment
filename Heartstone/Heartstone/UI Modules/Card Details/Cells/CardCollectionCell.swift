import UIKit

internal final class CardCollectionCell: UICollectionViewCell {
    internal struct ViewModel {
        internal let card: HeartStoneCard
        internal var isFavorite: () -> Bool
        internal var toggleFavorite: () -> Void
    }
    
    private let tableAdapter = CardDetailTableAdapter()
    private let cardView: CardDetailsView
    
    internal override init(frame: CGRect) {
        self.cardView = CardDetailsView(tableAdapter: self.tableAdapter)
        
        super.init(frame: frame)
        
        configureViews()
    }
    
    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    internal func setViewModel(_ viewModel: ViewModel?) {
        cardView.prepareForReuse()
        
        tableAdapter.setViewModel(viewModel)
        cardView.reloadData()
    }
}

// MARK: Configure Views
extension CardCollectionCell {
    private func configureViews() {
        contentView.addSubview(cardView.disableTranslateAutoresizingMask())
        cardView.pinEdgesToSuperview()
    }
}

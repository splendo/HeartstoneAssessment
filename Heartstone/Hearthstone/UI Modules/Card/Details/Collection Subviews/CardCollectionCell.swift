import UIKit

internal final class CardCollectionCell: UICollectionViewCell {
    internal struct ViewModel {
        internal let card: HearthstoneCard
        internal var isFavorite: () -> Bool
        internal var toggleFavorite: () -> Void
    }
    
    private let tableAdapter = CardDetailTableAdapter()
    private let tableView = UITableView()
    
    internal override init(frame: CGRect) {
        super.init(frame: frame)
        
        configureViews()
    }
    
    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    internal func setViewModel(_ viewModel: ViewModel?) {
        tableAdapter.setViewModel(viewModel)
        tableView.reloadData()
    }
    
    internal override func prepareForReuse() {
        super.prepareForReuse()
        
        tableView.contentOffset = CGPoint(x: 0, y: -(tableView.contentInset.top + tableView.adjustedContentInset.top))
    }
}

// MARK: Configure Views
extension CardCollectionCell {
    private func configureViews() {
        contentView.addSubview(tableView.disableTranslateAutoresizingMask())
        
        backgroundView = UIView()
        backgroundView?.backgroundColor = .systemGroupedBackground
        
        configureTableView()
    }
    
    private func configureTableView() {
        tableAdapter.configure(tableView)
        
        tableView.pinEdgesToSuperview()
        tableView.tableFooterView = UIView()
    }
}

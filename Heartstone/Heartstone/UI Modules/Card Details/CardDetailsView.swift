import UIKit

internal final class CardDetailsView: UIView {
    private let tableView = UITableView()
    private let tableAdapter: CardDetailTableAdapter
    
    internal init(tableAdapter: CardDetailTableAdapter) {
        self.tableAdapter = tableAdapter
        
        super.init(frame: UIScreen.main.bounds)
        
        configureViews()
    }
    
    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
// MARK: Configure Views
extension CardDetailsView {
    private func configureViews() {
        addSubview(tableView.disableTranslateAutoresizingMask())
        
        backgroundColor = .systemGroupedBackground
        
        configureTableView()
    }
    
    private func configureTableView() {
        tableAdapter.configure(tableView)
        
        tableView.pinEdgesToSuperview()
        tableView.tableFooterView = UIView()
    }
}

// MARK: Misc
extension CardDetailsView {
    internal func reloadData() {
        tableView.reloadData()
    }
    
    internal func prepareForReuse() {
        tableView.contentOffset = CGPoint(x: 0, y: -(tableView.contentInset.top + tableView.adjustedContentInset.top))
    }
}

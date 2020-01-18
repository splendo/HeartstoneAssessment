import UIKit

extension FilterViewController {
    @objc(FilterViewControllerView) 
    internal final class View: UIView {
        private let tableAdapter: FilterTableAdapter
        private let tableView = UITableView()
        
        internal init(tableAdapter: FilterTableAdapter) {
            self.tableAdapter = tableAdapter
            
            super.init(frame: UIScreen.main.bounds)
            
            configureViews()
        }
        
        internal required init?(coder aDecoder: NSCoder) {
            fatalError("init(coder:) has not been implemented")
        }
    }
}

// MARK: Configure Views
extension FilterViewController.View {
    private func configureViews() {
        addSubview(tableView.disableTranslateAutoresizingMask())
        tableView.pinEdgesToSuperview()
        tableView.tableFooterView = UIView()
        
        tableAdapter.configure(tableView)
    }
}

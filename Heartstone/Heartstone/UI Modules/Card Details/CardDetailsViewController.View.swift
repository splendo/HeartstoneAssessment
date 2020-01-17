import UIKit
import Alamofire

extension CardDetailsViewController {
    @objc(CardDetailsViewControllerView) 
    internal final class View: UIView {
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
}

// MARK: Configure Views
extension CardDetailsViewController.View {
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

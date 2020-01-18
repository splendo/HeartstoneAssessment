import UIKit

internal final class FilterViewController: UIViewController {
    internal typealias Completion = (HeartStoneActiveFilters?) -> Void
    
    private lazy var rootView = View(tableAdapter: tableAdapter)
    private let tableAdapter: FilterTableAdapter
    
    internal var completion: Completion?
        
    internal init(availableFilters: [HeartStoneFilter], activeFilters: HeartStoneActiveFilters) {
        self.tableAdapter = FilterTableAdapter(availableFilters: availableFilters, activeFilters: activeFilters)
        
        super.init(nibName: nil, bundle: nil)
        
        configureNavigationItem()
    }

    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func configureNavigationItem() {
        navigationItem.title = "Filters"
        
        let doneButton = UIBarButtonItem(barButtonSystemItem: .done, target: self, action: #selector(self.tappedDone(with:)))
        let cancelButton = UIBarButtonItem(barButtonSystemItem: .done, target: self, action: #selector(self.tappedCancel(with:)))
        
        navigationItem.rightBarButtonItem = doneButton
        navigationItem.leftBarButtonItem = cancelButton
    }
}

// MARK: Load view
extension FilterViewController {
    internal override func loadView() {
        view = rootView
    }
}

// MARK: User Interaction
extension FilterViewController {
    @objc
    private func tappedDone(with sender: UIBarButtonItem) {
        completion?(tableAdapter.activeFilters)
    }
    
    @objc
    private func tappedCancel(with sender: UIBarButtonItem) {
        completion?(nil)
    }
}

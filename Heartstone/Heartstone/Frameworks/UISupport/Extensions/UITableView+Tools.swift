import UIKit

extension UITableView {
    
    /// Checks if all the sections in the tableview have no items in them
    public var isEmpty: Bool {
        for section in 0..<numberOfSections {
            if numberOfRows(inSection: section) > 0 {
                return false
            }
        }
        
        return true
    }
    
    /// Checks if any section in the tableview has items in it
    public var isNotEmpty: Bool {
        return isEmpty == false
    }
    
    public func setDelegate(_ delegate: UITableViewDelegate?, dataSource: UITableViewDataSource?) {
        self.delegate = delegate
        self.dataSource = dataSource
    }
    
    /**
     * Registers the given cell type to enable dequeuing. The reuse identifier is created internally.
     * This cell can than be dequeued via `dequeueReusableCell(for:indexPath:)`
     */
    public func register<T: UITableViewCell>(cell: T.Type) {
        register(T.self, forCellReuseIdentifier: reuseIndentifier(for: cell))
    }
    
    /**
     * Registers the given UITableViewHeaderFooterView type to enable dequeuing. The reuse identifier is created internally.
     * This view can than be dequeued via `dequeueReusableHeaderFooterView(for:)`
     */
    public func register<T: UITableViewHeaderFooterView>(headerFooterView: T.Type) {
        register(T.self, forHeaderFooterViewReuseIdentifier: reuseIndentifier(for: headerFooterView))
    }
        
    /**
     * dequeues a cell for the given type. The cell needs to be already registered via `register(cell:)`
     * If the type is not registered a fatalError will occur.
     */
    public func dequeueReusableCell<T: UITableViewCell>(for type: T.Type, indexPath: IndexPath) -> T {
        guard let cell = dequeueReusableCell(withIdentifier: reuseIndentifier(for: type), for: indexPath) as? T else {
            fatalError()
        }
        
        return cell
    }
    
    /**
     * dequeues a UITableViewHeaderFooterView for the given type. The view needs to be already registered via `register(headerFooterView:)`
     * If the type is not registered a fatalError will occur.
     */
    public func dequeueReusableHeaderFooterView<T: UITableViewHeaderFooterView>(for type: T.Type) -> T {
        guard let view = dequeueReusableHeaderFooterView(withIdentifier: reuseIndentifier(for: type)) as? T else {
            fatalError()
        }
        
        return view
    }
    
    private func reuseIndentifier<T>(for type: T.Type) -> String {
        return String(describing: type)
    }
}

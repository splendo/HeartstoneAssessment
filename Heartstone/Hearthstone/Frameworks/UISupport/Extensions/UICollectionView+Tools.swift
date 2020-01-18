import UIKit

public enum UICollectionViewSupplementaryViewKind {
    case header, footer, custom(String)
    
    public init(value: String) {
        switch value {
        case UICollectionView.elementKindSectionHeader: self = .header
        case UICollectionView.elementKindSectionFooter: self = .footer
        default: self = .custom(value)
        }
    }
    
    fileprivate var kind: String {
        switch self {
        case .header: return UICollectionView.elementKindSectionHeader
        case .footer: return UICollectionView.elementKindSectionFooter
        case .custom(let kind): return kind
        }
    }
}

extension UICollectionView {
    /// Checks if all the sections in the tableview have no items in them
    public var isEmpty: Bool {
        for section in 0..<numberOfSections {
            if numberOfItems(inSection: section) > 0 {
                return false
            }
        }
        
        return true
    }
    
    /// Checks if any section in the tableview has items in it
    public var isNotEmpty: Bool {
        return isEmpty == false
    }
    
    /// Checks if the given indexPath is valid for this collection view
    public func validIndexPath(_ indexPath: IndexPath?) -> Bool {
        guard let indexPath = indexPath else {
            return false
        }
        
        if indexPath.section >= numberOfSections {
            return false
        }
        
        return indexPath.item < numberOfItems(inSection: indexPath.section)
    }
    /**
     * Registers the given cell type to enable dequeuing. The reuse identifier is created internally.
     * This cell can than be dequeued via `dequeueReusableCell(for:indexPath:)`
     */
    public func register<T: UICollectionViewCell>(cell: T.Type) {
        register(T.self, forCellWithReuseIdentifier: reuseIndentifier(for: cell))
    }
    
    /**
     * Registers the given UICollectionReusableView type to enable dequeuing. The reuse identifier is created internally.
     * This view can than be dequeued via `dequeueReusableSupplementaryView(for:kind:indexPath:)`
     */
    public func register<T: UICollectionReusableView>(view: T.Type, for kind: UICollectionViewSupplementaryViewKind) {
        register(T.self, forSupplementaryViewOfKind: kind.kind, withReuseIdentifier: reuseIndentifier(for: view))
    }
    
    /**
     * dequeues a cell for the given type. The cell needs to be already registered via `register(cell:)`
     * If the type is not registered a fatalError will occur.
     */
    public func dequeReusableCell<T: UICollectionViewCell>(for type: T.Type, indexPath: IndexPath) -> T {
        guard let cell = dequeueReusableCell(withReuseIdentifier: reuseIndentifier(for: type), for: indexPath) as? T else {
            fatalError()
        }
        
        return cell
    }
    
    /**
     * dequeues a UITableViewHeaderFooterView for the given type. The view needs to be already registered via `register(view:for:)`
     * If the type is not registered a fatalError will occur.
     */
    public func dequeueReusableSupplementaryView<T: UICollectionReusableView>(for type: T.Type, kind: UICollectionViewSupplementaryViewKind, indexPath: IndexPath) -> T {
        return dequeueReusableSupplementaryView(for: type, kind: kind.kind, indexPath: indexPath)
    }
    
    /**
     * dequeues a UITableViewHeaderFooterView for the given type. The view needs to be already registered via `register(view:for:)`
     * If the type is not registered a fatalError will occur.
     */
    public func dequeueReusableSupplementaryView<T: UICollectionReusableView>(for type: T.Type, kind: String, indexPath: IndexPath) -> T {
        guard let view = dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: reuseIndentifier(for: type), for: indexPath) as? T else {
            fatalError()
        }
        
        return view
    }
    
    private func reuseIndentifier<T>(for type: T.Type) -> String {
        return String(describing: type)
    }
}

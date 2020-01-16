import UIKit

extension UIView {
    /**
     Pin the view above the given view
     - Parameter view: the view where this view should be pinned above
     - Parameter padding: the padding between the two views
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual.
     - Parameter active: enable the constraints, defaults to true
     */
    @discardableResult
    public func pin(above view: UIView,
                    padding: CGFloat = 0,
                    relation: NSLayoutConstraint.Relation = .equal,
                    active: Bool = true) -> NSLayoutConstraint {
        return bottomAnchor.constraint(to: view.topAnchor,
                                       constant: -padding,
                                       relation: relation).set(active: active)
    }
    
    /**
     Pin the view below the given view
     - Parameter view: the view where this view should be pinned below
     - Parameter padding: the padding between the two views
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual.
     - Parameter active: enable the constraints, defaults to true
     */
    @discardableResult
    public func pin(below view: UIView,
                    padding: CGFloat = 0,
                    relation: NSLayoutConstraint.Relation = .equal,
                    active: Bool = true) -> NSLayoutConstraint {
        return topAnchor.constraint(to: view.bottomAnchor,
                                    constant: padding,
                                    relation: relation).set(active: active)
    }
    
    /**
     Pin the view next to the given view
     - Parameter view: the view where this view should be pinned next to
     - Parameter padding: the padding between the two views
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual.
     - Parameter active: enable the constraints, defaults to true
     */
    @discardableResult
    public func pin(nextTo view: UIView,
                    padding: CGFloat = 0,
                    relation: NSLayoutConstraint.Relation = .equal,
                    active: Bool = true) -> NSLayoutConstraint {
        return leadingAnchor.constraint(to: view.trailingAnchor,
                                        constant: padding,
                                        relation: relation).set(active: active)
    }
    
    /**
     Pin the view before the given view
     - Parameter view: the view where this view should be pinned before
     - Parameter padding: the padding between the two views
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual.
     - Parameter active: enable the constraints, defaults to true
     */
    @discardableResult
    public func pin(before view: UIView,
                    padding: CGFloat = 0,
                    relation: NSLayoutConstraint.Relation = .equal,
                    active: Bool = true) -> NSLayoutConstraint {
        return trailingAnchor.constraint(to: view.leadingAnchor,
                                         constant: -padding,
                                         relation: relation).set(active: active)
    }
}

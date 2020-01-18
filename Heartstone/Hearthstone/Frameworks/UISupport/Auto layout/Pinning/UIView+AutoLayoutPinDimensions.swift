import UIKit

extension UIView {
    
    /**
     Pin the view size to the given size
     - Parameter size: the size the view should be
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual.
     - Parameter active: enable the constraints, defaults to true
     */
    @discardableResult
    public func pin(size: CGSize,
                    relation: NSLayoutConstraint.Relation = .equal,
                    active: Bool = true) -> [NSLayoutConstraint] {
        return [widthAnchor.constraint(toConstant: size.width, relation: relation),
                heightAnchor.constraint(toConstant: size.height, relation: relation)]
            .set(active: active)
    }
    
    /**
     Pin the view size to the given size
     - Parameter singleSize: the size the view should be (width and height are equal)
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual.
     - Parameter active: enable the constraints, defaults to true
     */
    @discardableResult
    public func pin(singleSize: CGFloat,
                    relation: NSLayoutConstraint.Relation = .equal,
                    active: Bool = true) -> [NSLayoutConstraint] {
        return [widthAnchor.constraint(toConstant: singleSize, relation: relation),
                heightAnchor.constraint(toConstant: singleSize, relation: relation)]
            .set(active: active)
    }
    
    /**
     Pin the view width related to the given views width
     - Parameter view: the view where this views width should relate to
     - Parameter multiplier: the multiplier for the width relation
     - Parameter extraWidth: the extra width that should be added to this view
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual.
     - Parameter active: enable the constraints, defaults to true
     */
    @discardableResult
    public func pinWidth(with view: UIView,
                         multiplier: CGFloat = 1,
                         extraWidth: CGFloat = 0,
                         relation: NSLayoutConstraint.Relation = .equal,
                         active: Bool = true) -> NSLayoutConstraint {
        return widthAnchor.constraint(to: view.widthAnchor,
                                      multiplier: multiplier,
                                      constant: extraWidth,
                                      relation: relation).set(active: active)
    }
    
    /**
     Pin the view width to the given constant
     - Parameter width: the width that this view should be
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual.
     - Parameter active: enable the constraints, defaults to true
     */
    @discardableResult
    public func pin(width: CGFloat,
                    relation: NSLayoutConstraint.Relation = .equal,
                    active: Bool = true) -> NSLayoutConstraint {
        return widthAnchor.constraint(toConstant: width, relation: relation).set(active: active)
    }
    
    /**
     Pin the view height related to the given views height
     - Parameter view: the view where this views height should relate to
     - Parameter multiplier: the multiplier for the height relation
     - Parameter extraHeight: the extra height that should be added to this view
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual.
     - Parameter active: enable the constraints, defaults to true
     */
    @discardableResult
    public func pinHeight(with view: UIView,
                          multiplier: CGFloat = 1,
                          extraHeight: CGFloat = 0,
                          relation: NSLayoutConstraint.Relation = .equal,
                          active: Bool = true) -> NSLayoutConstraint {
        return heightAnchor.constraint(to: view.heightAnchor,
                                       multiplier: multiplier,
                                       constant: extraHeight,
                                       relation: relation).set(active: active)
    }
    
    /**
     Pin the view height to the given constant
     - Parameter height: the height that this view should be
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual.
     - Parameter active: enable the constraints, defaults to true
     */
    @discardableResult
    public func pin(height: CGFloat,
                    relation: NSLayoutConstraint.Relation = .equal,
                    active: Bool = true) -> NSLayoutConstraint {
        return heightAnchor.constraint(toConstant: height, relation: relation).set(active: active)
    }
}

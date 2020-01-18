import UIKit

// MARK: - Top
extension UIView {
    /**
     Pins the top anchor of this view to the given views layout area
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint or nil if the superview is not set (this is considered a programming error)
     */
    @discardableResult
    public func pinTopToSuperview(padding: CGFloat = 0,
                                  layoutArea: LayoutAreaType = .default,
                                  relation: NSLayoutConstraint.Relation = .equal,
                                  active: Bool = true) -> NSLayoutConstraint? {
        guard let superview = superview else {
            assertionFailure("Superview is not set")
            return nil
        }
        
        return pinTop(to: superview, padding: padding, layoutArea: layoutArea, relation: relation, active: active)
    }
    
    /**
     Pins the top anchor of this view to the given views layout area
     - Parameter view: the view to pin this view top anchor to
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint
     */
    @discardableResult
    public func pinTop(to view: UIView,
                       padding: CGFloat = 0,
                       layoutArea: LayoutAreaType = .default,
                       relation: NSLayoutConstraint.Relation = .equal,
                       active: Bool = true) -> NSLayoutConstraint {
        return pinTop(to: view.layoutArea(for: layoutArea),
                      padding: padding,
                      relation: relation,
                      active: active)
    }
    
    /**
     Pins the top anchor of this view to the given layout area top anchor
     - Parameter layoutArea: the layout anchors to pin this view top anchor to
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint
     */
    @discardableResult
    public func pinTop(to layoutArea: LayoutArea,
                       padding: CGFloat = 0,
                       relation: NSLayoutConstraint.Relation = .equal,
                       active: Bool = true) -> NSLayoutConstraint {
        return topAnchor.constraint(to: layoutArea.topAnchor, constant: padding, relation: relation).set(active: active)
    }
}

// MARK: - Leading
extension UIView {
    /**
     Pins the leading anchor of this view to the given views layout area
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint or nil if the superview is not set (this is considered a programming error)
     */
    @discardableResult
    public func pinLeadingToSuperview(padding: CGFloat = 0,
                                      layoutArea: LayoutAreaType = .default,
                                      relation: NSLayoutConstraint.Relation = .equal,
                                      active: Bool = true) -> NSLayoutConstraint? {
        guard let superview = superview else {
            assertionFailure("Superview is not set")
            return nil
        }
        
        return pinLeading(to: superview, padding: padding, layoutArea: layoutArea, relation: relation, active: active)
    }
    
    /**
     Pins the leading anchor of this view to the given views layout area
     - Parameter view: the view to pin this view leading anchor to
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint
     */
    @discardableResult
    public func pinLeading(to view: UIView,
                           padding: CGFloat = 0,
                           layoutArea: LayoutAreaType = .default,
                           relation: NSLayoutConstraint.Relation = .equal,
                           active: Bool = true) -> NSLayoutConstraint {
        return pinLeading(to: view.layoutArea(for: layoutArea),
                          padding: padding,
                          relation: relation,
                          active: active)
    }
    
    /**
     Pins the leading anchor of this view to the given layout area leading anchor
     - Parameter layoutArea: the layout anchors to pin this view leading anchor to
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint
     */
    @discardableResult
    public func pinLeading(to layoutArea: LayoutArea,
                           padding: CGFloat = 0,
                           relation: NSLayoutConstraint.Relation = .equal,
                           active: Bool = true) -> NSLayoutConstraint {
        return leadingAnchor.constraint(to: layoutArea.leadingAnchor, constant: padding, relation: relation).set(active: active)
    }
}

// MARK: - Trailing
extension UIView {
    /**
     Pins the trailing anchor of this view to the given views layout area
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`. This values sign is flipped when creating the constraint.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`. The value is flipped wehen creating the constraint.
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint or nil if the superview is not set (this is considered a programming error)
     */
    @discardableResult
    public func pinTrailingToSuperview(padding: CGFloat = 0,
                                       layoutArea: LayoutAreaType = .default,
                                       relation: NSLayoutConstraint.Relation = .equal,
                                       active: Bool = true) -> NSLayoutConstraint? {
        guard let superview = superview else {
            assertionFailure("Superview is not set")
            return nil
        }
        
        return pinTrailing(to: superview, padding: padding, layoutArea: layoutArea, relation: relation, active: active)
    }
    
    /**
     Pins the trailing anchor of this view to the given views layout area
     - Parameter view: the view to pin this view leading anchor to
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`. This values sign is flipped when creating the constraint.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`. The value is flipped wehen creating the constraint.
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint
     */
    @discardableResult
    public func pinTrailing(to view: UIView,
                            padding: CGFloat = 0,
                            layoutArea: LayoutAreaType = .default,
                            relation: NSLayoutConstraint.Relation = .equal,
                            active: Bool = true) -> NSLayoutConstraint {
        return pinTrailing(to: view.layoutArea(for: layoutArea),
                           padding: padding,
                           relation: relation,
                           active: active)
    }
    
    /**
     Pins the leading anchor of this view to the given layout area leading anchor
     - Parameter layoutArea: the layout anchors to pin this view leading anchor to
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`. This values sign is flipped when creating the constraint.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`. The value is flipped wehen creating the constraint.
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint
     */
    @discardableResult
    public func pinTrailing(to layoutArea: LayoutArea,
                            padding: CGFloat = 0,
                            relation: NSLayoutConstraint.Relation = .equal,
                            active: Bool = true) -> NSLayoutConstraint {
        return trailingAnchor.constraint(to: layoutArea.trailingAnchor,
                                         constant: -padding,
                                         relation: relation.flipped).set(active: active)
    }
}

// MARK: - Bottom
extension UIView {
    /**
     Pins the bottom anchor of this view to the given views layout area
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`. This values sign is flipped when creating the constraint.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`. The value is flipped wehen creating the constraint.
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint or nil if the superview is not set (this is considered a programming error)
     */
    @discardableResult
    public func pinBottomToSuperview(padding: CGFloat = 0,
                                     layoutArea: LayoutAreaType = .default,
                                     relation: NSLayoutConstraint.Relation = .equal,
                                     active: Bool = true) -> NSLayoutConstraint? {
        guard let superview = superview else {
            assertionFailure("Superview is not set")
            return nil
        }
        
        return pinBottom(to: superview, padding: padding, layoutArea: layoutArea, relation: relation, active: active)
    }
    
    /**
     Pins the bottom anchor of this view to the given views layout area
     - Parameter view: the view to pin this view bottom anchor to
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`. This values sign is flipped when creating the constraint.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`. The value is flipped wehen creating the constraint.
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint
     */
    @discardableResult
    public func pinBottom(to view: UIView,
                          padding: CGFloat = 0,
                          layoutArea: LayoutAreaType = .default,
                          relation: NSLayoutConstraint.Relation = .equal,
                          active: Bool = true) -> NSLayoutConstraint {
        return pinBottom(to: view.layoutArea(for: layoutArea),
                         padding: padding,
                         relation: relation,
                         active: active)
    }
    
    /**
     Pins the leading anchor of this view to the given layout area leading anchor
     - Parameter layoutArea: the layout anchors to pin this view bottom anchor to
     - Parameter padding: the padding between the two layout anchors. Defaults to `0`. This values sign is flipped when creating the constraint.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Defaults to `.equal`. The value is flipped wehen creating the constraint.
     - Parameter active: enable the constraints. Defaults to `true`
     - Returns: the created constraint
     */
    @discardableResult
    public func pinBottom(to layoutArea: LayoutArea,
                          padding: CGFloat = 0,
                          relation: NSLayoutConstraint.Relation = .equal,
                          active: Bool = true) -> NSLayoutConstraint {
        return bottomAnchor.constraint(to: layoutArea.bottomAnchor,
                                       constant: -padding,
                                       relation: relation.flipped).set(active: active)
    }
}

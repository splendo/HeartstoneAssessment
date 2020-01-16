import UIKit

extension UIView {
    /**
     Pin the edges view to the given view.
     - Parameter view: the view to pin this view edges to
     - Parameter padding: the padding of the edges. The bottom and right (trailing) values will be prefixed with `-`. Defaults to .zero.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter excludeEdges: the edges that should not be pinned. Defaults to .none.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. The value for trailing and bottom will be flipped
     - Parameter active: enable the constraints, defaults to true
     
     - Returns: an array with the created constraints
     */
    @discardableResult
    public func pinEdges(to view: UIView,
                         padding: UIEdgeInsets = .zero,
                         layoutArea: LayoutAreaType = .default,
                         excludeEdges: Edge = .none,
                         relation: NSLayoutConstraint.Relation = .equal,
                         active: Bool = true) -> [NSLayoutConstraint] {
        let relations = NSLayoutConstraint.RelationsAround(top: relation,
                                                           leading: relation,
                                                           trailing: relation.flipped,
                                                           bottom: relation.flipped)
        return pinEdges(to: view.layoutArea(for: layoutArea),
                        padding: padding,
                        excludeEdges: excludeEdges,
                        relations: relations,
                        active: active)
    }
    
    /**
     Pin the edges view to the given view.
     - Parameter view: the view to pin this view edges to
     - Parameter padding: the padding of the edges. The bottom and right (trailing) values will be prefixed with `-`. Defaults to .zero.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter excludeEdges: the edges that should not be pinned. Defaults to .none.
     - Parameter relations: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. The value for trailing and bottom will **not** be flipped.
     The `relations` do not have a default due to signature conflicts that will arise with the functions that have `relation` as input parameter.
     - Parameter active: enable the constraints, defaults to true
     
     - Returns: an array with the created constraints
     */
    @discardableResult
    public func pinEdges(to view: UIView,
                         padding: UIEdgeInsets = .zero,
                         layoutArea: LayoutAreaType = .default,
                         excludeEdges: Edge = .none,
                         relations: NSLayoutConstraint.RelationsAround,
                         active: Bool = true) -> [NSLayoutConstraint] {
        return pinEdges(to: view.layoutArea(for: layoutArea),
                        padding: padding,
                        excludeEdges: excludeEdges,
                        relations: relations,
                        active: active)
    }
    
    /**
     Pin the edges of this views to the super view
     - Parameter padding: the padding of the edges. The bottom and right (trailing) values will be prefixed with `-`. Defaults to .zero.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter excludeEdges: the edges that should not be pinned. Defaults to .none.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. The value for trailing and bottom will be flipped
     - Parameter active: enable the constraints, defaults to true
     
     - Returns: an array with the created constraints
     */
    @discardableResult
    public func pinEdgesToSuperview(padding: UIEdgeInsets = .zero,
                                    layoutArea: LayoutAreaType = .default,
                                    excludeEdges: Edge = .none,
                                    relation: NSLayoutConstraint.Relation = .equal,
                                    active: Bool = true) -> [NSLayoutConstraint] {
        let relations = NSLayoutConstraint.RelationsAround(top: relation,
                                                           leading: relation,
                                                           trailing: relation.flipped,
                                                           bottom: relation.flipped)
        
        return pinEdgesToSuperview(padding: padding,
                                   layoutArea: layoutArea,
                                   excludeEdges: excludeEdges,
                                   relations: relations,
                                   active: active)
    }
    
    /**
     Pin the edges of this views to the super view
     - Parameter padding: the padding of the edges. The bottom and right (trailing) values will be prefixed with `-`. Defaults to .zero.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter excludeEdges: the edges that should not be pinned. Defaults to .none.
     - Parameter relations: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. The value for trailing and bottom will **not** be flipped.
     The `relations` do not have a default due to signature conflicts that will arise with the functions that have `relation` as input parameter.
     - Parameter active: enable the constraints, defaults to true
     
     - Returns: an array with the created constraints
     */
    @discardableResult
    public func pinEdgesToSuperview(padding: UIEdgeInsets = .zero,
                                    layoutArea: LayoutAreaType = .default,
                                    excludeEdges: Edge = .none,
                                    relations: NSLayoutConstraint.RelationsAround,
                                    active: Bool = true) -> [NSLayoutConstraint] {
        guard let superview = superview else {
            assertionFailure("Superview is not set")
            return []
        }
        
        return pinEdges(to: superview,
                        padding: padding,
                        layoutArea: layoutArea,
                        excludeEdges: excludeEdges,
                        relations: relations,
                        active: active)
    }
    
    /**
     Pins the view vertical in the superview
     - Parameter padding: the padding of the edges. Defaults to 0.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. The value for bottom will be flipped
     - Parameter active: enable the constraints, defaults to true
     
     - Returns: an array with the created constraints
     */
    @discardableResult
    public func pinEdgesVerticalToSuperview(padding: CGFloat = 0,
                                            layoutArea: LayoutAreaType = .default,
                                            relation: NSLayoutConstraint.Relation = .equal,
                                            active: Bool = true) -> [NSLayoutConstraint] {
        guard let superview = superview else {
            assertionFailure("Superview is not set")
            return []
        }
        
        return pinEdgesVertical(to: superview,
                                padding: padding,
                                layoutArea: layoutArea,
                                relation: relation,
                                active: active)
    }
    
    /**
     Pins the view vertical in the given view
     - Parameter view: the view to pin this view vertical edges to
     - Parameter padding: the padding of the edges. Defaults to 0.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. The value for bottom will be flipped
     - Parameter active: enable the constraints, defaults to true
     
     - Returns: an array with the created constraints
     */
    @discardableResult
    public func pinEdgesVertical(to view: UIView,
                                 padding: CGFloat = 0,
                                 layoutArea: LayoutAreaType = .default,
                                 relation: NSLayoutConstraint.Relation = .equal,
                                 active: Bool = true) -> [NSLayoutConstraint] {
        return pinEdges(to: view.layoutArea(for: layoutArea),
                        padding: UIEdgeInsets(top: padding, left: 0, bottom: padding, right: 0),
                        excludeEdges: .horizontal,
                        relation: relation,
                        active: active)
    }
    
    /**
     Pins the view horizontal in the superview
     - Parameter padding: the padding of the edges. Defaults to 0.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. The value for trailing will be flipped
     - Parameter active: enable the constraints, defaults to true
     
     - Returns: an array with the created constraints
     */
    @discardableResult
    public func pinEdgesHorizontalToSuperview(padding: CGFloat = 0,
                                              layoutArea: LayoutAreaType = .default,
                                              relation: NSLayoutConstraint.Relation = .equal,
                                              active: Bool = true) -> [NSLayoutConstraint] {
        guard let superview = superview else {
            assertionFailure("Superview is not set")
            return []
        }
        
        return pinEdgesHorizontal(to: superview,
                                  padding: padding,
                                  layoutArea: layoutArea,
                                  relation: relation,
                                  active: active)
    }
    
    /**
     Pins the view horizontal in the given view
     - Parameter view: the view to pin this view horizontal edges to
     - Parameter padding: the padding of the edges. Defaults to 0.
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. The value for trailing will be flipped
     - Parameter active: enable the constraints, defaults to true
     
     - Returns: an array with the created constraints
     */
    @discardableResult
    public func pinEdgesHorizontal(to view: UIView,
                                   padding: CGFloat = 0,
                                   layoutArea: LayoutAreaType = .default,
                                   relation: NSLayoutConstraint.Relation = .equal,
                                   active: Bool = true) -> [NSLayoutConstraint] {
        return pinEdges(to: view.layoutArea(for: layoutArea),
                        padding: UIEdgeInsets(top: 0, left: padding, bottom: 0, right: padding),
                        excludeEdges: .vertical,
                        relation: relation,
                        active: active)
    }
    
    /**
     Pin the edges view to the given surrounding anchors.
     - Parameter layoutArea: the layout anchors to pin this view edges to
     - Parameter padding: the padding of the edges. The bottom and right (trailing) values will be prefixed with `-`. Defaults to .zero.
     - Parameter excludeEdges: the edges that should not be pinned. Defaults to .none.
     - Parameter relation: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. The values for bottom and trailing will be flipped
     - Parameter active: enable the constraints, defaults to true
     
     - Returns: an array with the created constraints
     */
    @discardableResult
    public func pinEdges(to layoutArea: LayoutArea,
                         padding: UIEdgeInsets = .zero,
                         excludeEdges: Edge = .none,
                         relation: NSLayoutConstraint.Relation = .equal,
                         active: Bool = true) -> [NSLayoutConstraint] {
        let relations = NSLayoutConstraint.RelationsAround(top: relation,
                                                           leading: relation,
                                                           trailing: relation.flipped,
                                                           bottom: relation.flipped)
        
        return pinEdges(to: layoutArea,
                        padding: padding,
                        excludeEdges: excludeEdges,
                        relations: relations,
                        active: active)
    }
    
    /**
     Pin the edges view to the given surrounding anchors.
     - Parameter layoutArea: the layout anchors to pin this view edges to
     - Parameter padding: the padding of the edges. The bottom and right (trailing) values will be prefixed with `-`. Defaults to .zero.
     - Parameter excludeEdges: the edges that should not be pinned. Defaults to .none.
     - Parameter relations: constraints should be equal, lessThanOrEqual or greaterThanOrEqual. Trailing and bottom will **not** be flipped.
     The `relations` do not have a default due to signature conflicts that will arise with the functions that have `relation` as input parameter.
     - Parameter active: enable the constraints, defaults to true
     
     - Returns: an array with the created constraints
     */
    @discardableResult
    public func pinEdges(to layoutArea: LayoutArea,
                         padding: UIEdgeInsets = .zero,
                         excludeEdges: Edge = .none,
                         relations: NSLayoutConstraint.RelationsAround,
                         active: Bool = true) -> [NSLayoutConstraint] {
        var constraints: [NSLayoutConstraint] = []
        
        if excludeEdges.contains(.top) == false {
            let topConstraint = topAnchor.constraint(to: layoutArea.topAnchor,
                                                     constant: padding.top,
                                                     relation: relations.top)
            constraints.append(topConstraint)
        }
        
        if excludeEdges.contains(.leading) == false {
            let leadingConstraint = leadingAnchor.constraint(to: layoutArea.leadingAnchor,
                                                             constant: padding.left,
                                                             relation: relations.leading)
            constraints.append(leadingConstraint)
        }
        
        if excludeEdges.contains(.bottom) == false {
            let bottomConstraint = bottomAnchor.constraint(to: layoutArea.bottomAnchor,
                                                           constant: -padding.bottom,
                                                           relation: relations.bottom)
            constraints.append(bottomConstraint)
        }
        
        if excludeEdges.contains(.trailing) == false {
            let trailingConstraint = trailingAnchor.constraint(to: layoutArea.trailingAnchor,
                                                               constant: -padding.right,
                                                               relation: relations.trailing)
            constraints.append(trailingConstraint)
        }
        
        return constraints.set(active: active)
    }
}

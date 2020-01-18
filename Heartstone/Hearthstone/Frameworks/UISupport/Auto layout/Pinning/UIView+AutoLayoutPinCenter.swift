import UIKit

// MARK: - Complete center
extension UIView {
    /**
     Pin the view center to its superview
     - Parameter offset: the offset from the superview center
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter active: enable the constraints, defaults to true
     - Returns: the created constraints or an empty array if the superview is not set (this is considered a programming error)
     */
    @discardableResult
    public func pinCenterToSuperview(offset: CGPoint = .zero,
                                     layoutArea: LayoutAreaType = .default,
                                     active: Bool = true) -> [NSLayoutConstraint] {
        guard let superView = superview else {
            assertionFailure("Superview is not set")
            return []
        }
        
        return pinCenter(in: superView,
                         offset: offset,
                         layoutArea: layoutArea,
                         active: active)
    }
    
    /**
     Pin the view center to the given views center
     - Parameter view: the view to which this views center should relate to
     - Parameter offset: the offset from the given views center
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter active: enable the constraints, defaults to true
     - Returns: the created constraints
     */
    @discardableResult
    public func pinCenter(in view: UIView,
                          offset: CGPoint = .zero,
                          layoutArea: LayoutAreaType = .default,
                          active: Bool = true) -> [NSLayoutConstraint] {
        return pinCenter(in: view.layoutArea(for: layoutArea),
                         offset: offset,
                         active: active)
    }
    
    /**
     Pin the view center to the given layout area center
     - Parameter view: the view to which this views center should relate to
     - Parameter offset: the offset from the given views center
     - Parameter active: enable the constraints, defaults to true
     - Returns: the created constraints
     */
    @discardableResult
    public func pinCenter(in layoutArea: LayoutArea,
                          offset: CGPoint = .zero,
                          active: Bool = true) -> [NSLayoutConstraint] {
        return [centerXAnchor.constraint(equalTo: layoutArea.centerXAnchor, constant: offset.x),
                centerYAnchor.constraint(equalTo: layoutArea.centerYAnchor, constant: offset.y)]
            .set(active: active)
    }
}

// MARK: Horizontal center
extension UIView {
    /**
     Pin the view horizontal center to its superview horizontal center
     - Parameter offset: the offset from the given views center, positive will move the view to the right of the center and negative will move the view to the left of the center (in LTR)
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter active: enable the constraints, defaults to true
     - Returns: the created constraint or nil if the superview is not set (this is considered a programming error)
     */
    @discardableResult
    public func pinCenterHorizontalToSuperview(offset: CGFloat = 0,
                                               layoutArea: LayoutAreaType = .default,
                                               active: Bool = true) -> NSLayoutConstraint? {
        guard let superView = superview else {
            assertionFailure("Superview is not set")
            return nil
        }
        
        return pinCenterHorizontal(in: superView,
                                   offset: offset,
                                   layoutArea: layoutArea,
                                   active: active)
    }
    
    /**
     Pin the view horizontal center to the given views horizontal center
     - Parameter view: the view to which this views horizontal center should relate to
     - Parameter offset: the offset from the given views center, positive will move the view to the right of the center and negative will move the view to the left of the center (in LTR)
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter active: enable the constraints, defaults to true
     - Returns: the created constraint
     */
    @discardableResult
    public func pinCenterHorizontal(in view: UIView,
                                    offset: CGFloat = 0,
                                    layoutArea: LayoutAreaType = .default,
                                    active: Bool = true) -> NSLayoutConstraint {
        return pinCenterHorizontal(in: view.layoutArea(for: layoutArea),
                                   offset: offset,
                                   active: active)
    }
    
    /**
     Pin the view horizontal center to the given layout area horizontal center
     - Parameter layoutArea: the layout area to which this views horizontal center should relate to
     - Parameter offset: the offset from the given views center, positive will move the view to the right of the center and negative will move the view to the left of the center (in LTR)
     - Parameter active: enable the constraints, defaults to true
     - Returns: the created constraint
     */
    @discardableResult
    public func pinCenterHorizontal(in layoutArea: LayoutArea,
                                    offset: CGFloat = 0,
                                    active: Bool = true) -> NSLayoutConstraint {
        return centerXAnchor.constraint(equalTo: layoutArea.centerXAnchor, constant: offset).set(active: active)
    }
}

// MARK: Vertical center
extension UIView {
    /**
     Pin the view vertical center to its superview vertical center
     - Parameter offset: the offset from the given views center, positive will move the view down of the center and negative will move the view up from of the center
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter active: enable the constraints, defaults to true
     - Returns: the created constraint or nil if the superview is not set (this is considered a programming error)
     */
    @discardableResult
    public func pinCenterVerticaltalToSuperview(offset: CGFloat = 0,
                                                layoutArea: LayoutAreaType = .default,
                                                active: Bool = true) -> NSLayoutConstraint? {
        guard let superView = superview else {
            assertionFailure("Superview is not set")
            return nil
        }
        
        return pinCenterVertical(in: superView,
                                 offset: offset,
                                 layoutArea: layoutArea,
                                 active: active)
    }
    
    /**
     Pin the view vertical center to the given views vertical center
     - Parameter view: the view to which this views vertical center should relate to
     - Parameter offset: the offset from the given views center, positive will move the view down of the center and negative will move the view up from of the center
     - Parameter layoutArea: use the views default anchors, the safe area anchors or the layout margin anchors. Defaults to `.default`.
     - Parameter active: enable the constraints, defaults to true
     - Returns: the created constraint
     */
    @discardableResult
    public func pinCenterVertical(in view: UIView,
                                  offset: CGFloat = 0,
                                  layoutArea: LayoutAreaType = .default,
                                  active: Bool = true) -> NSLayoutConstraint {
        return pinCenterVertical(in: view.layoutArea(for: layoutArea),
                                 offset: offset,
                                 active: active)
    }
    
    /**
     Pin the view vertical center to the given layout area vertical center
     - Parameter layoutArea: the layout area to which this views vertical center should relate to
     - Parameter offset: the offset from the given views center, positive will move the view down of the center and negative will move the view up from of the center
     - Parameter active: enable the constraints, defaults to true
     - Returns: the created constraint
     */
    @discardableResult
    public func pinCenterVertical(in layoutArea: LayoutArea,
                                  offset: CGFloat = 0,
                                  active: Bool = true) -> NSLayoutConstraint {
        return centerYAnchor.constraint(equalTo: layoutArea.centerYAnchor, constant: offset).set(active: active)
    }
}

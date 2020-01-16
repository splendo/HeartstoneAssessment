import UIKit

extension Array where Element == NSLayoutConstraint {
    /**
     Activates all the constraints
     */
    @discardableResult
    public func activateConstraints() -> [Element] {
        NSLayoutConstraint.activate(self)
        return self
    }
    
    /**
     Deactivates all the constraints
     */
    @discardableResult
    public func deactivateConstraints() -> [Element] {
        NSLayoutConstraint.deactivate(self)
        return self
    }
    
    /**
     Set all the constraints to active or not. Returns the instance so multiple function calls can be chained
     - Parameter active: if the constraint should be active or  not
     - Returns: self
     */
    @discardableResult
    public func set(active: Bool) -> [Element] {
        if active {
            return activateConstraints()
        } else {
            return deactivateConstraints()
        }
    }
    
    /**
     Set the debug identifiers for all the constraints
     */
    @discardableResult
    public func set(autoLayoutIdentifiers identifier: String) -> [Element] {
        forEach { $0.identifier = identifier }
        return self
    }
    
    /**
     Set the priority for all the constraints
     */
    @discardableResult
    public func set(autoLayoutPriority priority: UILayoutPriority) -> [Element] {
        forEach { $0.priority = priority }
        return self
    }
    
    /**
     Set the priority for all the constraints
     */
    @discardableResult
    public func set(autoLayoutPriority priority: Float) -> [Element] {
        forEach { $0.priority = UILayoutPriority(priority) }
        return self
    }
    
    /**
     Filters the array for constraints relating to the given layout anchor
     - Parameter anchor: the anchor which the returned constrains should contain
     - Returns: the current array filtered so that only constraints are included that contain the given anchor
     */
    public func constraints(containing anchor: NSLayoutAnchor<AnyObject>) -> [Element] {
        if #available(iOS 10.0, *) {
            return filter { return $0.firstAnchor == anchor || $0.secondAnchor == anchor }
        } else {
            return filter { return $0.firstItem === anchor || $0.secondItem === anchor }
        }
    }
    
    /**
     Filters the array for constraints relating to the given layout anchor
     - Parameter anchor: the anchor which the returned constrains should contain
     - Returns: the current array filtered so that only constraints are included that contain the given anchor
     */
    public func constraints(containing anchor: NSLayoutYAxisAnchor) -> [Element] {
        if #available(iOS 10.0, *) {
            return filter { return $0.firstAnchor == anchor || $0.secondAnchor == anchor }
        } else {
            return filter { return $0.firstItem === anchor || $0.secondItem === anchor }
        }
    }
    
    /**
     Filters the array for constraints relating to the given layout anchor
     - Parameter anchor: the anchor which the returned constrains should contain
     - Returns: the current array filtered so that only constraints are included that contain the given anchor
     */
    public func constraints(containing anchor: NSLayoutXAxisAnchor) -> [Element] {
        if #available(iOS 10.0, *) {
            return filter { return $0.firstAnchor == anchor || $0.secondAnchor == anchor }
        } else {
            return filter { return $0.firstItem === anchor || $0.secondItem === anchor }
        }
    }
    
    /**
     Filters the array for constraints relating to the given layout anchor
     - Parameter anchor: the anchor which the returned constrains should contain
     - Returns: the current array filtered so that only constraints are included that contain the given anchor
     */
    public func constraints(containing anchor: NSLayoutDimension) -> [Element] {
        if #available(iOS 10.0, *) {
            return filter { return $0.firstAnchor == anchor || $0.secondAnchor == anchor }
        } else {
            return filter { return $0.firstItem === anchor || $0.secondItem === anchor }
        }
    }
}

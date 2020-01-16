import UIKit

extension NSLayoutConstraint {
    /**
     Activates the current constraint. Returns the instance so multiple function call can be chained
     - Returns: self
     */
    @discardableResult
    public func activate() -> Self {
        isActive = true
        return self
    }
    
    /**
     Deactivates the current constraint. Returns the instance so multiple function call can be chained
     - Returns: self
     */
    @discardableResult
    public func deactivate() -> Self {
        isActive = false
        return self
    }
    
    /**
     Set the current constraint to active or not. Returns the instance so multiple function call can be chained
     - Parameter active: if the constraint should be active or  not
     - Returns: self
     */
    @discardableResult
    public func set(active: Bool) -> Self {
        isActive = active
        return self
    }
    
    /**
     Set the current constraint identifier. Returns the instance so multiple function call can be chained
     - Parameter identifier: the identifier for the constraint
     - Returns: self
     */
    @discardableResult
    public func set(identifier: String) -> Self {
        self.identifier = identifier
        
        return self
    }
    
    /**
     Set the current constraint priority. Returns the instance so multiple function call can be chained
     - Parameter priority: the priority for the constraint
     - Returns: self
     */
    @discardableResult
    public func set(priority: UILayoutPriority) -> Self {
        self.priority = priority
        return self
    }
    
    /**
     Set the current constraint priority. Returns the instance so multiple function call can be chained
     - Parameter priority: the priority for the constraint
     - Returns: self
     */
    @discardableResult
    public func set(priority: Float) -> Self {
        return set(priority: UILayoutPriority(priority))
    }
}

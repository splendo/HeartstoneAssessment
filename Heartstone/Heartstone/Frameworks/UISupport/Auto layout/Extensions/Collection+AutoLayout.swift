import UIKit

extension Collection where Element == UIView {
    /**
     sets the property `translatesAutoresizingMaskIntoConstraints` of all views in the array to false
     */
    @discardableResult
    public func disableTranslateAutoresizingMask() -> Self {
        forEach { $0.translatesAutoresizingMaskIntoConstraints = false }
        
        return self
    }
    
    /**
     Adds all the views in the collection to the given view
     - Parameter view: the view in which the views will be added
     */
    @discardableResult
    public func add(to view: UIView) -> Self {
        forEach(view.addSubview)
        
        return self
    }
    
    /**
     Sets the content hugging priority to the views in the collection
     - Parameter priority: the priority
     - Parameter axis: the axis
     */
    @discardableResult
    public func setContentHuggingPriority(_ priority: UILayoutPriority, for axis: NSLayoutConstraint.Axis) -> Self {
        forEach { $0.setContentHuggingPriority(priority, for: axis) }
        
        return self
    }
    
    /**
     Sets the content compression resistance priority to the views in the collection
     - Parameter priority: the priority
     - Parameter axis: the axis
     */
    @discardableResult
    public func setContentCompressionResistancePriority(_ priority: UILayoutPriority, for axis: NSLayoutConstraint.Axis) -> Self {
        forEach { $0.setContentCompressionResistancePriority(priority, for: axis) }
        
        return self
    }
}

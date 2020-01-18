import UIKit

extension UIView {
    /**
     Sets `translatesAutoresizingMaskIntoConstraints` to false
     */
    @discardableResult
    public func disableTranslateAutoresizingMask() -> Self {
        translatesAutoresizingMaskIntoConstraints = false
        
        return self
    }
}

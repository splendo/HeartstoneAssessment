import UIKit

extension NSLayoutConstraint.Relation {
    /**
     Flips the value, equal will stay equal, greater will become less and less will become greater
     */
    public var flipped: NSLayoutConstraint.Relation {
        switch self {
        case .lessThanOrEqual: return .greaterThanOrEqual
        case .equal: return .equal
        case .greaterThanOrEqual: return .lessThanOrEqual
        @unknown default:
            assertionFailure("Unknown case")
            
            return .equal
        }
    }
}

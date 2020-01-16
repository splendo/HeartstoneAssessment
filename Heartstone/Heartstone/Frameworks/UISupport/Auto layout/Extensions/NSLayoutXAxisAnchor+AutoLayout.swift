import UIKit

extension NSLayoutXAxisAnchor {
    /**
     Convenience functions to create constraints
     
     - Parameter anchor: the anchor to which this anchor should create a constraint to
     - Parameter constant: the additional constant added to the relatino between the two anchors
     - Parameter relation: the relation between the two anchors
     */
    public func constraint(to anchor: NSLayoutXAxisAnchor,
                           constant: CGFloat = 0,
                           relation: NSLayoutConstraint.Relation) -> NSLayoutConstraint {
        switch relation {
        case .lessThanOrEqual:
            return constraint(lessThanOrEqualTo: anchor, constant: constant)
        case .equal:
            return constraint(equalTo: anchor, constant: constant)
        case .greaterThanOrEqual:
            return constraint(greaterThanOrEqualTo: anchor, constant: constant)
        @unknown default:
            assertionFailure("Unknown case")
            
            return constraint(equalTo: anchor, constant: constant)
        }
    }
}

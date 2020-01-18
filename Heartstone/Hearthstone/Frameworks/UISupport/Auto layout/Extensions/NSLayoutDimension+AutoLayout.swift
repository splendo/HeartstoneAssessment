import UIKit

extension NSLayoutDimension {
    /**
     Convenience functions to create constraints
     
     - Parameter dimension: the dimension to which this dimension should create a constraint to
     - Parameter multiplier: The multiplier to use between the given dimension and this anchor
     - Parameter constant: the additional constant added to the relation between the two anchors
     - Parameter relation: the relation between the two anchors
     */
    public func constraint(to dimension: NSLayoutDimension,
                           multiplier: CGFloat = 1,
                           constant: CGFloat = 0,
                           relation: NSLayoutConstraint.Relation) -> NSLayoutConstraint {
        switch relation {
        case .lessThanOrEqual:
            return constraint(lessThanOrEqualTo: dimension,
                              multiplier: multiplier,
                              constant: constant)
        case .equal:
            return constraint(equalTo: dimension,
                              multiplier: multiplier,
                              constant: constant)
        case .greaterThanOrEqual:
            return constraint(greaterThanOrEqualTo: dimension,
                              multiplier: multiplier,
                              constant: constant)
        @unknown default:
            assertionFailure("Unknown case")
            
            return constraint(equalTo: dimension,
                              multiplier: multiplier,
                              constant: constant)
        }
    }
    
    /**
     Convenience functions to create constraints
     
     - Parameter constant: the constant used for the constraint
     - Parameter relation: the relation between the two anchors
     */
    public func constraint(toConstant constant: CGFloat,
                           relation: NSLayoutConstraint.Relation) -> NSLayoutConstraint {
        switch relation {
        case .lessThanOrEqual:
            return constraint(lessThanOrEqualToConstant: constant)
        case .equal:
            return constraint(equalToConstant: constant)
        case .greaterThanOrEqual:
            return constraint(greaterThanOrEqualToConstant: constant)
        @unknown default:
            assertionFailure("Unknown case")
            
            return constraint(equalToConstant: constant)
        }
    }
}

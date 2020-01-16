import UIKit

extension NSLayoutConstraint {
    /**
     Helper class to easily provide a set of constraints that should be applied to functions that pins a view to multiple edges at once but some relations should be different
     */
    public struct RelationsAround {
        public static let allLessThanOrEqual = RelationsAround(value: .lessThanOrEqual)
        public static let allEqual = RelationsAround(value: .equal)
        public static let allGreaterThanOrEqual = RelationsAround(value: .greaterThanOrEqual)
        
        public let top: NSLayoutConstraint.Relation
        public let leading: NSLayoutConstraint.Relation
        public let trailing: NSLayoutConstraint.Relation
        public let bottom: NSLayoutConstraint.Relation
        
        /**
         Inits with all the relations set to the provided value
         - Parameter value: the restrction to use
         */
        public init(value: NSLayoutConstraint.Relation) {
            self.top = value
            self.leading = value
            self.trailing = value
            self.bottom = value
        }
        
        /**
         Inits with all the relations set to the provided values
         */
        public init(top: NSLayoutConstraint.Relation = .equal,
                    leading: NSLayoutConstraint.Relation = .equal,
                    trailing: NSLayoutConstraint.Relation = .equal,
                    bottom: NSLayoutConstraint.Relation = .equal) {
            self.top = top
            self.leading = leading
            self.trailing = trailing
            self.bottom = bottom
        }
    }
}

// MARK: ExpressibleByDictionaryLiteral
extension NSLayoutConstraint.RelationsAround: ExpressibleByDictionaryLiteral {
    public enum Attribute: String, Hashable {
        case top, bottom, leading, trailing
    }
    
    /**
     Convenience init that uses a dictionary to create the restritions. If no value is provided it defaults to `.equal`.
     example:
     `let relations: NSLayoutConstraint.ConstraintRelationsAround = [.top: .lessThanOrEqual]
     This will create the relations set all to `.equal` except the top which will be `.lessThanOrEqual`
     */
    public init(dictionaryLiteral elements: (Attribute, NSLayoutConstraint.Relation)...) {
        var top: NSLayoutConstraint.Relation = .equal
        var bottom: NSLayoutConstraint.Relation = .equal
        var leading: NSLayoutConstraint.Relation = .equal
        var trailing: NSLayoutConstraint.Relation = .equal
        
        elements.forEach { (attribute, value) in
            switch attribute {
            case .top: top = value
            case .bottom: bottom = value
            case .leading: leading = value
            case .trailing: trailing = value
            }
        }
        
        self.top = top
        self.bottom = bottom
        self.leading = leading
        self.trailing = trailing
    }
}

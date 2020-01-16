import UIKit

extension UIView {
    /**
     Enum to identify edges of a view.
     This is used in example to exclude edges.
     I.e. `pinToSuperView(exclude: .top)`
     */
    public struct Edge: OptionSet {
        public let rawValue: Int
        
        public static let top = Edge(rawValue: 1 << 0)
        public static let leading = Edge(rawValue: 1 << 1)
        public static let bottom = Edge(rawValue: 1 << 2)
        public static let trailing = Edge(rawValue: 1 << 3)
        
        public static let vertical: Edge = [.top, .bottom]
        public static let horizontal: Edge = [.leading, .trailing]
        public static let all: Edge = [.top, .leading, .bottom, .trailing]
        public static let none: Edge = []
        
        public init(rawValue: Int) {
            self.rawValue = rawValue
        }
    }
}

import UIKit

extension UIEdgeInsets {
    public var width: CGFloat { return left + right }
    public var height: CGFloat { return top + bottom }
    
    public init(horizontal: CGFloat = 0, vertical: CGFloat = 0) {
        self.init(top: vertical, left: horizontal, bottom: vertical, right: horizontal)
    }
    
    public init(all: CGFloat) {
        self.init(top: all, left: all, bottom: all, right: all)
    }
    
    public var vertical: CGFloat { return top + bottom }
    
    public var horizontal: CGFloat { return left + right }
    
    public var topLeft: CGPoint { return CGPoint(x: left, y: top) }
}

public func + (lhs: UIEdgeInsets, rhs: UIEdgeInsets) -> UIEdgeInsets {
    return UIEdgeInsets(top: lhs.top + rhs.top,
                        left: lhs.left + rhs.left,
                        bottom: lhs.bottom + rhs.bottom,
                        right: lhs.right + rhs.right)
}

public func - (lhs: UIEdgeInsets, rhs: UIEdgeInsets) -> UIEdgeInsets {
    return UIEdgeInsets(top: lhs.top - rhs.top,
                        left: lhs.left - rhs.left,
                        bottom: lhs.bottom - rhs.bottom,
                        right: lhs.right - rhs.right)
}

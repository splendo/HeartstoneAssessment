import UIKit

extension NSDirectionalEdgeInsets {
    public var width: CGFloat { leading + trailing }
    public var height: CGFloat { top + bottom }
    
    public init(horizontal: CGFloat = 0, vertical: CGFloat = 0) {
        self.init(top: vertical, leading: horizontal, bottom: vertical, trailing: horizontal)
    }
    
    public init(value: CGFloat) {
        self.init(top: value, leading: value, bottom: value, trailing: value)
    }
}

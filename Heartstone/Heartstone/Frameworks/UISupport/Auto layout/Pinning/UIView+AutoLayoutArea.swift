import UIKit

extension UIView {
    /**
     Returns the surrounding anchors for this view
     */
    public var defaultLayoutArea: LayoutArea {
        return LayoutArea(view: self)
    }
    
    /**
     Returns the safe area anchors for iOS 11 and above and the default view anchors for iOS 10 and below
     */
    public var safeLayoutArea: LayoutArea {
        if #available(iOS 11.0, *) {
            return LayoutArea(layoutGuide: safeAreaLayoutGuide)
        } else {
            return defaultLayoutArea
        }
    }
    
    /**
     Returns the margins anchors
     */
    public var layoutMarginLayoutArea: LayoutArea {
        return LayoutArea(layoutGuide: layoutMarginsGuide)
    }
    
    /**
     Returns the anchors and dimensions related to the given layout area
     - Parameter layoutArea: which layout area anchors should be returned
     */
    public func layoutArea(for layoutArea: LayoutAreaType) -> LayoutArea {
        switch layoutArea {
        case .default:
            return defaultLayoutArea
        case .safeArea:
            return safeLayoutArea
        case .layoutMargins:
            return layoutMarginLayoutArea
        }
    }
}

import UIKit

extension UIView {
    /**
     Helper class that contains many of a view its anchors and dimensions.
     This is used i.e. to abstract the usage of which layout area of the view is used (default, layoutMargins, safeArea)
     */
    public struct LayoutArea: Equatable {
        public let topAnchor: NSLayoutYAxisAnchor
        public let leadingAnchor: NSLayoutXAxisAnchor
        public let bottomAnchor: NSLayoutYAxisAnchor
        public let trailingAnchor: NSLayoutXAxisAnchor
        public var widthAnchor: NSLayoutDimension
        public var heightAnchor: NSLayoutDimension
        public var centerXAnchor: NSLayoutXAxisAnchor
        public var centerYAnchor: NSLayoutYAxisAnchor
        
        public init(topAnchor: NSLayoutYAxisAnchor,
                    leadingAnchor: NSLayoutXAxisAnchor,
                    bottomAnchor: NSLayoutYAxisAnchor,
                    trailingAnchor: NSLayoutXAxisAnchor,
                    widthAnchor: NSLayoutDimension,
                    heightAnchor: NSLayoutDimension,
                    centerXAnchor: NSLayoutXAxisAnchor,
                    centerYAnchor: NSLayoutYAxisAnchor) {
            self.topAnchor = topAnchor
            self.leadingAnchor = leadingAnchor
            self.bottomAnchor = bottomAnchor
            self.trailingAnchor = trailingAnchor
            self.widthAnchor = widthAnchor
            self.heightAnchor = heightAnchor
            self.centerXAnchor = centerXAnchor
            self.centerYAnchor = centerYAnchor
        }
        
        public init(view: UIView) {
            self.init(topAnchor: view.topAnchor,
                      leadingAnchor: view.leadingAnchor,
                      bottomAnchor: view.bottomAnchor,
                      trailingAnchor: view.trailingAnchor,
                      widthAnchor: view.widthAnchor,
                      heightAnchor: view.heightAnchor,
                      centerXAnchor: view.centerXAnchor,
                      centerYAnchor: view.centerYAnchor)
        }
        
        public init(layoutGuide: UILayoutGuide) {
            self.init(topAnchor: layoutGuide.topAnchor,
                      leadingAnchor: layoutGuide.leadingAnchor,
                      bottomAnchor: layoutGuide.bottomAnchor,
                      trailingAnchor: layoutGuide.trailingAnchor,
                      widthAnchor: layoutGuide.widthAnchor,
                      heightAnchor: layoutGuide.heightAnchor,
                      centerXAnchor: layoutGuide.centerXAnchor,
                      centerYAnchor: layoutGuide.centerYAnchor)
        }
    }
}

import UIKit

extension CardDetailsViewController {
    @objc(CardDetailsViewControllerView) 
    internal final class View: UIView {
        internal init() {
            super.init(frame: UIScreen.main.bounds)
            
            configureViews()
        }
        
        internal required init?(coder aDecoder: NSCoder) {
            fatalError("init(coder:) has not been implemented")
        }
    }
}

// MARK: Configure Views
extension CardDetailsViewController.View {
    private func configureViews() {}
}

// MARK: Layout Views
extension CardDetailsViewController.View {
    internal override func layoutSubviews() {
        super.layoutSubviews()
    }
}

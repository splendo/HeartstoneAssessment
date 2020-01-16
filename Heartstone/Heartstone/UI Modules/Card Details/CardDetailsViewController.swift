import UIKit

internal final class CardDetailsViewController: UIViewController {
    private let card: HeartStoneCard
    
    private lazy var rootView = View()
    
    internal init(card: HeartStoneCard) {
        self.card = card
        
        super.init(nibName: nil, bundle: nil)
    }

    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: Load view
extension CardDetailsViewController {
    internal override func loadView() {
        view = rootView
    }
}

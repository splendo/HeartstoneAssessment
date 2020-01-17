import UIKit

internal final class CardDetailsViewController: UIViewController {
    private let card: HeartStoneCard
    private let tableAdapter = CardDetailTableAdapter()
    
    private lazy var rootView = View(tableAdapter: tableAdapter)
    
    internal init(card: HeartStoneCard) {
        self.card = card
        
        super.init(nibName: nil, bundle: nil)
        
        tableAdapter.card = card
        
        configureNavigationItem()
    }

    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func configureNavigationItem() {
        navigationItem.title = card.name
    }
}

// MARK: Load view
extension CardDetailsViewController {
    internal override func loadView() {
        view = rootView
    }
}

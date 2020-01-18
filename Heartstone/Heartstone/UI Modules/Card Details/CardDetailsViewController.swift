import UIKit

internal final class CardDetailsViewController: UIViewController {
    private let card: HeartStoneCard
    private let appDependencies: AppDependencies
    
    private let tableAdapter = CardDetailTableAdapter()
    
    private lazy var rootView = View(tableAdapter: tableAdapter)
    
    internal init(appDependencies: AppDependencies, card: HeartStoneCard) {
        self.card = card
        self.appDependencies = appDependencies
        
        super.init(nibName: nil, bundle: nil)
        
        tableAdapter.card = card
        
        configureNavigationItem()
    }

    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func configureNavigationItem() {
        navigationItem.title = card.name
        
        updateFavoriteNavigationButton()
    }
}

// MARK: - Load view
extension CardDetailsViewController {
    internal override func loadView() {
        view = rootView
    }
}

// MARK: - User Interaction
extension CardDetailsViewController {
    @objc
    private func tappedFavorite(from sender: UIBarButtonItem) {
        appDependencies.favoritesManager.addFavorite(card)
        
        updateFavoriteNavigationButton()
    }
    
    @objc
    private func tappedUnfavorite(from sender: UIBarButtonItem) {
        appDependencies.favoritesManager.removeFavorite(card)
        
        updateFavoriteNavigationButton()
    }
    
    private func updateFavoriteNavigationButton() {
        if appDependencies.favoritesManager.isFavorite(card) {
            navigationItem.rightBarButtonItem = UIBarButtonItem(image: UIImage(systemName: "star.fill"),
                                                                style: .plain,
                                                                target: self, action: #selector(self.tappedUnfavorite(from:)))
        } else {
            navigationItem.rightBarButtonItem = UIBarButtonItem(image: UIImage(systemName: "star"),
                                                                style: .plain,
                                                                target: self, action: #selector(self.tappedFavorite(from:)))
        }
    }
}

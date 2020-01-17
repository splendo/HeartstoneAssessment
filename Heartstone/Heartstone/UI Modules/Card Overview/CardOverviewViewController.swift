import UIKit

internal final class CardOverviewViewController: UIViewController {
    private let appDependencies: AppDependencies
    private let collectionAdapter = CardCollectionAdapter()
    
    private lazy var rootView = View(adapter: self.collectionAdapter)
    
    internal init(appDependencies: AppDependencies) {
        self.appDependencies = appDependencies
        
        super.init(nibName: nil, bundle: nil)
        
        configureNavigationItem()
        configureCollectionAdapter()
    }

    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func configureNavigationItem() {
        navigationItem.titleView = UIImageView(image: UIImage(named: "nav_mainIcon"))
        
        let filterBarButtonItem = UIBarButtonItem(image: UIImage(systemName: "tag"),
                                                  style: .plain,
                                                  target: self,
                                                  action: #selector(self.tappedFilter(_:)))
        
        navigationItem.rightBarButtonItems = [filterBarButtonItem]
    }
    
    private func configureCollectionAdapter() {
        collectionAdapter.didSelect = { [weak self] indexPath in
            self?.selectedItem(at: indexPath)
        }
    }
}

// MARK: Load view
extension CardOverviewViewController {
    internal override func loadView() {
        view = rootView
        
        loadCards()
    }
}

// MARK: Content Loading
extension CardOverviewViewController {
    private func loadCards() {
        appDependencies.heartStoneCardManager.retrieveCards(.init()) { [weak self] result, error in
            if let result = result {
                self?.received(result)
            } else {
                self?.received(error)
            }
        }
    }
    
    private func received(_ result: HeartStoneCardManager.Result) {
        collectionAdapter.setItems(result.cards)
        rootView.state = .content
        rootView.reloadData()
    }
    
    private func received(_ error: Error?) {
        rootView.state = .error
    }
}

// MARK: Content Loading
extension CardOverviewViewController {
    private func selectedItem(at indexPath: IndexPath) {
        guard let card = collectionAdapter.itemAt(indexPath) else {
            return assertionFailure("Could not find card for \(indexPath)")
        }
        
        let detailViewController = CardDetailsViewController(card: card)
        
        navigationController?.pushViewController(detailViewController, animated: true)
    }
}

// MARK: User Interaction
extension CardOverviewViewController {
    @objc
    private func tappedFilter(_ barButton: UIBarButtonItem) {}
}

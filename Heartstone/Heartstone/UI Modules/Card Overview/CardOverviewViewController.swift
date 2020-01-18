import UIKit

internal final class CardOverviewViewController: UIViewController {
    private let appDependencies: AppDependencies
    private let collectionAdapter = CardCollectionAdapter()
    
    private lazy var rootView = View(adapter: self.collectionAdapter)
    
    private var availableFilters: [HeartStoneFilter]?
    private var activeFilters = HeartStoneActiveFilters()
    
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
        loadFilters()
    }
}

// MARK: Content Loading
extension CardOverviewViewController {
    private func loadCards() {
        let request = HeartStoneCardManager.Request(activeFilters: activeFilters)
        
        appDependencies.heartStoneCardManager.retrieveCards(request) { [weak self] result, error in
            if let result = result {
                self?.receivedCardResult(result)
            } else {
                self?.received(error)
            }
        }
    }
    
    private func receivedCardResult(_ result: HeartStoneCardManager.Result) {
        collectionAdapter.setSets(result.sets)
        rootView.state = .content
        rootView.reloadData()
    }
    
    private func received(_ error: Error?) {
        rootView.state = .error
    }
    
    private func loadFilters() {
        appDependencies.heartStoneFilterManager.retrieveFilters { [weak self] result, error in
            if let result = result {
                self?.receivedFiltersResult(result)
            } else {
                print("Could not receive filters with error \(String(describing: error))")
            }
        }
    }
    
    private func receivedFiltersResult(_ result: HeartStoneFilterManager.Result) {
        guard result.filters.isNotEmpty else {
            return
        }
        
        availableFilters = result.filters
        
        let filterBarButtonItem = UIBarButtonItem(image: UIImage(systemName: "tag"),
                                                  style: .plain,
                                                  target: self,
                                                  action: #selector(self.tappedFilter(_:)))
        
        navigationItem.rightBarButtonItems = [filterBarButtonItem]
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
    private func tappedFilter(_ barButton: UIBarButtonItem) {
        guard let availableFilters = availableFilters else {
            return assertionFailure("Should not be called when available filters is nil")
        }
        
        let viewController = FilterViewController(availableFilters: availableFilters, activeFilters: activeFilters)
        viewController.delegate = self
        let navigationController = UINavigationController(rootViewController: viewController)
        
        present(navigationController, animated: true, completion: nil)
    }
}

// MARK: FilterViewControllerDelegate
extension CardOverviewViewController: FilterViewControllerDelegate {
    internal func filterViewController(_ viewController: FilterViewController, finishedWithFilters activeFilters: HeartStoneActiveFilters) {
        self.activeFilters = activeFilters
        
        loadCards()
        
        viewController.dismiss(animated: true)
    }
    
    internal func filterViewControllerCancelled(_ viewController: FilterViewController) {
        viewController.dismiss(animated: true)
    }
}

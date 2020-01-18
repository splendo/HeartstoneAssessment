import UIKit

internal final class CardOverviewViewController: UIViewController {
    private let appDependencies: AppDependencies
    private let collectionAdapter: CardCollectionAdapter
    
    private lazy var rootView = View(adapter: self.collectionAdapter)
    
    private var availableFilters: [HeartStoneFilter]?
    private var activeFilters = HeartStoneActiveFilters()
    private var sortNamesAscending: Bool = true
    
    internal init(appDependencies: AppDependencies) {
        self.appDependencies = appDependencies
        self.collectionAdapter = CardCollectionAdapter(favoritesManager: appDependencies.favoritesManager)
        
        super.init(nibName: nil, bundle: nil)
        
        configureNavigationItem()
        configureCollectionAdapter()
    }

    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func configureNavigationItem() {
        navigationItem.titleView = UIImageView(image: UIImage(named: "nav_mainIcon"))
        
        navigationItem.leftBarButtonItem = UIBarButtonItem(title: "Sort",
                                                           style: .plain,
                                                           target: self,
                                                           action: #selector(self.tappedSort(from:)))
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

// MARK: Load view
extension CardOverviewViewController {
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        collectionAdapter.softReload(rootView.collectionView)
    }
}

// MARK: Content Loading
extension CardOverviewViewController {
    private func loadCards() {
        let request = HeartStoneCardManager.Request(sortNamesAscending: sortNamesAscending, activeFilters: activeFilters)
        
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
                                                  action: #selector(self.tappedFilter(from:)))
        
        navigationItem.rightBarButtonItems = [filterBarButtonItem]
    }
}

// MARK: Content Loading
extension CardOverviewViewController {
    private func selectedItem(at indexPath: IndexPath) {
        guard let card = collectionAdapter.itemAt(indexPath) else {
            return assertionFailure("Could not find card for \(indexPath)")
        }
        
        let detailViewController = CardDetailsViewController(appDependencies: appDependencies, card: card)
        
        navigationController?.pushViewController(detailViewController, animated: true)
    }
}

// MARK: User Interaction
extension CardOverviewViewController {
    @objc
    private func tappedFilter(from sender: UIBarButtonItem) {
        guard let availableFilters = availableFilters else {
            return assertionFailure("Should not be called when available filters is nil")
        }
        
        let viewController = FilterViewController(availableFilters: availableFilters, activeFilters: activeFilters)
        viewController.delegate = self
        let navigationController = UINavigationController(rootViewController: viewController)
        
        present(navigationController, animated: true, completion: nil)
    }
    
    @objc
    private func tappedSort(from sender: UIBarButtonItem) {
        let actionSheet = UIAlertController(title: "Sort order", message: nil, preferredStyle: .actionSheet)
        
        let sortAscendingAction = UIAlertAction(title: "Names ascending", style: .default) { [weak self] _ in
            self?.setSortNamesAscending(true)
        }
        
        let sortDescendingAction = UIAlertAction(title: "Names descending", style: .default) { [weak self] _ in
            self?.setSortNamesAscending(false)
        }
        
        actionSheet.addAction(sortAscendingAction)
        actionSheet.addAction(sortDescendingAction)
        actionSheet.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        
        if sortNamesAscending {
            actionSheet.preferredAction = sortAscendingAction
        } else {
            actionSheet.preferredAction = sortDescendingAction
        }
        
        present(actionSheet, animated: true, completion: nil)
    }
    
    private func setSortNamesAscending(_ value: Bool) {
        guard sortNamesAscending != value else {
            return
        }
        
        sortNamesAscending = value
        
        loadCards()
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

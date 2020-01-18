import UIKit

internal final class CardOverviewViewController: UIViewController {
    private let appDependencies: AppDependencies
    private let collectionAdapter: CardCollectionAdapter
    
    private lazy var rootView = View(adapter: self.collectionAdapter)
    
    private weak var router: CardRouter?
    
    private var availableFilters: [HearthstoneFilter]?
    private var activeFilters = HearthstoneActiveFilters()
    private var sortNamesAscending: Bool = true
    
    internal init(router: CardRouter, appDependencies: AppDependencies) {
        self.appDependencies = appDependencies
        self.router = router
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
        let request = HearthstoneCardManager.Request(sortNamesAscending: sortNamesAscending, activeFilters: activeFilters)
        
        appDependencies.hearthstoneCardManager.retrieveCards(request) { [weak self] result, error in
            if let result = result {
                if result.isEmpty {
                    self?.received(error, message: "Couldn't find any content matching current filters")
                } else {
                    self?.receivedCardResult(result)
                }
            } else {
                self?.received(error, message: "Failed to load content. Please try again later")
            }
        }
    }
    
    private func receivedCardResult(_ result: HearthstoneCardManager.Result) {
        collectionAdapter.sets = result.sets
        rootView.state = .content
        rootView.reloadData()
    }
    
    private func received(_ error: Error?, message: String) {
        rootView.state = .error
        rootView.errorMessage = message
    }
    
    private func loadFilters() {
        appDependencies.hearthstoneFilterManager.retrieveFilters { [weak self] result, error in
            if let result = result {
                self?.receivedFiltersResult(result)
            } else {
                print("Could not receive filters with error \(String(describing: error))")
            }
        }
    }
    
    private func receivedFiltersResult(_ result: HearthstoneFilterManager.Result) {
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
        router?.routeToCardDetails(cardSets: collectionAdapter.sets, selectedIndexPath: indexPath)
    }
}

// MARK: User Interaction
extension CardOverviewViewController {
    @objc
    private func tappedFilter(from sender: UIBarButtonItem) {
        guard let availableFilters = availableFilters else {
            return assertionFailure("Should not be called when available filters is nil")
        }
        
        router?.routeToFilters(availableFilters: availableFilters, activeFilters: activeFilters) { [weak self] newActiveFilters in
            self?.activeFilters = newActiveFilters
            self?.loadCards()
        }
    }
    
    @objc
    private func tappedSort(from sender: UIBarButtonItem) {
        router?.routeToSort(from: sender) { [weak self] sortNamesAscending in
            self?.setSortNamesAscending(sortNamesAscending)
        }
    }
    
    private func setSortNamesAscending(_ value: Bool) {
        guard sortNamesAscending != value else {
            return
        }
        
        sortNamesAscending = value
        
        loadCards()
    }
}

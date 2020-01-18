import UIKit

internal final class CardDetailCollectionViewController: UIViewController {
    private let collectionAdapter: CardDetailsCollectionAdapter
    private lazy var rootView = View(collectionAdapter: collectionAdapter)
    
    private let startIndexPath: IndexPath
    private var hasPerformedInitialLayout = false
    
    internal init(appDependencies: AppDependencies, indexPath: IndexPath, cardSets: [HearthstoneCardSet]) {
        self.collectionAdapter = CardDetailsCollectionAdapter(favoritesManager: appDependencies.favoritesManager)
        self.startIndexPath = indexPath
        
        super.init(nibName: nil, bundle: nil)
        
        collectionAdapter.sets = cardSets
    }

    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: Load view
extension CardDetailCollectionViewController {
    internal override func loadView() {
        view = rootView
        
        rootView.backButton.addTarget(self, action: #selector(self.tappedBackButton(_:)), for: .touchUpInside)
    }
}

// MARK: Lifecycle
extension CardDetailCollectionViewController {
    internal override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        navigationController?.setNavigationBarHidden(true, animated: true)
    }
    
    internal override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        navigationController?.setNavigationBarHidden(false, animated: true)
    }
}

// MARK: Layout view
extension CardDetailCollectionViewController {
    internal override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        
        if hasPerformedInitialLayout == false {
            hasPerformedInitialLayout = true
            
            rootView.scrollToIndexPath(startIndexPath, animated: false)
        }
    }
}

// MARK: User Interaction
extension CardDetailCollectionViewController {
    @objc
    private func tappedBackButton(_ sender: UIButton) {
        navigationController?.popViewController(animated: true)
    }
}

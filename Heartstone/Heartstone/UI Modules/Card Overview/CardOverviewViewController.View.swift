import UIKit

// MARK: Configure Views
extension CardOverviewViewController.View {
    internal enum State {
        case loading, content, error
    }
}

extension CardOverviewViewController {
    @objc(CardOverviewViewControllerView) 
    internal final class View: UIView {
        private let collectionView: UICollectionView
        private let collectionFlowLayout = UICollectionViewFlowLayout()
        private let activityIndicator = UIActivityIndicatorView(style: .medium)
        private let errorLabel = UILabel()
        
        private let adapter: CollectionAdapter
        
        internal var state: State = .loading {
            didSet { stateUpdated() }
        }
        
        internal init(adapter: CollectionAdapter) {
            self.adapter = adapter
            self.collectionView = UICollectionView(frame: UIScreen.main.bounds, collectionViewLayout: self.collectionFlowLayout)
            
            super.init(frame: UIScreen.main.bounds)
            
            configureViews()
            
            stateUpdated()
        }
        
        internal required init?(coder aDecoder: NSCoder) {
            fatalError("init(coder:) has not been implemented")
        }
        
        internal func reloadData() {
            collectionView.reloadData()
        }
    }
}

// MARK: Configure Views
extension CardOverviewViewController.View {
    private func configureViews() {
        [collectionView, activityIndicator, errorLabel]
            .disableTranslateAutoresizingMask()
            .add(to: self)
        
        backgroundColor = .systemGroupedBackground
        
        configureCollectionView()
        configureActivityIndicator()
    }
    
    private func configureCollectionView() {
        adapter.configure(collectionView)
        
        collectionView.backgroundColor = .clear
        collectionView.pinEdgesToSuperview()
        
        collectionView.contentInset = UIEdgeInsets(all: 2)
        collectionFlowLayout.minimumInteritemSpacing = 2
        collectionFlowLayout.minimumLineSpacing = 2
    }
    
    private func configureActivityIndicator() {
        activityIndicator.pinCenterToSuperview(layoutArea: .safeArea)
    }
    
    private func configureErrorLabel() {
        errorLabel.pinCenterToSuperview(layoutArea: .safeArea)
        errorLabel.pinWidth(with: self, extraWidth: -30, relation: .lessThanOrEqual)
        errorLabel.pinHeight(with: self, extraHeight: -200, relation: .lessThanOrEqual)
        
        errorLabel.numberOfLines = 0
        errorLabel.textAlignment = .center
        errorLabel.text = Localization.failedToLoadContentMessage
    }
}

// MARK: Layout Views
extension CardOverviewViewController.View {
    internal override func layoutSubviews() {
        super.layoutSubviews()
        
        updateItemSize()
    }
    
    private func updateItemSize() {
        let availableWidth = collectionView.bounds.width - collectionView.contentInset.width - 2 * collectionFlowLayout.minimumInteritemSpacing
        
        let itemWidth = floor(availableWidth / 3)
        
        collectionFlowLayout.itemSize = CGSize(width: itemWidth, height: itemWidth + 32)
    }
}

// MARK: State
extension CardOverviewViewController.View {
    private func stateUpdated() {
        collectionView.isHidden = state != .content
        activityIndicator.isHidden = state != .loading
        errorLabel.isHidden = state != .error
        
        if state == .loading {
            activityIndicator.startAnimating()
        } else {
            activityIndicator.stopAnimating()
        }
    }
}
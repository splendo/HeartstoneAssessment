import UIKit

extension CardDetailCollectionViewController {
    @objc(CardDetailCollectionViewControllerView) 
    internal final class View: UIView {
        private let collectionView: UICollectionView
        private let flowLayout = UICollectionViewFlowLayout()
        internal let backButton = UIButton()
        
        private let collectionAdapter: CollectionAdapter
        
        internal init(collectionAdapter: CollectionAdapter) {
            self.collectionAdapter = collectionAdapter
            self.collectionView = UICollectionView(frame: UIScreen.main.bounds, collectionViewLayout: self.flowLayout)
            
            super.init(frame: UIScreen.main.bounds)
            
            configureViews()
        }
        
        internal required init?(coder aDecoder: NSCoder) {
            fatalError("init(coder:) has not been implemented")
        }
    }
}

// MARK: Configure Views
extension CardDetailCollectionViewController.View {
    private func configureViews() {
        [collectionView, backButton]
            .disableTranslateAutoresizingMask()
            .add(to: self)
        
        backgroundColor = .systemGroupedBackground
        
        configureCollectionView()
        configureBackButton()
    }
    
    private func configureCollectionView() {
        collectionAdapter.configure(collectionView)
        
        collectionView.pinEdgesToSuperview()
        
        collectionView.backgroundColor = .systemGroupedBackground
        collectionView.contentInsetAdjustmentBehavior = .never
        collectionView.isPagingEnabled = true
        collectionView.showsHorizontalScrollIndicator = false
        
        flowLayout.scrollDirection = .horizontal
        flowLayout.sectionInset = .zero
        flowLayout.minimumInteritemSpacing = 0
        flowLayout.minimumLineSpacing = 0
    }
    
    private func configureBackButton() {
        backButton.setImage(UIImage(systemName: "chevron.left"), for: .normal)
        backButton.pin(singleSize: 44)
        backButton.pinTopToSuperview(layoutArea: .safeArea)
        backButton.pinLeadingToSuperview(padding: 0, layoutArea: .safeArea)
    }
}

// MARK: Layout views
extension CardDetailCollectionViewController.View {
    override func layoutSubviews() {
        super.layoutSubviews()
        
        flowLayout.itemSize = bounds.size
        flowLayout.headerReferenceSize = bounds.size
    }
}

// MARK: Misc
extension CardDetailCollectionViewController.View {
    internal func scrollToIndexPath(_ indexPath: IndexPath, animated: Bool) {
        collectionView.scrollToItem(at: indexPath, at: .centeredHorizontally, animated: animated)
    }
}

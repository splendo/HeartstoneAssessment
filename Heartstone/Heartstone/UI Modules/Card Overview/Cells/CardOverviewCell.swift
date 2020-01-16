import UIKit

extension CardOverviewCell {
    internal struct ViewModel {
        internal let title: String
        internal let imageURL: URL?
    }
}

internal final class CardOverviewCell: UICollectionViewCell {
    private let imageView = UIImageView()
    private let titleLabel = UILabel()
    
    internal var viewModel: ViewModel? {
        didSet { viewModelUpdated() }
    }
    
    internal override init(frame: CGRect) {
        super.init(frame: frame)
        
        configureViews()
    }
    
    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        
        viewModel = nil
    }
}

// MARK: Configure Views
extension CardOverviewCell {
    private func configureViews() {
        insetsLayoutMarginsFromSafeArea = false
        directionalLayoutMargins = NSDirectionalEdgeInsets(horizontal: 10, vertical: 5)
        
        [imageView, titleLabel]
            .disableTranslateAutoresizingMask()
            .add(to: contentView)
        
        configureBackgroundView()
        configureSelectedBackgroundView()
        configureImageView()
        configureTitleLabel()
    }
    
    private func configureBackgroundView() {
        backgroundView = UIView(frame: bounds)
        
        backgroundView?.backgroundColor = .secondarySystemGroupedBackground
    }
    
    private func configureSelectedBackgroundView() {
        selectedBackgroundView = UIView(frame: bounds)
        
        selectedBackgroundView?.backgroundColor = .tertiarySystemGroupedBackground
    }
    
    private func configureImageView() {
        imageView.pinEdgesToSuperview(layoutArea: .layoutMargins, excludeEdges: .bottom)
        imageView.pin(above: titleLabel, padding: 10)
        
        imageView.contentMode = .scaleAspectFit
    }
    
    private func configureTitleLabel() {
        titleLabel.pinEdgesToSuperview(layoutArea: .layoutMargins, excludeEdges: .top)
        titleLabel.pin(height: 32)
        titleLabel.textAlignment = .center
    }
}

// MARK: Misc
extension CardOverviewCell {
    private func viewModelUpdated() {
        titleLabel.text = viewModel?.title
    }
}

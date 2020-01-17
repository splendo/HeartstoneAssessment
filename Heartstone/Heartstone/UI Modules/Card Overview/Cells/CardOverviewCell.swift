import UIKit
import Alamofire
import AlamofireImage

extension CardOverviewCell {
    internal struct ViewModel {
        internal let title: String
        internal let imageURL: URL?
    }
}

internal final class CardOverviewCell: UICollectionViewCell {
    private let imageView = CardImageView()
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
        
        imageView.prepareForReuse()
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
        imageView.heightAnchor.constraint(equalTo: imageView.widthAnchor, multiplier: 1.5).activate()
        
        imageView.contentMode = .scaleAspectFit
    }
    
    private func configureTitleLabel() {
        titleLabel.pin(below: imageView, padding: 10)
        titleLabel.pinEdgesHorizontalToSuperview(layoutArea: .layoutMargins)
        titleLabel.pinBottomToSuperview(layoutArea: .layoutMargins, relation: .lessThanOrEqual)?.set(priority: .defaultLow)
        
        titleLabel.textAlignment = .center
        titleLabel.numberOfLines = 0
        titleLabel.setContentHuggingPriority(.required, for: .vertical)
    }
}

// MARK: Misc
extension CardOverviewCell {
    private func viewModelUpdated() {
        titleLabel.text = viewModel?.title
        
        guard let viewModel = viewModel else {
            imageView.image = nil
            return
        }
        
        imageView.loadURL(viewModel.imageURL)
    }
}

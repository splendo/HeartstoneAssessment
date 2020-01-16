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
    private let imageView = UIImageView()
    private let titleLabel = UILabel()
    private let activityIndicatorView = UIActivityIndicatorView(style: .medium)
    
    private var dataRequest: DataRequest?
    
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
        
        dataRequest?.cancel()
        dataRequest = nil
        
        activityIndicatorView.isHidden = true
        activityIndicatorView.stopAnimating()
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
        
        imageView.addSubview(activityIndicatorView.disableTranslateAutoresizingMask())
        
        configureBackgroundView()
        configureSelectedBackgroundView()
        configureImageView()
        configureTitleLabel()
        configureActivityIndicatorView()
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
    
    private func configureActivityIndicatorView() {
        activityIndicatorView.pinCenterToSuperview()
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
        
        if let imageURL = viewModel.imageURL {
            activityIndicatorView.isHidden = false
            activityIndicatorView.startAnimating()
            
            dataRequest = Alamofire.request(imageURL).responseImage { [weak self] response in
                self?.loadedImage(response.result.value, for: imageURL)
            }
        } else {
            setPlaceholderImage()
        }
    }
    
    private func loadedImage(_ image: UIImage?, for url: URL) {
        activityIndicatorView.stopAnimating()
        activityIndicatorView.isHidden = true
        
        if let image = image, viewModel?.imageURL == url {
            imageView.image = image
        } else {
            setPlaceholderImage()
        }
    }
    
    private func setPlaceholderImage() {
        imageView.image = UIImage(named: "cardPlaceholder")
    }
}

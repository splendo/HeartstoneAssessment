import UIKit
import Alamofire
import AlamofireImage

internal final class CardImageView: UIImageView {
    private let activityIndicatorView = UIActivityIndicatorView(style: .medium)
    
    private var dataRequest: DataRequest?
    private var url: URL?
    
    override var image: UIImage? {
        didSet { stopActivityIndicatorView() }
    }
    
    internal convenience init() {
        self.init(frame: .zero)
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        setup()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        
        setup()
    }
    
    private func setup() {
        contentMode = .scaleAspectFit
        
        addSubview(activityIndicatorView.disableTranslateAutoresizingMask())
        activityIndicatorView.pinCenterToSuperview()
    }
    
    internal func prepareForReuse() {
        url = nil
        
        dataRequest?.cancel()
        dataRequest = nil
        
        stopActivityIndicatorView()
    }
    
    internal func loadURL(_ url: URL?) {
        guard let url = url else {
            return setPlaceholderImage()
        }
        
        self.url = url
            
        activityIndicatorView.isHidden = false
        activityIndicatorView.startAnimating()
                
        dataRequest = Alamofire.request(url).responseImage { [weak self] response in
            self?.loadedImage(response.result.value, for: url)
        }
    }
    
    private func loadedImage(_ image: UIImage?, for url: URL) {
        stopActivityIndicatorView()
        
        if let image = image, self.url == url {
            self.image = image
        } else {
            setPlaceholderImage()
        }
        
        dataRequest = nil
    }
    
    private func stopActivityIndicatorView() {
        activityIndicatorView.stopAnimating()
        activityIndicatorView.isHidden = true
    }
    
    private func setPlaceholderImage() {
        image = UIImage(named: "cardPlaceholder")
    }
}

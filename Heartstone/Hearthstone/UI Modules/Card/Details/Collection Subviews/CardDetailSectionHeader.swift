import UIKit

internal final class CardDetailSectionHeader: UICollectionReusableView {
    private let titleLabel = UILabel()
    private let imageView = UIImageView()
    
    internal var title: String? {
        set { titleLabel.text = newValue }
        get { titleLabel.text }
    }
    
    internal override init(frame: CGRect) {
        super.init(frame: frame)
        
        configureViews()
    }
    
    internal required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Configure Views
extension CardDetailSectionHeader {
    private func configureViews() {
        [titleLabel, imageView]
            .disableTranslateAutoresizingMask()
            .add(to: self)
            
        insetsLayoutMarginsFromSafeArea = false
        directionalLayoutMargins = NSDirectionalEdgeInsets(horizontal: 20, vertical: 5)
        
        configureTitleLabel()
        configureImageView()
    }
    
    private func configureTitleLabel() {
        titleLabel.pinCenterHorizontalToSuperview(layoutArea: .layoutMargins)
        titleLabel.pin(below: imageView, padding: 20)
        titleLabel.font = .preferredFont(forTextStyle: .title1)
    }
    
    private func configureImageView() {
        imageView.image = UIImage(named: "sectionHeader")
        imageView.pinCenterHorizontalToSuperview(layoutArea: .layoutMargins)
        imageView.bottomAnchor.constraint(equalTo: layoutMarginsGuide.centerYAnchor).activate()
    }
}

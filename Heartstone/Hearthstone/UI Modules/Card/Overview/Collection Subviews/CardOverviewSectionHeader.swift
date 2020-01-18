import UIKit

internal final class CardOverviewSectionHeader: UICollectionReusableView {
    private let backgroundView = UIVisualEffectView(effect: UIBlurEffect(style: .regular))
    private let titleLabel = UILabel()
    
    internal var title: String? {
        set { titleLabel.text = newValue }
        get { titleLabel.text }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        configureViews()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Configure Views
extension CardOverviewSectionHeader {
    private func configureViews() {
        [backgroundView, titleLabel]
            .disableTranslateAutoresizingMask()
            .add(to: self)
            
        insetsLayoutMarginsFromSafeArea = false
        directionalLayoutMargins = NSDirectionalEdgeInsets(horizontal: 20, vertical: 5)
        
        configureBackgroundView()
        configureTitleLabel()
    }
    
    private func configureBackgroundView() {
        backgroundView.pinEdgesToSuperview()
    }
    
    private func configureTitleLabel() {
        titleLabel.pinEdgesToSuperview(layoutArea: .layoutMargins)
    }
}

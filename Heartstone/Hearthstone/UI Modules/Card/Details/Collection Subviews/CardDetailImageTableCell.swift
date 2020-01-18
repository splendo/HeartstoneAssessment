import UIKit

internal final class CardDetailImageTableCell: UITableViewCell {
    internal typealias ViewModel = CardCollectionCell.ViewModel
    
    private let cardImageView = CardImageView()
    private let favoriteButton = UIButton()
    
    private var viewModel: ViewModel?
        
    internal override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        configureViews()
    }
    
    internal required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    internal override func prepareForReuse() {
        super.prepareForReuse()
        
        cardImageView.prepareForReuse()
    }
}

// MARK: - Configure views
extension CardDetailImageTableCell {
    private func configureViews() {
        insetsLayoutMarginsFromSafeArea = false
        directionalLayoutMargins = NSDirectionalEdgeInsets(horizontal: 10, vertical: 5)
        
        [cardImageView, favoriteButton]
            .disableTranslateAutoresizingMask()
            .add(to: contentView)
        
        configureCardImageView()
        configureFavoriteButton()
    }
    
    private func configureCardImageView() {
        cardImageView.pinEdgesToSuperview(layoutArea: .layoutMargins)
        cardImageView.heightAnchor.constraint(equalTo: contentView.widthAnchor, multiplier: 1.2).activate()
    }
    
    private func configureFavoriteButton() {
        favoriteButton.pinTopToSuperview(layoutArea: .safeArea)
        favoriteButton.pinTrailingToSuperview(padding: 10, layoutArea: .safeArea)
        favoriteButton.pin(singleSize: 44)
        
        favoriteButton.addTarget(self, action: #selector(self.tappedFavoriteButton(_:)), for: .touchUpInside)
    }
}

// MARK: - Content
extension CardDetailImageTableCell {
    internal func setViewModel(_ viewModel: ViewModel?) {
        self.viewModel = viewModel
                
        cardImageView.loadURL(viewModel?.card.imageURL)
        updateFavoriteButton()
    }
    
    private func updateFavoriteButton() {
        if viewModel?.isFavorite() == true {
            favoriteButton.setImage(UIImage(systemName: "star.fill"), for: .normal)
        } else {
            favoriteButton.setImage(UIImage(systemName: "star"), for: .normal)
        }
    }
}

// MARK: - User Interaction
extension CardDetailImageTableCell {
    @objc
    private func tappedFavoriteButton(_ sender: UIButton) {
        viewModel?.toggleFavorite()
        updateFavoriteButton()
    }
}

import UIKit

internal final class CardDetailImageTableCell: UITableViewCell {
    private let cardImageView = CardImageView()
        
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        configureViews()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        
        cardImageView.prepareForReuse()
    }
}

// MARK: - Configure views
extension CardDetailImageTableCell {
    private func configureViews() {
        insetsLayoutMarginsFromSafeArea = false
        directionalLayoutMargins = NSDirectionalEdgeInsets(horizontal: 10, vertical: 5)
        
        contentView.addSubview(cardImageView.disableTranslateAutoresizingMask())
        cardImageView.pinEdgesToSuperview(layoutArea: .layoutMargins)
        cardImageView.heightAnchor.constraint(equalTo: contentView.widthAnchor, multiplier: 1.2).activate()
    }
}

// MARK: - Content
extension CardDetailImageTableCell {
    internal func loadURL(_ url: URL?) {
        cardImageView.loadURL(url)
    }
}

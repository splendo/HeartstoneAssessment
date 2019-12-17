//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

enum CardCellState {
    case noImage
    case loading
    case image(UIImage)
}

protocol CardCellDisplaying: class {
    func display(name: String)
    func display(state: CardCellState)
    func display(favoriteStatus: CardMetadata.FavoriteStatus)
}

class CardCellView: UICollectionViewCell {

    static let reuseIdentifier = "\(CardCellView.self)"

    var interactor: CardCellInteracting?

    private let favoriteImageSizeRatio: CGFloat = 0.2
    private let placeholderNameLabelMargin = 10

    private lazy var imageView: UIImageView = {
        let view = UIImageView()

        view.contentMode = .scaleAspectFit

        return view
    }()

    private lazy var favoriteImageView: UIImageView = {
        let view = UIImageView(image: Resources.favoriteImage)

        view.contentMode = .scaleAspectFit

        return view
    }()

    private lazy var loadingIndicator: UIActivityIndicatorView = {
        let indicator = UIActivityIndicatorView()

        indicator.hidesWhenStopped = true
        indicator.color = Style.CellLoadingIndicator.color

        return indicator
    }()

    private lazy var placeholderNameLabel: UILabel = {
        let label = UILabel()

        label.numberOfLines = 0
        label.textAlignment = .center
        label.textColor = Style.CellPlaceholderNameLabel.color

        return label
    }()

    private lazy var placeholderView: UIView = {
        let view = UIView()

        view.addSubview(placeholderNameLabel)

        view.backgroundColor = .white
        view.layer.cornerRadius = 5
        view.layer.masksToBounds = true

        return view
    }()

    override var bounds: CGRect {
        didSet {
            layout()
        }
    }

    required init?(coder: NSCoder?) {
        fatalError("init(coder:) has not been implemented")
    }

    override init(frame: CGRect) {
        super.init(frame: frame)

        setupPlaceholderView()
        setupImageView()
        setupLoadingIndicator()
        setupFavoriteImageView()

        layout()
    }

    private func setupFavoriteImageView() {
        addSubview(favoriteImageView)

        favoriteImageView.translatesAutoresizingMaskIntoConstraints = false
        favoriteImageView.rightAnchor.constraint(equalTo: rightAnchor).isActive = true
        favoriteImageView.topAnchor.constraint(equalTo: topAnchor).isActive = true
        favoriteImageView.widthAnchor.constraint(equalTo: widthAnchor,
                multiplier: favoriteImageSizeRatio).isActive = true
        favoriteImageView.heightAnchor.constraint(equalTo: widthAnchor,
                multiplier: favoriteImageSizeRatio).isActive = true
    }

    private func setupPlaceholderView() {
        addSubview(placeholderView)

        placeholderView.translatesAutoresizingMaskIntoConstraints = false
        placeholderView.centerXAnchor.constraint(equalTo: centerXAnchor).isActive = true
        placeholderView.centerYAnchor.constraint(equalTo: centerYAnchor).isActive = true
        placeholderView.widthAnchor.constraint(equalTo: widthAnchor).isActive = true
        placeholderView.heightAnchor.constraint(equalTo: heightAnchor).isActive = true

        placeholderNameLabel.translatesAutoresizingMaskIntoConstraints = false
        placeholderNameLabel.centerXAnchor.constraint(equalTo: placeholderView.centerXAnchor).isActive = true
        placeholderNameLabel.centerYAnchor.constraint(equalTo: placeholderView.centerYAnchor).isActive = true
        placeholderNameLabel.widthAnchor.constraint(
                equalTo: placeholderView.widthAnchor,
                constant: CGFloat(-1 * placeholderNameLabelMargin)).isActive = true
    }

    private func setupImageView() {
        addSubview(imageView)
    }

    private func setupLoadingIndicator() {
        addSubview(loadingIndicator)
    }

    private func layout() {
        loadingIndicator.center = CGPoint(x: bounds.width / 2, y: bounds.height / 6)
        imageView.frame = bounds
    }
}

// MARK: - CardCellDisplaying
extension CardCellView: CardCellDisplaying {
    func display(name: String) {
        placeholderNameLabel.text = name
    }

    func display(state: CardCellState) {
        switch state {
        case .noImage:
            loadingIndicator.stopAnimating()
            placeholderNameLabel.isHidden = false
            imageView.isHidden = true
        case .loading:
            loadingIndicator.startAnimating()
            placeholderNameLabel.isHidden = false
            imageView.isHidden = true
        case .image(let image):
            loadingIndicator.stopAnimating()
            placeholderNameLabel.isHidden = true
            imageView.isHidden = false
            imageView.image = image
        }
    }

    func display(favoriteStatus: CardMetadata.FavoriteStatus) {
        switch favoriteStatus {
        case .favorite: favoriteImageView.isHidden = false
        case .notFavorite: favoriteImageView.isHidden = true
        }
    }
}
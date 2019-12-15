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
}

class CardCellView: UICollectionViewCell {

    static let reuseIdentifier = "\(CardCellView.self)"

    var interactor: CardCellInteracting?

    private let placeholderNameLabelMargin = 10

    private lazy var imageView: UIImageView = {
        let view = UIImageView()

        view.contentMode = .scaleAspectFit

        return view
    }()

    private lazy var loadingIndicator: UIActivityIndicatorView = {
        let indicator = UIActivityIndicatorView()

        indicator.hidesWhenStopped = true
        indicator.color = UIColor(red: 0.74, green: 0.76, blue: 0.78, alpha: 1.0)

        return indicator
    }()

    private lazy var placeholderNameLabel: UILabel = {
        let placeholderNameLabel = UILabel()

        placeholderNameLabel.numberOfLines = 0
        placeholderNameLabel.textAlignment = .center
        placeholderNameLabel.textColor = UIColor(red: 0.20, green: 0.29, blue: 0.37, alpha: 1.0)

        return placeholderNameLabel
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

        layout()
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
        imageView.frame = bounds
        addSubview(imageView)
    }

    private func setupLoadingIndicator() {
        loadingIndicator.center = center
        addSubview(loadingIndicator)
    }

    private func layout() {
        loadingIndicator.center = CGPoint(x: bounds.width / 2, y: bounds.height / 6)
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
            imageView.isHidden = true
        case .loading:
            loadingIndicator.startAnimating()
            imageView.isHidden = true
        case .image(let image):
            loadingIndicator.stopAnimating()
            imageView.isHidden = false
            imageView.image = image
        }
    }
}
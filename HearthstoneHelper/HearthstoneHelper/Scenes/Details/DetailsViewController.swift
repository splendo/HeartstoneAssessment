//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

protocol DetailsDisplaying: class, Displaying {
    func display(image: UIImage)
    func display(favoriteStatus: CardMetadata.FavoriteStatus)
    func display(cardName: String)
    func display(cardFlavor: String)
}

final class DetailsViewController: UIViewController {
    private let interactor: DetailsInteracting

    private let favoriteButtonSize: CGFloat = 30

    private let imageTopOffset: CGFloat = -10
    private let imageHeightMultiplier: CGFloat = 0.7

    private let flavorLabelTopOffset: CGFloat = 10
    private let flavorLabelMargin: CGFloat = 30

    private lazy var favoriteBarImage: UIImageView = {
        let view = UIImageView()

        view.contentMode = .scaleAspectFit

        return view
    }()

    private lazy var favoriteButton: UIButton = {
        let button = UIButton()

        button.setBackgroundImage(Resources.notFavoriteImage, for: .normal)

        return button
    }()

    private lazy var imageView: UIImageView = {
        let view = UIImageView()

        view.contentMode = .scaleAspectFit

        return view
    }()

    private lazy var flavorLabel: UILabel = {
        let label = UILabel()

        label.numberOfLines = 0
        label.textAlignment = .center
        label.textColor = Style.CardDetailsFlavorLabel.color

        return label
    }()

    init(interactor: DetailsInteracting) {
        self.interactor = interactor

        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
        setupFavoriteView()
        setupImageView()
        setupFlavorLabel()

        interactor.loadDetails()
    }

    private func setupView() {
        view.backgroundColor = Style.ScreenBackground.color
    }

    private func setupImageView() {
        view.addSubview(imageView)

        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        imageView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor,
                constant: imageTopOffset).isActive = true
        imageView.heightAnchor.constraint(equalTo: view.heightAnchor,
                multiplier: imageHeightMultiplier).isActive = true
    }

    private func setupFlavorLabel() {
        view.addSubview(flavorLabel)

        flavorLabel.translatesAutoresizingMaskIntoConstraints = false
        flavorLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        flavorLabel.topAnchor.constraint(equalTo: imageView.bottomAnchor,
                constant: flavorLabelTopOffset).isActive = true
        flavorLabel.widthAnchor.constraint(equalTo: view.widthAnchor,
                constant: -1 * flavorLabelMargin).isActive = true
    }

    private func setupFavoriteView() {
        favoriteButton.translatesAutoresizingMaskIntoConstraints = false
        favoriteButton.widthAnchor.constraint(equalToConstant: favoriteButtonSize).isActive = true
        favoriteButton.heightAnchor.constraint(equalToConstant: favoriteButtonSize).isActive = true
        favoriteButton.addTarget(self, action: #selector(toggleFavoriteStatus), for: .touchUpInside)

        navigationItem.rightBarButtonItem = UIBarButtonItem(customView: favoriteButton)
    }

    @objc private func toggleFavoriteStatus() {
        interactor.toggleFavoriteStatus()
    }
}

// MARK: - DetailsDisplaying
extension DetailsViewController: DetailsDisplaying {
    func display(image: UIImage) {
        imageView.image = image
    }

    func display(favoriteStatus: CardMetadata.FavoriteStatus) {
        switch favoriteStatus {
        case .favorite:
            favoriteButton.setBackgroundImage(Resources.favoriteImage, for: .normal)
        case .notFavorite:
            favoriteButton.setBackgroundImage(Resources.notFavoriteImage, for: .normal)
        }
    }

    func display(cardName: String) {
        navigationItem.title = cardName
    }

    func display(cardFlavor: String) {
        flavorLabel.text = cardFlavor
    }
}
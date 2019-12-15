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

    private lazy var imageView: UIImageView = {
        let view = UIImageView()

        view.frame = bounds

        return view
    }()

    private lazy var placeholderNameLabel: UILabel = {
        let view = UILabel()
        return view
    }()

    required init?(coder: NSCoder?) {
        fatalError("init(coder:) has not been implemented")
    }

    override init(frame: CGRect) {
        super.init(frame: frame)

        setupPlaceholderNameLabel()

        addSubview(imageView)
    }
    
    private func setupPlaceholderNameLabel() {
        addSubview(placeholderNameLabel)

        placeholderNameLabel.numberOfLines = 0
        placeholderNameLabel.textAlignment = .center
        
        placeholderNameLabel.translatesAutoresizingMaskIntoConstraints = false
        placeholderNameLabel.centerXAnchor.constraint(equalTo: centerXAnchor).isActive = true
        placeholderNameLabel.centerYAnchor.constraint(equalTo: centerYAnchor).isActive = true
        placeholderNameLabel.widthAnchor.constraint(equalTo: widthAnchor).isActive = true
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
            imageView.isHidden = true
            placeholderNameLabel.isHidden = false
        case .loading:
            imageView.isHidden = true
            placeholderNameLabel.isHidden = false
            // todo: loading thingy
        case .image(let image):
            placeholderNameLabel.isHidden = true
            imageView.isHidden = false
            imageView.image = image
        }
    }
}
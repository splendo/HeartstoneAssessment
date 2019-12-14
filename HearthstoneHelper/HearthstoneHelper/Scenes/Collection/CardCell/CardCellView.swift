//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

protocol CardCellDisplaying: class {
    func display(name: String)
    func display(image: UIImage)
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
    }
    
    private func setupPlaceholderNameLabel() {
        addSubview(placeholderNameLabel)
        
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

    func display(image: UIImage) {
        // todo: this is temporary, image/name view will be removed by loading state change
        if (imageView.superview === self) {
            imageView.removeFromSuperview()
        }
        placeholderNameLabel.removeFromSuperview()
        
        imageView.image = image
        addSubview(imageView)
    }
}
//
//  CardsCollectionViewCell.swift
//  Hearthstone
//
//  Created by Dmitrii on 26/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import UIKit


class CardsCollectionViewCell: UICollectionViewCell {

    @IBOutlet var imageView: UIImageView!
    @IBOutlet var errorLabel: UILabel!

    private var currentImageURL: String?

    override func prepareForReuse() {
        super.prepareForReuse()
        errorLabel.isHidden = true
        setPlaceholderImage()
    }

    func setModel(_ model: CardsCollectionViewCellModel) {
        currentImageURL = model.imgURL
        setPlaceholderImage()
        errorLabel.text = "Image loading error"
        ImageManager.sharedInstance.downloadImage(urlString: model.imgURL) { [weak self] (image, url) in
            guard url == self?.currentImageURL else {return}
            if image != nil {
                self?.imageView?.image = image
            } else {
                self?.errorLabel.isHidden = false
            }
        }
    }

    private func setPlaceholderImage() {
        let placeholder = UIImage(named: "200px-Card_back-Power_Core")
        imageView?.image = placeholder
    }
}


class CardsCollectionViewCellModel {

    let imgURL: String

    init(card: Card) {
        imgURL = card.imgURL
    }
}

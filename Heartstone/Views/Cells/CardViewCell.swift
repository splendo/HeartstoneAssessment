//
//  CardViewCell.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit
import Kingfisher

class CardViewCell: UICollectionViewCell, NibInstantiatable {

    @IBOutlet private weak var borderedView: BorderedView! {
        didSet {
            borderedView.borderColor = UIColor.blue.cgColor
        }
    }

    @IBOutlet private weak var imageView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!

    var viewModel: CardViewModel? {
        didSet {
            guard let viewModel = viewModel else {
                return
            }
            titleLabel.text = viewModel.name
            borderedView.borderSides = viewModel.borderSides
            if let url = viewModel.imageURL {
                imageView.kf.setImage(with: url, placeholder: UIImage(named: "placeholder"))
            }
        }
    }
}

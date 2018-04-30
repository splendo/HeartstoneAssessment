//
// Created by Roman Petryshen on 25/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit
import SDWebImage

class HCCardsDetailsCell: UICollectionViewCell {
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var subtitleLabel: UILabel!
    @IBOutlet weak var imageView: UIImageView!
    
    func setImageUrlString(_ urlString: String) {
        imageView.image = nil
        imageView.sd_setImage(with: URL(string: urlString), placeholderImage: UIImage(named: HCCardsListCell.kCardPlaceholder))
    }
}

//
//  HCCardsListCell.swift
//  HeartstoneCards
//
//  Created by Roman Petryshen on 22/04/2018.
//  Copyright © 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit
import SDWebImage

public extension UICollectionViewCell {
    
    public static func getIdentifier() -> String {
        return String(describing: self)
    }
    
    public static func getNibName() -> String {
        return String(describing: self)
    }
}

class HCCardsListCell: UICollectionViewCell {

    static let kCardPlaceholder = "CardPlaceholder"

    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var favoriteView: UIView!
    @IBOutlet weak var ribbonView: UIView!

    public var isFavorite: Bool = false {
        didSet {
            favoriteView.isHidden = !isFavorite
        }
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        ribbonView.layer.cornerRadius = ribbonView.frame.height / 2
    }

    func setImageUrlString(_ urlString: String) {
        imageView.image = nil
        imageView.sd_setImage(with: URL(string: urlString), placeholderImage: UIImage(named: HCCardsListCell.kCardPlaceholder))
    }
}

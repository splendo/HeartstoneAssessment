//
//  CardCollectionViewCell.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import UIKit
import SDWebImage

class CardCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var playerClassLabel: UILabel!
    
    static let PLACEHOLDER = "CardPlaceholder"
    
    var card: Card?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        //
    }
    
    func setCard(_ card: Card) {
        self.card = card
        updateUI()
    }
    
    func updateUI() {
        guard let cardItem = card else {
            return
        }
        
        playerClassLabel.text = cardItem.playerClass ?? "--"
        
        // mark Elite item
        if cardItem.isElite {
            playerClassLabel.textColor = UIColor.magenta
        } else {
            playerClassLabel.textColor = UIColor.darkGray
        }
        
        // set Image url
        if let imageUrl = card?.img {
            imageView.sd_setImage(with: URL(string: imageUrl), placeholderImage:UIImage(named: CardCollectionViewCell.PLACEHOLDER), options: .cacheMemoryOnly, completed: nil)
        }
    }
    
}

//
//  CardDetailCollectionViewCell.swift
//  HearthstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/15/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation
import UIKit
import ScalingCarousel
import SDWebImage

protocol CardDetailViewCellDelegate: NSObjectProtocol {
    func markFavourite(_ card: Card)
}

class CardDetailCollectionViewCell: ScalingCarouselCell {
    
    weak var cardDetailViewDelegate: CardDetailViewCellDelegate? = nil
    
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var playerClassLabel: UILabel!
    @IBOutlet weak var favoutireButton: UIButton!
    
    var card: Card?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        //
    }
    
    @IBAction func favouriteTapped(_ sender: UIButton) {
        sender.isSelected = !sender.isSelected
        cardDetailViewDelegate?.markFavourite(card!)
    }
    
    func setCard(_ card: Card) {
        self.card = card
        updateUI()
    }
    
    func updateUI() {
        guard let cardItem = card else {
            return
        }
        
        favoutireButton.isSelected = card?.isFavorite ?? false
        
        titleLabel.text = cardItem.name ?? "--"
        playerClassLabel.text = cardItem.playerClass ?? "--"        
        
        // set Image url
        if let imageUrl = card?.img {
            imageView.sd_setImage(with: URL(string: imageUrl), placeholderImage:UIImage(named: CardCollectionViewCell.PLACEHOLDER), options: .cacheMemoryOnly, completed: nil)
        }
    }
    
}

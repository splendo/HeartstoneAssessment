//
//  HeartStoneCardCollectionViewCell.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 21..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import UIKit
import Kingfisher

class HeartStoneCardCollectionViewCell: UICollectionViewCell
{
    //MARK: Variables
    
    var currentHeartStoneCard : HeartStoneCard?

    //MARK: Enums
    
    //MARK: IBOutlets
    
    @IBOutlet weak var backgroundImageView  : UIImageView!
    @IBOutlet weak var titleLabel           : UILabel!
    
    //MARK: IBActions
    
    //MARK: Life-cycle
    
    override func awakeFromNib()
    {
        super.awakeFromNib()
        
        commonInit()
    }
    
    override func prepareForReuse()
    {
        super.prepareForReuse()
        
        commonInit()
    }

    fileprivate func commonInit()
    {
        backgroundImageView.image   = nil
        titleLabel.text             = ""
    }
    
    //MARK: Functions
 
    func setupCell(forHeartStoneCard newHeartStoneCard: HeartStoneCard)
    {
        currentHeartStoneCard   = newHeartStoneCard
        titleLabel.text         = newHeartStoneCard.name
        
        if newHeartStoneCard.img.characters.count > 0,
            let url = URL(string: newHeartStoneCard.img)
        {
            DispatchQueue.main.async {
                
                self.backgroundImageView.kf.setImage(with: url, placeholder: #imageLiteral(resourceName: "placeholder_card"), options: nil, progressBlock: nil, completionHandler: nil)
                self.setNeedsDisplay()
            }
        }
        else
        {
            self.backgroundImageView.image = #imageLiteral(resourceName: "placeholder_card")
        }
    }
    
    func setupCell(forHeartStoneCardType newHeartStoneCardType: CardType)
    {
        titleLabel.text             = newHeartStoneCardType.rawValue
        backgroundImageView.image   = #imageLiteral(resourceName: "placeholder_card")
    }
}

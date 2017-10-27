//
//  CardDetailInfoTableViewCell.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 26..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import UIKit

class CardDetailInfoTableViewCell: UITableViewCell
{
    //MARK: Variables
    
    //MARK: Enums
    
    //MARK: IBOutlets
    
    @IBOutlet weak var titleLabel: UILabel!
    
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
        selectionStyle = .none
    }
    
    //MARK: Functions
    
    func setupCell(forHeartStoneCard newHeartStoneCard: HeartStoneCard)
    {
        let titleText = "\(newHeartStoneCard.text)\n \(newHeartStoneCard.flavor)\n \(newHeartStoneCard.howToGet)\n"
        
        titleLabel.text = titleText
    }
}

//
//  CardDetailHeaderTableViewCell.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 26..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import UIKit
import Kingfisher

class CardDetailHeaderTableViewCell: UITableViewCell
{
    //MARK: Variables
    
    //MARK: Enums
    
    //MARK: IBOutlets
    
    @IBOutlet weak var scrollView   : UIScrollView!
    
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
        scrollView.delegate                         = self
        scrollView.showsVerticalScrollIndicator     = false
        scrollView.showsHorizontalScrollIndicator   = false
        selectionStyle                              = .none
    }
    
    //MARK: Functions
    
    func setupCell(forHeartStoneCard newHeartStoneCard: HeartStoneCard, withCellHeight: CGFloat = 150)
    {
        let imageView           = UIImageView(frame: CGRect(x: 0, y: 0, width: UIScreen.main.bounds.width, height: withCellHeight))
        imageView.contentMode   = .scaleAspectFit
        
        if newHeartStoneCard.img.characters.count > 0
        {
            let url             = URL(string: newHeartStoneCard.img)
            imageView.kf.setImage(with: url, placeholder: #imageLiteral(resourceName: "placeholder_card"), options: nil, progressBlock: nil, completionHandler: nil)
        }
        else
        {
            imageView.image     = #imageLiteral(resourceName: "placeholder_card")
        }
        
        scrollView.addSubview(imageView)
    }
}

extension CardDetailHeaderTableViewCell: UIScrollViewDelegate
{
    func scrollViewDidScroll(_ scrollView: UIScrollView)
    {
        //disable vertical scrolling
        scrollView.setContentOffset(CGPoint(x: scrollView.contentOffset.x, y: 0), animated: false)
    }
}

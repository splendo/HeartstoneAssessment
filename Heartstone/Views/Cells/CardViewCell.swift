//
//  CardViewCell.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

class CardViewCell: UICollectionViewCell, NibInstantiatable {

    @IBOutlet weak var borderedView: BorderedView!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        borderedView.borderColor = UIColor.blue.cgColor
    }
}

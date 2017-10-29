//
//  CardDetailCell.swift
//  Hearthstone
//
//  Created by Dmitrii on 28/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import UIKit

class CardDetailCell: UITableViewCell {

    var model: CardDetailModel? {
        didSet {
            guard let model = model else {return}
            title.text = model.name + ":"
            detailText.text = model.text
        }
    }

    @IBOutlet private var title: UILabel!
    @IBOutlet private var detailText: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
    }
}

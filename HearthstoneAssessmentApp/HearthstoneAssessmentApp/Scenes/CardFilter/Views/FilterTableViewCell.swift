//
//  FilterTableViewCell.swift
//  HearthstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/17/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import UIKit

class FilterTableViewCell: UITableViewCell {
    
    @IBOutlet weak var filterNameLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func setFilterName(_ name: String) {
        filterNameLabel.text = name
    }
}


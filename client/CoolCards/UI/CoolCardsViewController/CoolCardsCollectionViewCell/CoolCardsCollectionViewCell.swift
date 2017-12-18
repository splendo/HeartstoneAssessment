//
//  CoolCardsCollectionViewCell.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import UIKit

class CoolCardsCollectionViewCell: UICollectionViewCell {
    public var didTap: ((CoolCardsCollectionViewCell) -> Void)?
    
    @IBOutlet weak var cardImageView: UIImageView!
    
    override public func awakeFromNib() {
        super.awakeFromNib()
        self.backgroundColor = UIColor(white: 0.0, alpha: 0.15)
        let gestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(CoolCardsCollectionViewCell.tap(_:)))
        self.addGestureRecognizer(gestureRecognizer)
    }
    
    func populate(imageURL: URL?, title: String, description: String) {
        self.cardImageView.setImage(imageURL: imageURL,  animatedOnce: true)
    }
    
    @objc private func tap(_ sender: Any) {
        self.didTap?(self)
    }
}

extension CoolCardsCollectionViewCell: NibLoadableView { }

extension CoolCardsCollectionViewCell: ReusableView { }

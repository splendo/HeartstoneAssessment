//
//  CardGridViewCell.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 31/7/22.
//

import UIKit

class CardGridViewCell: UICollectionViewCell {
    
    // MARK: - View Initialization
    
    lazy var cardName: UILabel = {
        let label = UILabel()
        label.font = UIFont.systemFont(ofSize: 16, weight: .heavy)
        label.textAlignment = .center
        label.textColor = .systemBlue
        
        return label
    }()
    
    lazy var cardImage: UIImageView = {
        let imageView = UIImageView()
        return imageView
    }()
    
    lazy var favoriteButton: UIButton = {
        let button = UIButton()
        button.setImage(UIImage(systemName: "heart"), for: .normal)
        button.tintColor = .red
        return button
    }()
    
    var cardViewModel: CardViewModel! {
        didSet {
            configure()
        }
    }
    
    // MARK: - Initializers
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        addSubview(cardName)
        cardName.addAnchors(wAnchor: widthAnchor, 0.85, cXAnchor: centerXAnchor, tAnchor: topAnchor, topConstant: 8)
        addSubview(cardImage)
        cardImage.addAnchors(lAnchor: leftAnchor, leftConstant: 4, tAnchor: cardName.bottomAnchor, rAnchor: rightAnchor, rightConstant: -4, bAnchor: bottomAnchor, bottomConstant: -8)
        addSubview(favoriteButton)
        favoriteButton.addAnchors(rAnchor: rightAnchor, rightConstant: -4, bAnchor: bottomAnchor, bottomConstant: -4)
        
        layer.borderWidth = 0.75
        layer.borderColor = UIColor.accentColor.cgColor
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}

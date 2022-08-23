//
//  CardView.swift
//  Hearthstone
//
//  Created by Epsilon User on 22/8/22.
//

import UIKit

class CardView: UIView {

    // MARK: - Subivew Initializers
    lazy var cardImage: UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFit
        return imageView
    }()
    
    lazy var cardName: UILabel = {
        let label = UILabel()
        label.font = UIFont(name: "MarkerFelt-Wide", size: 24.0)
        label.textAlignment = .left
        return label
    }()
    
    lazy var cardText: UILabel = {
        let label = UILabel()
        label.font = UIFont(name: "MarkerFelt-Thin", size: 16.0)
        label.numberOfLines = 0
        return label
    }()
    
    lazy var cardType: UIButton = {
        let button = UIButton()
        return button
    }()
    
    var cardViewModel: CardViewModel! {
        didSet {
            configure()
        }
    }
    
    // MARK: - Initializers
    init(frame: CGRect, for card: Card?) {
        super.init(frame: frame)
        
        addSubview(cardImage)
        cardImage.addAnchors(wAnchor: widthAnchor, 0.7, cXAnchor: centerXAnchor, tAnchor: topAnchor, topConstant: 32)
        addSubview(cardType)
        cardType.addAnchors(lAnchor: leftAnchor, leftConstant: 12, tAnchor: cardImage.bottomAnchor, topConstant: 12)
        addSubview(cardName)
        cardName.addAnchors(lAnchor: leftAnchor, leftConstant: 12, tAnchor: cardType.bottomAnchor, topConstant: 4, rAnchor: rightAnchor, rightConstant: -12)
        addSubview(cardText)
        cardText.addAnchors(lAnchor: cardName.leftAnchor, leftConstant: 4, tAnchor: cardName.bottomAnchor, topConstant: 4, rAnchor: cardName.rightAnchor)
        
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

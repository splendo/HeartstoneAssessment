//
//  CardView.swift
//  Hearthstone
//
//  Created by Epsilon User on 22/8/22.
//

import UIKit

class CardView: UIView {

    // MARK: - Subivew Initializers
    
    
    // MARK: - Initializers
    init(frame: CGRect, for card: Card) {
        super.init(frame: frame)
        
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

//
//  CardViewModel.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 28/7/22.
//

import Foundation

struct CardViewModel {
    
    let title: String
    let image: String?
    
}

extension CardViewModel {
    
    static var placeholderTitle: String {
        "No Name"
    }
    
}

extension CardViewModel {
    
    init(card: Card) {
        title = card.name ?? CardViewModel.placeholderTitle
        image = card.img
    }
    
}

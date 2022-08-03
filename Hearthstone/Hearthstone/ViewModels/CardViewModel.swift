//
//  CardViewModel.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 28/7/22.
//

import Foundation

struct CardViewModel {
    
    let title: String
    let image: String
    let isFavorite: Bool = false
    let select: () -> Void
    
    init(card: Card, select: @escaping () -> Void) {
        self.title = card.name ?? CardViewModel.placeholderTitle
        self.image = card.img
        // TODO: isFavorite should be initialized based on the Query in DB (Favorites table)
        self.select = select
    }
    
}

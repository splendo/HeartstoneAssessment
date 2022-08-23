//
//  CardViewModel.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 28/7/22.
//

import Foundation
import UIKit

struct CardViewModel {
    
    let title: String
    let image: String
    let isFavorite: Bool
    var isHsiaoFav: Bool = false
    // MARK: - Collection View variables
    let select: () -> Void
    // MARK: - Detail View variables
    var description: String?
    var type: String?
    
    init(card: Card, select: @escaping () -> Void) {
        self.title = card.name ?? CardViewModel.placeholderTitle
        self.image = card.img ?? "https://via.placeholder.com/500x500.png?text=No+Image+Found"
        // TODO: isFavorite should be initialized based on the Query in DB (Favorites table)
        isFavorite = false
        self.select = select
        isHsiaoFav = isFeatured(card)
    }
}

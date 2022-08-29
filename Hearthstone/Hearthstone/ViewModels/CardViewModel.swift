//
//  CardViewModel.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 28/7/22.
//

import Foundation
import UIKit

// Write Unit/UI tests for protocol
protocol UpdateFavoritesProtocol {
    func initFavorite(for card: CardViewModel, completion: @escaping(Bool) -> Void)
    func updateFavorite(for card: CardViewModel)
}

class CardViewModel {
    
    var delegate: UpdateFavoritesProtocol?
    
    let cardID: String
    let title: String
    let image: String
    var isFavorite: Bool = false
    var isHsiaoFav: Bool = false
    // MARK: - Collection View variables
    let select: () -> Void
    // MARK: - Detail View variables
    var description: String?
    var type: String?
    
    init(card: Card, select: @escaping () -> Void) {
        self.cardID = card.cardId ?? ""
        self.title = card.name ?? CardViewModel.placeholderTitle
        self.image = card.img ?? "https://via.placeholder.com/500x500.png?text=No+Image+Found"
        self.select = select
        isHsiaoFav = isFeatured(card)
    }
}

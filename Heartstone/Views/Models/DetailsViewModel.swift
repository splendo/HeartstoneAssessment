//
//  DetailsViewModel.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 05/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

struct DetailsViewModel {
    let cardId: String
    let name: String
    let imageURL: URL?
    let text: String?
    let isFavorite: Bool

    init(card: Card, isFavorite: Bool) {
        self.cardId = card.cardId
        self.name = card.name
        self.imageURL = card.imageURL
        self.text = "Rarity: \(card.rarity ?? "unknown")"
        self.isFavorite = isFavorite
    }

    var favoriteButtonTitle: String {
        return isFavorite ?
            NSLocalizedString("Remove from Favorites", comment: "Favorite Button") :
            NSLocalizedString("Add to Favorites", comment: "Favorite Button")
    }
}

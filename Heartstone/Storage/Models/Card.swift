//
//  Card.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import Foundation

struct Card: Decodable {

    typealias CardType = String
    typealias CardRarity = String

    struct Mechanics: Decodable {
        let name: String
    }

    let cardId: String
    let name: String
    let type: CardType
    let img: String?
    let text: String?
    let rarity: CardRarity?
    let classes: [String]?
    let mechanics: [Mechanics]?

    var imageURL: URL? {
        guard let img = img else { return nil }
        return URL(string: img)
    }
}

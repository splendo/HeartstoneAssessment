//
//  CardCollection.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

struct CardCollection: Decodable {

    private let groups: [String: [Card]]
    // Flatten cards list
    let cards: [Card]

    private struct GroupKeys: CodingKey {
        let intValue: Int? = nil
        var stringValue: String
        init?(stringValue: String) {
            self.stringValue = stringValue
        }
        init?(intValue: Int) { return nil }
    }

    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: GroupKeys.self)
        var results = [String: [Card]]()
        var cardsList = [Card]()
        try container.allKeys.forEach { key in
            let cards = try container.decode([Card].self, forKey: key)
            results[key.stringValue] = cards
            cardsList.append(contentsOf: cards)
        }
        groups = results
        cards = cardsList
    }
}

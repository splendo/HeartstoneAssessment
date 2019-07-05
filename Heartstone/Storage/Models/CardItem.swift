//
//  CardItem.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import CoreData

class CardItem: NSManagedObject {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<CardItem> {
        return NSFetchRequest<CardItem>(entityName: "CardItem")
    }

    @NSManaged public var cardId: String
    @NSManaged public var img: String?
    @NSManaged public var isFavorite: Bool
    @NSManaged public var name: String
    @NSManaged public var rarity: String?
    @NSManaged public var type: String
    @NSManaged public var updatedAt: Date

    var imageURL: URL? {
        guard let img = img else {
            return nil
        }
        return URL(string: img)
    }
}

extension CardItem {

    func toCard() -> Card {
        return Card(
            cardId: cardId,
            name: name,
            type: type,
            img: img,
            text: nil,
            rarity: rarity,
            classes: nil,
            mechanics: nil
        )
    }
}

//
//  CardStorage.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 05/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit
import CoreData

protocol CardStorage {

    var storage: Storage { get }
    
    func isExists(cardId: String) -> Bool
    func findCard(by cardId: String) -> CardItem?
    func createCard(from card: Card)
    func deleteCard(by cardId: String)
}

class CoreDataCardStorage: CardStorage {

    let storage: Storage

    init(storage: Storage) {
        self.storage = storage
    }

    func isExists(cardId: String) -> Bool {
        return findCardObjectId(by: cardId) != nil
    }

    func findCard(by cardId: String) -> CardItem? {
        if let objectId = findCardObjectId(by: cardId) {
            return storage.mainContext.object(with: objectId) as? CardItem
        }
        return nil
    }

    func findCardObjectId(by cardId: String) -> NSManagedObjectID? {
        let request: NSFetchRequest<CardItem> = CardItem.fetchRequest()
        request.predicate = NSPredicate(format: "cardId == %@", cardId)
        do {
            let results = try storage.mainContext.fetch(request)
            return results.first?.objectID
        } catch {
            return nil
        }
    }

    func createCard(from card: Card) {
        storage.performAndSave(context: storage.privateContext, block: { context in

            let entity = NSEntityDescription.entity(forEntityName: "CardItem", in: context)!
            let cardItem = CardItem.init(entity: entity, insertInto: context)

            cardItem.cardId = card.cardId
            cardItem.name = card.name
            cardItem.img = card.img
            cardItem.type = card.type
            cardItem.rarity = card.rarity
            cardItem.updatedAt = Date()

        }) { state in
            debugPrint("Created card item: \(state)")
        }
    }

    func deleteCard(by cardId: String) {
        guard let objectId = findCardObjectId(by: cardId) else {
            return
        }
        storage.performAndSave(context: storage.privateContext, block: { context in

            let cardItem = context.object(with: objectId)
            context.delete(cardItem)

        }) { state in
            debugPrint("Removed card item: \(state)")
        }
    }
}

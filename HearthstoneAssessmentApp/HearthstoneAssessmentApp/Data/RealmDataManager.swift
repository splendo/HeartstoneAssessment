//
//  RealmDataManager.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/12/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation
import RealmSwift

class RealmDataManager {
    
    let realm: Realm
    
    init() {
        self.realm = try! Realm()
    }
    
    // Persist objects in realm DB
    func saveCards(_ cards: [Card])  {
        try! realm.write {
            realm.add(cards)
        }
    }
    
    // Load objects from realm DB
    func loadCards() -> [Card]? {
        let results = realm.objects(Card.self)
        if results.count > 0 {
            return [Card](results)
        }
        return nil
    }
    
    func getCards(type: BasicType) -> [Card] {
        let results = realm.objects(Card.self).filter({
            $0.type == type.rawValue
        })
        if results.count > 0 {
            return [Card](results)
        }
        return [Card]()
    }
    
    func getCards(rarity: BasicRarity) -> [Card] {
        let results = realm.objects(Card.self).filter({
            $0.rarity == rarity.rawValue
        })
        if results.count > 0 {
            return [Card](results)
        }
        return [Card]()
    }
    
    func getCards(playerClass: BasicPlayerClass) -> [Card] {
        let results = realm.objects(Card.self).filter({
            $0.playerClass == playerClass.rawValue
        })
        if results.count > 0 {
            return [Card](results)
        }
        return [Card]()
    }
    
    func updateCard(card: Card) {
        try! realm.write {
            card.isFavorite = !card.isFavorite
            realm.add(card, update: true)
        }
    }
    
}

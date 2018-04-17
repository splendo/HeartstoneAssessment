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
    func loadCards() -> [Card] {
        let results = self.realm.objects(Card.self)
        let cards = [Card](results)
        return cards    
    }
    
    func getCards(_ filter: String) -> [Card] {
        if filter != DataConstants.ALL {
            let results = realm.objects(Card.self).filter({
                $0.type == filter ||
                $0.playerClass == filter ||
                $0.rarity == filter
            })
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

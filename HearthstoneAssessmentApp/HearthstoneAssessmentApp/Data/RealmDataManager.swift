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
        let cardResults = realm.objects(Card.self)
        if cardResults.count > 0 {
            return [Card](cardResults)
        }
        return nil
    }
    
    func updateCard(card: Card) {
        try! realm.write {
            realm.add(card, update: true)
        }
    }
    
}

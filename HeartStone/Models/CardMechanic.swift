//
//  CardMechanic.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 27..
//Copyright © 2017. Amberglass. All rights reserved.
//

import Foundation
import RealmSwift

class CardMechanic: Object
{
    override static func primaryKey() -> String? {
        return "mechanicName"
    }
    
    @objc dynamic var mechanicName = ""
}

extension CardMechanic
{
    class func allMechanics() -> [CardMechanic]
    {
        let realm           = try! Realm()
        let realmResults    = realm.objects(CardMechanic.self)
        var cards           : [CardMechanic] = []
        
        for card in realmResults
        {
            cards.append(card)
        }
        
        return cards
    }
    
    class func deleteAllMechanics()
    {
        let realm           = try! Realm()
        let realmResults    = realm.objects(CardMechanic.self)
        
        try! realm.write {
            realm.delete(realmResults)
        }
    }
}

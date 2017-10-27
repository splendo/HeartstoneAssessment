//
//  CardMechanicFactory.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 27..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import SwiftyJSON
import RealmSwift

class CardMechanicFactory
{
    static func createCardMechanic(fromJSON json: JSON) -> CardMechanic? {
        
        guard json != JSON.null,
            json["name"].exists() else
        {
            return nil
        }
        
        let realm                   = try! Realm()
        var cardMechanic            = CardMechanic()
        let cardMechanicName        = json["name"].stringValue
        cardMechanic.mechanicName   = cardMechanicName
        
        if let existingCardMechanic = realm.object(ofType: CardMechanic.self, forPrimaryKey: cardMechanicName)
        {
            cardMechanic = existingCardMechanic
        }
    
        realm.add(cardMechanic)
        
        return cardMechanic
    }
    
    static func createCardMechanicsList(fromJSON json: JSON) -> [CardMechanic]
    {
        return json.flatMap{
            return createCardMechanic(fromJSON: $0.1)
        }
    }
}

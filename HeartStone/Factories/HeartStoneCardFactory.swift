//
//  HeartStoneCardFactory.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 20..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import Foundation
import SwiftyJSON
import RealmSwift

class HeartStoneCardFactory
{
    static func createHeartStoneCard(fromJSON json: JSON) -> HeartStoneCard? {
        
        guard json != JSON.null,
            json["cardId"].exists() else
        {
            return nil
        }
        
        let realm           = try! Realm()
        var card            = HeartStoneCard()
        let cardId          = json["cardId"].stringValue
        
        try! realm.write {

            card.cardId         = cardId
            
            if let existingCard = realm.object(ofType: HeartStoneCard.self, forPrimaryKey: cardId)
            {
                card = existingCard
            }
            
            card.name           = json["name"].stringValue
            card.cardSet        = json["cardSet"].stringValue
            card.type           = json["type"].stringValue
            card.rarity         = json["rarity"].stringValue
            card.cost           = json["cost"].intValue
            card.attack         = json["attack"].intValue
            card.health         = json["health"].intValue
            card.text           = json["text"].stringValue
            card.flavor         = json["flavor"].stringValue
            card.artist         = json["artist"].stringValue
            card.collectible    = json["collectible"].boolValue
            card.elite          = json["elite"].boolValue
            card.playerClass    = json["playerClass"].stringValue
            card.howToGet       = json["howToGet"].stringValue
            card.howToGetGold   = json["howToGetGold"].stringValue
            card.img            = json["img"].stringValue
            card.imgGold        = json["imgGold"].stringValue
            card.locale         = json["locale"].stringValue
            
            if cardId == "EX1_029"
            {
                print(json["mechanics"])
            }
            
            let cardMechanics   = CardMechanicFactory.createCardMechanicsList(fromJSON: json["mechanics"])

            for cardMechanic in cardMechanics
            {
                card.mechanics.append(cardMechanic)
            }
        
            realm.add(card)
        }

        return card
    }
    
    static func createHeartStoneCardsList(fromJSON json: JSON) -> [HeartStoneCard]
    {
        return json.flatMap{
            return createHeartStoneCard(fromJSON: $0.1)
        }
    }
}

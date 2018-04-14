//
//  CardEntity+Extensions.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

extension Card {
         
    class func create(basicCard: BasicCard) -> Card {
        let card = Card()
        card.cardId = basicCard.cardID
        card.name = basicCard.name
        card.playerClass = basicCard.playerClass?.rawValue
        card.type = basicCard.type?.rawValue
        card.rarity = basicCard.type?.rawValue
        card.img = basicCard.img
        card.isFavorite = false
        card.isElite = basicCard.elite ?? false
        card.classes = basicCard.classes ?? [String]()
        if let mechanics = basicCard.mechanics {
            for mechanic in mechanics {
                let mechanic = MechanicCategory(name: mechanic.name?.rawValue)
                card.mechanics.append(mechanic)
            }
        }
        return card
    }
    
}

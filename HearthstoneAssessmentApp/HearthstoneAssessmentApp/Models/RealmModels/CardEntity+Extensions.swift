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
        card.rarity = basicCard.rarity?.rawValue
        card.img = basicCard.img
        card.isFavorite = false
        card.isElite = basicCard.elite ?? false
        card.classes = basicCard.classes ?? [String]()
        card.attack = basicCard.attack ?? 0
        card.health = basicCard.health ?? 0        
        return card
    }
    
}

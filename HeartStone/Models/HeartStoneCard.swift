//
//  HeartStoneCard.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 20..
//Copyright © 2017. Amberglass. All rights reserved.
//

import Foundation
import RealmSwift

class HeartStoneCard: Object
{
    override static func primaryKey() -> String? {
        return "cardId"
    }
    
    @objc dynamic var cardId        = ""
    @objc dynamic var name          = ""
    @objc dynamic var cardSet       = ""
    @objc dynamic var type          = ""
    @objc dynamic var rarity        = ""
    @objc dynamic var cost          = 0
    @objc dynamic var attack        = 0
    @objc dynamic var health        = 0
    @objc dynamic var text          = ""
    @objc dynamic var flavor        = ""
    @objc dynamic var artist        = ""
    @objc dynamic var collectible   = false
    @objc dynamic var elite         = false
    @objc dynamic var playerClass   = ""
    @objc dynamic var howToGet      = ""
    @objc dynamic var howToGetGold  = ""
    @objc dynamic var img           = ""
    @objc dynamic var imgGold       = ""
    @objc dynamic var locale        = ""
    let mechanics                   = List<CardMechanic>()
}

//
//  Card.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 26/7/22.
//

import Foundation

struct Card {

    var cardId: String?
    var name: String?
    var cardSet: String?
    var type: String?
    var rarity: String?
    var cost: Int?
    var attack: Int?
    var health: Int?
    var text: String?
    var flavor: String?
    var artist: String?
    var collectible: Bool?
    var elite: Bool?
    var playerClass: String?
    var multiClassGroup: String?
    var classes: [String]?
    var img: String?
    var imgGold: String?
    var locale: String?
    var mechanics: [String]?
    
    // Instantiation of a Card object for testing and internal purposes
    init(cardId: String? = nil, name: String? = nil, cardSet: String? = nil, type: String? = nil, rarity: String? = nil, cost: Int? = nil, attack: Int? = nil, health: Int? = nil, text: String? = nil, flavor: String? = nil, artist: String? = nil, collectible: Bool? = nil, elite: Bool? = nil, playerClass: String? = nil, multiClassGroup: String? = nil, classes: [String]? = nil, img: String? = nil, imgGold: String? = nil, locale: String? = nil, mechanics: [String]? = nil) {
        self.cardId = cardId
        self.name = name
        self.cardSet = cardSet
        self.type = type
        self.rarity = rarity
        self.cost = cost
        self.attack = attack
        self.health = health
        self.text = text
        self.flavor = flavor
        self.artist = artist
        self.collectible = collectible
        self.elite = elite
        self.playerClass = playerClass
        self.multiClassGroup = multiClassGroup
        self.classes = classes
        self.img = img
        self.imgGold = imgGold
        self.locale = locale
        self.mechanics = mechanics
    }
    
    //  JSON Serialization returns a Dictionary of card category arrays
    init(from dictionary: [String: Any]) {
        self.cardId = dictionary["cardId"] as? String
        self.name = dictionary["name"] as? String
        self.cardSet = dictionary["cardSet"] as? String
        self.type = dictionary["type"] as? String
        self.rarity = dictionary["rarity"] as? String
        self.cost = dictionary["cost"] as? Int
        self.attack = dictionary["attack"] as? Int
        self.health = dictionary["health"] as? Int
        self.text = dictionary["text"] as? String
        self.flavor = dictionary["flavor"] as? String
        self.artist = dictionary["artist"] as? String
        self.collectible = dictionary["collectible"] as? Bool
        self.elite = dictionary["elite"] as? Bool
        self.playerClass = dictionary["playerClass"] as? String
        self.multiClassGroup = dictionary["multiClassGroup"] as? String
        self.classes = dictionary["classes"] as? [String]
        self.img = dictionary["img"] as? String
        self.imgGold = dictionary["imgGold"] as? String
        self.locale = dictionary["locale"] as? String
        self.mechanics = dictionary["mechanics"] as? [String]
    }
    
}

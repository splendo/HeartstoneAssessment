//
//  Card.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 26/7/22.
//

import Foundation

struct Card {

    let cardId: String?
    let name: String?
    let cardSet: String?
    let type: String?
    let rarity: String?
    let cost: Int?
    let attack: Int?
    let health: Int?
    let text: String?
    let flavor: String?
    let artist: String?
    let collectible: Bool?
    let elite: Bool?
    let playerClass: String?
    let multiClassGroup: String?
    let classes: [String]?
    let img: String?
    let imgGold: String?
    let locale: String?
    let mechanics: [String]?
    
    //  JSON file returns a Dictionary in categories 
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

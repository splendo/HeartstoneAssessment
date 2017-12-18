//
//  Card.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

struct Card: Decodable {
    let cardId : String
    let name: String
    let cardSet: String
    let type: String
    let rarity: String
    let cost: Int
    let attack: Int
    let health: Int
    let text: String
    let flavor: String
    let artist: String
    let collectible: Bool
    let elite: Bool
    let playerClass: String
    let multiClassGroup: String?
    let howToGet: String?
    let howToGetGold: String?
    let img: String
    let imgGold: String
    let locale: String
    
    private enum CodingKeys: String, CodingKey {
        case cardId = "cardId"
        case name = "name"
        case cardSet = "cardSet"
        case type = "type"
        case rarity = "rarity"
        case cost = "cost"
        case attack = "attack"
        case health = "health"
        case text = "text"
        case flavor = "flavor"
        case artist = "artist"
        case collectible = "collectible"
        case elite = "elite"
        case playerClass = "playerClass"
        case multiClassGroup = "multiClassGroup"
        case howToGet = "howToGet"
        case howToGetGold = "howToGetGold"
        case img = "img"
        case imgGold = "imgGold"
        case locale = "locale"
    }
}

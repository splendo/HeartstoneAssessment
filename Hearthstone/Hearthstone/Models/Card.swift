//
//  Card.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 26/7/22.
//

import Foundation

// For the purpose of the DEMO we make the assumption that we know the Card Categories beforehand (if API requests, the setup would be different)
struct CardsResponse: Codable {
    var Basic: [Card] = Array()
    var Classic: [Card] = Array()
    var Promo: [Card] = Array()
    var HallofFame: [Card] = Array()
    var Naxxramas: [Card] = Array()
    var GoblinsvsGnomes: [Card] = Array()
    var BlackrockMountain: [Card] = Array()
    var TheGrandTournament: [Card] = Array()
    var TheLeagueofExplorers: [Card] = Array()
    var WhispersoftheOldGods: [Card] = Array()
    var OneNightinKarazhan: [Card] = Array()
    var MeanStreetsofGadgetzan: [Card] = Array()
    var JourneytoUnGoro: [Card] = Array()
    var TavernBrawl: [Card] = Array()
    var HeroSkins: [Card] = Array()
    var Missions: [Card] = Array()
    var Credits: [Card] = Array()
    var System: [Card] = Array()
    var Debug: [Card] = Array()
}

struct Card: Codable {

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
    var classes: [String]? = Array()
    var img: String?
    var imgGold: String?
    var locale: String?
    var mechanics: [[String: String]]? = Array()
    
}

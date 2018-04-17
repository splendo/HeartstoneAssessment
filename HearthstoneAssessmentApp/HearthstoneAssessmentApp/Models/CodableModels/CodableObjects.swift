//
//  BasicModel.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/10/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

struct RootCardsObject: Codable {
    let basic, classic: [BasicCard]?
    let promo: [JSONAny]?
    let hallOfFame, naxxramas, goblinsVsGnomes, blackrockMountain: [BasicCard]?
    let theGrandTournament, theLeagueOfExplorers, whispersOfTheOldGods, oneNightInKarazhan: [BasicCard]?
    let meanStreetsOfGadgetzan, journeyToUnGoro, tavernBrawl: [BasicCard]?
    let heroSkins: [Credit]?
    let missions: [BasicCard]?
    let credits: [Credit]?
    let system: [JSONAny]?
    let debug: [BasicCard]?
    
    enum CodingKeys: String, CodingKey {
        case basic = "Basic"
        case classic = "Classic"
        case promo = "Promo"
        case hallOfFame = "Hall of Fame"
        case naxxramas = "Naxxramas"
        case goblinsVsGnomes = "Goblins vs Gnomes"
        case blackrockMountain = "Blackrock Mountain"
        case theGrandTournament = "The Grand Tournament"
        case theLeagueOfExplorers = "The League of Explorers"
        case whispersOfTheOldGods = "Whispers of the Old Gods"
        case oneNightInKarazhan = "One Night in Karazhan"
        case meanStreetsOfGadgetzan = "Mean Streets of Gadgetzan"
        case journeyToUnGoro = "Journey to Un'Goro"
        case tavernBrawl = "Tavern Brawl"
        case heroSkins = "Hero Skins"
        case missions = "Missions"
        case credits = "Credits"
        case system = "System"
        case debug = "Debug"
    }
}

struct BasicCard: Codable {
    let cardID, name: String?
    let cardSet: BasicCardSet?
    let type: BasicType?
    let text: String?
    let playerClass: BasicPlayerClass?
    let locale: Locale?
    let mechanics: [Mechanic]?
    let faction: Faction?
    let artist: String?
    let rarity: BasicRarity?
    let cost: Int?
    let img, imgGold, flavor: String?
    let collectible: Bool?
    let attack, health: Int?
    let race: Race?
    let durability: Int?
    let elite: Bool?
    let howToGet, howToGetGold, multiClassGroup: String?
    let classes: [String]?
    
    enum CodingKeys: String, CodingKey {
        case cardID = "cardId"
        case name, cardSet, type, text, playerClass, locale, mechanics, faction, artist, rarity, cost, img, imgGold, flavor, collectible, attack, health, race, durability, elite, howToGet, howToGetGold, multiClassGroup, classes
    }
}

struct Mechanic: Codable {
    let name: Skill?
}

struct Credit: Codable {
    let cardID, name: String?
    let cardSet: CreditCardSet?
    let type: CreditType?
    let rarity: CreditRarity?
    let text: String?
    let elite: Bool?
    let playerClass: CreditPlayerClass?
    let img, imgGold: String?
    let locale: Locale?
    let cost, attack, health: Int?
    let race: String?
    let collectible: Bool?
    let faction: String?
    
    enum CodingKeys: String, CodingKey {
        case cardID = "cardId"
        case name, cardSet, type, rarity, text, elite, playerClass, img, imgGold, locale, cost, attack, health, race, collectible, faction
    }
}



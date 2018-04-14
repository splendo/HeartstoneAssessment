//
//  BasicModel.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/10/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation
import Alamofire

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

enum BasicCardSet: String, Codable {
    case basic = "Basic"
    case blackrockMountain = "Blackrock Mountain"
    case classic = "Classic"
    case debug = "Debug"
    case goblinsVsGnomes = "Goblins vs Gnomes"
    case hallOfFame = "Hall of Fame"
    case journeyToUnGoro = "Journey to Un'Goro"
    case meanStreetsOfGadgetzan = "Mean Streets of Gadgetzan"
    case missions = "Missions"
    case naxxramas = "Naxxramas"
    case oneNightInKarazhan = "One Night in Karazhan"
    case tavernBrawl = "Tavern Brawl"
    case theGrandTournament = "The Grand Tournament"
    case theLeagueOfExplorers = "The League of Explorers"
    case whispersOfTheOldGods = "Whispers of the Old Gods"
}

enum Faction: String, Codable {
    case alliance = "Alliance"
    case horde = "Horde"
    case neutral = "Neutral"
}

enum Locale: String, Codable {
    case enUS = "enUS"
}

struct Mechanic: Codable {
    let name: Skill?
}

enum Skill: String, Codable {
    case adapt = "Adapt"
    case adjacentBuff = "AdjacentBuff"
    case affectedBySpellPower = "AffectedBySpellPower"
    case aiMustPlay = "AIMustPlay"
    case aura = "Aura"
    case battlecry = "Battlecry"
    case charge = "Charge"
    case combo = "Combo"
    case deathrattle = "Deathrattle"
    case discover = "Discover"
    case divineShield = "Divine Shield"
    case enrage = "Enrage"
    case freeze = "Freeze"
    case immuneToSpellpower = "ImmuneToSpellpower"
    case inspire = "Inspire"
    case invisibleDeathrattle = "InvisibleDeathrattle"
    case jadeGolem = "Jade Golem"
    case morph = "Morph"
    case oneTurnEffect = "OneTurnEffect"
    case overload = "Overload"
    case poisonous = "Poisonous"
    case quest = "Quest"
    case secret = "Secret"
    case silence = "Silence"
    case spellDamage = "Spell Damage"
    case stealth = "Stealth"
    case summoned = "Summoned"
    case taunt = "Taunt"
    case windfury = "Windfury"
}

enum BasicPlayerClass: String, Codable {
    case deathKnight = "Death Knight"
    case dream = "Dream"
    case druid = "Druid"
    case hunter = "Hunter"
    case mage = "Mage"
    case neutral = "Neutral"
    case paladin = "Paladin"
    case priest = "Priest"
    case rogue = "Rogue"
    case shaman = "Shaman"
    case warlock = "Warlock"
    case warrior = "Warrior"
}

enum Race: String, Codable {
    case beast = "Beast"
    case demon = "Demon"
    case dragon = "Dragon"
    case elemental = "Elemental"
    case mech = "Mech"
    case murloc = "Murloc"
    case orc = "Orc"
    case pirate = "Pirate"
    case totem = "Totem"
}

enum BasicRarity: String, Codable {
    case common = "Common"
    case epic = "Epic"
    case free = "Free"
    case legendary = "Legendary"
    case rare = "Rare"
}

enum BasicType: String, Codable {
    case enchantment = "Enchantment"
    case hero = "Hero"
    case heroPower = "Hero Power"
    case minion = "Minion"
    case spell = "Spell"
    case weapon = "Weapon"
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

enum CreditCardSet: String, Codable {
    case credits = "Credits"
    case heroSkins = "Hero Skins"
}

enum CreditPlayerClass: String, Codable {
    case hunter = "Hunter"
    case mage = "Mage"
    case neutral = "Neutral"
    case paladin = "Paladin"
    case priest = "Priest"
    case rogue = "Rogue"
    case shaman = "Shaman"
    case warrior = "Warrior"
}

enum CreditRarity: String, Codable {
    case epic = "Epic"
    case free = "Free"
    case legendary = "Legendary"
}

enum CreditType: String, Codable {
    case hero = "Hero"
    case heroPower = "Hero Power"
    case minion = "Minion"
}


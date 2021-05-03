//
//  HearthstoneTypes.swift
//  HearthstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/16/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

enum BasicRarity: String, Codable, EnumCollection {
    case common = "Common"
    case epic = "Epic"
    case free = "Free"
    case legendary = "Legendary"
    case rare = "Rare"
}

enum BasicType: String, Codable, EnumCollection {
    case enchantment = "Enchantment"
    case hero = "Hero"
    case heroPower = "Hero Power"
    case minion = "Minion"
    case spell = "Spell"
    case weapon = "Weapon"
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

enum CreditCardSet: String, Codable {
    case credits = "Credits"
    case heroSkins = "Hero Skins"
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

enum Skill: String, Codable, EnumCollection {
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

enum BasicPlayerClass: String, Codable, EnumCollection {
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

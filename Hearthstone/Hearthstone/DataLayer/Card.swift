//
//  Card.swift
//  Hearthstone
//
//  Created by Dmitrii on 25/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import Foundation

class Card {

    let cardId: String
    let imgURL: String
    let name: String
    var cardSet: String?
    var health: Int?
    var playerClass: CardPlayerClass?
    var type: CardType?
    var cost: Int?
    var attack: Int?
    var text: String?
    var flavor: String?
    var artist: String?
    var howToGet: String?
    var rarity: CardRarity?
    var mechanics = [CardMechanic]()

    init(cardId: String, imgURL: String, name: String) {
        self.cardId = cardId
        self.imgURL = imgURL
        self.name = name
    }
}

enum CardPlayerClass: String {
    case Hunter = "Hunter"
    case Mage = "Mage"
    case Paladin = "Paladin"
    case Warrior = "Warrior"
    case Rogue = "Rogue"
    case Shaman = "Shaman"
    case Priest = "Priest"
    case Warlock = "Warlock"
    case Druid = "Druid"
    case Neutral = "Neutral"

    static let allValues = [Hunter, Mage, Paladin, Warrior, Rogue, Shaman, Priest, Warlock, Druid, Neutral]
}

enum CardType: String {
    case Hero = "Hero"
    case Weapon = "Weapon"
    case HeroPower = "Hero Power"
    case Spell = "Spell"
    case Minion = "Minion"

    static let allValues = [Hero, Weapon, HeroPower, Spell, Minion]
}

enum CardMechanicName: String {
    case Battlecry = "Battlecry"
    case Secret = "Secret"
    case Overload = "Overload"
    case JadeGolem = "Jade Golem"
    case Combo = "Combo"
    case Deathrattle = "Deathrattle"
    case Stealth = "Stealth"
    case Taunt = "Taunt"
    case Charge = "Charge"
    case Freeze = "Freeze"
    case Silence = "Silence"
    case Discover = "Discover"
    case DivineShield = "Divine Shield"
    case AffectedBySpellPower = "AffectedBySpellPower"

    static let allValues = [Battlecry, Secret, Overload, JadeGolem, Combo, Deathrattle, Stealth, Taunt, Charge, Freeze, Silence, Discover, DivineShield, AffectedBySpellPower]
}

enum CardRarity: String {
    case Common = "Common"
    case Epic = "Epic"
    case Free = "Free"
    case Legendary = "Legendary"
    case Rare = "Rare"

    static let allValues = [Common, Epic, Free, Legendary, Rare]
}

struct CardMechanic {
    let name: CardMechanicName
}

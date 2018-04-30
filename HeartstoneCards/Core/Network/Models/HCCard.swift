//
// Created by Roman Petryshen on 22/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation

public struct HCCardModel: Decodable {
    let cardId: String
    let name: String
    let img: String
    let imgGold: String?
    let rarity: String?
    let type: String?
    let playerClass: String?
    let cardSet: String?
}

//    "collectible": true,
//    "name": "Alleria Windrunner",
//    "img": "http://media.services.zam.com/v1/media/byName/hs/cards/enus/HERO_05a.png",
//    "locale": "enUS",
//    "rarity": "Epic",
//    "health": 30,
//    "cardId": "HERO_05a",
//    "imgGold": "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/HERO_05a_premium.gif",
//    "type": "Hero",
//    "playerClass": "Hunter",
//    "cardSet": "Hero Skins"

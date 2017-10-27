//
//  CardType.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 27..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import Foundation

enum CardType : String
{
    case basic                  = "Basic"
    case classic                = "Classic"
    case hallOfFame             = "Hall of Fame"
    case naxxramas              = "Naxxramas"
    case goblinsVsGnomes        = "Goblins vs Gnomes"
    case blackrockMountain      = "Blackrock Mountain"
    case theGrandTournament     = "The Grand Tournament"
    case theLeagueOfExplorers   = "The League of Explorers"
    case whispersOfTheOldGods   = "Whispers of the Old Gods"
    case oneNightInKarazhan     = "One Night in Karazhan"
    case meanStreetsOfGadgetzan = "Mean Streets of Gadgetzan"
    case journeyToUnGoro        = "Journey to Un'Goro"
    case tavernBrawl            = "Tavern Brawl"
    case heroSkins              = "Hero Skins"
    case missions               = "Missions"
}

extension CardType
{
    static let allTypes : [CardType] = [.basic,
                                        .classic,
                                        .hallOfFame,
                                        .naxxramas,
                                        .goblinsVsGnomes,
                                        .blackrockMountain,
                                        .theGrandTournament,
                                        .theLeagueOfExplorers,
                                        .whispersOfTheOldGods,
                                        .oneNightInKarazhan,
                                        .meanStreetsOfGadgetzan,
                                        .journeyToUnGoro,
                                        .tavernBrawl,
                                        .heroSkins,
                                        .missions]
}

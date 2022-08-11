//
//  CardsHelper.swift
//  HearthstoneTests
//
//  Created by Stavros Tsikinas on 28/7/22.
//

import Foundation
@testable import Hearthstone

///
/// Initializers for dummy cards.
///
func testCard(name: String? = nil, img: String? = nil, rarity: String? = nil, mechanics: [[String: String]]? = nil) -> Card {
    Card(cardId: nil, name: name, cardSet: nil, type: nil, rarity: rarity, cost: nil, attack: nil, health: nil, text: nil, flavor: nil, artist: nil, collectible: nil, elite: nil, playerClass: nil, multiClassGroup: nil, classes: nil, img: img, imgGold: nil, locale: nil, mechanics: mechanics)
}

func testCardArray() -> [Card] {
    var cards = [Card]()
    for i in 0...5 {
        
        cards.append(testCard(name: "Card #\(i)",
                              img: "a://whatever.image",
                              rarity: i % 2 == 0 ? "Legendary" : "Basic",
                              mechanics: i % 2 == 0 ? [["name": "Deathrattle"]] : nil
                             ))
    }
    
    return cards
}
 
///
/// Test helper method of retrieving the json url.
///
func getUrlFile(from name: String?, typed: String?) -> URL? {
    Bundle.main.url(forResource: name, withExtension: typed)
}

///
/// Test helper method of converting url to json string.
///
func getJSONString(from url: URL) throws -> String? {
    
    do {
        return try String(contentsOf: url, encoding: .utf8)
    } catch {
        return nil
    }
    
}

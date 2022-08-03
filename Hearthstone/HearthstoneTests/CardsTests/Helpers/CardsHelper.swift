//
//  CardsHelper.swift
//  HearthstoneTests
//
//  Created by Stavros Tsikinas on 28/7/22.
//

import Foundation
@testable import Hearthstone

///
/// Initializer for dummy cards.
///
func testCard(name: String? = nil, img: String? = nil) -> Card {
    Card(from: ["name": name as Any, "img": img as Any])
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

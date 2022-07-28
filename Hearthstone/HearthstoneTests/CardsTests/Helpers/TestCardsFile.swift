//
//  TestCardsFileExists.swift
//  HearthstoneTests
//
//  Created by Epsilon User on 27/7/22.
//

import XCTest
@testable import Hearthstone

class TestCardsFile: XCTestCase {

    override func setUpWithError() throws {
        super.setUp()
    }
    
    func testJSONFileExists() {
        guard let _ = getUrlFile(from: "cards", typed: "json") else {
            XCTFail("File is Missing")
            return
        }
    }
    
    func testJSONFileIsValid() {
        guard let cardsUrl = getUrlFile(from: "cards", typed: "json") else {
            XCTFail("File is missing")
            return
        }
        
        do {
            if let cardsJSON = try getJSONString(from: cardsUrl) {
                guard let cardsData = cardsJSON.data(using: .utf8) else {
                    XCTFail("Cards data can't be created from JSON")
                    return
                }
                
                guard let cardsDictionary = try JSONSerialization.jsonObject(with: cardsData) as? [String: Any] else {
                    XCTFail("Cards Dictionary can't be created from Data object")
                    return
                }
                
                XCTAssertTrue(JSONSerialization.isValidJSONObject(cardsDictionary))
            } else {
                XCTFail("Can't parse cards.json into JSON string")
            }
        } catch {
            XCTFail("Cards data can't be created")
        }
    }

}

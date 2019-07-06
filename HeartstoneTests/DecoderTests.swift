//
//  DecoderTests.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 05/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import XCTest
@testable import Heartstone

class DecoderTests: XCTestCase {

    let testData = [
        "Some group 1": [
            [
                "cardId": "1",
                "name": "name 1",
                "type": "type 1"
            ]],
        "Some group 2": [
            [
                "cardId": "2",
                "name": "name 2",
                "type": "type 2",
                "img": "http://aaa.bb/cc.jpg",
                "rarity": "foo",
                "classes": ["1", "2", "3"],
                "mechanics": [
                    [
                        "name": "real"
                    ],
                    [
                        "name": "unreal"
                    ]
                ]
            ],
        ]
    ]

    let invalidData = [
        "aa": [ "bb": [ "cc" ] ]
    ]

    func testDecoder() {
        do {
            let data = try JSONSerialization.data(withJSONObject: testData, options: [])
            let decoded = try JSONDecoder().decode(CardCollection.self, from: data)
            XCTAssertEqual(decoded.cards.count, 2)
        } catch {
            XCTFail(error.localizedDescription)
        }
    }

    func testCardProperties() {
        do {
            let data = try JSONSerialization.data(withJSONObject: testData, options: [])
            let decoded = try JSONDecoder().decode(CardCollection.self, from: data)
            XCTAssertEqual(decoded.cards.count, 2)
            guard let card = decoded.cards.first(where: { $0.cardId == "2" }) else {
                return XCTFail("Can't find Card with id '2'")
            }
            XCTAssertEqual(card.name, "name 2")
            XCTAssertEqual(card.type, "type 2")
            XCTAssertEqual(card.rarity, "foo")
            XCTAssertEqual(card.img, "http://aaa.bb/cc.jpg")
            XCTAssertEqual(card.classes?.sorted(), ["1", "2", "3"])
            XCTAssertNotNil(card.mechanics?.first(where: {$0.name == "unreal" }))
            XCTAssertNotNil(card.mechanics?.first(where: {$0.name == "real" }))
        } catch {
            XCTFail(error.localizedDescription)
        }
    }

    func testInvalidData() {
        guard let data = try? JSONSerialization.data(withJSONObject: invalidData, options: []) else {
            return XCTFail("Can't create Data from invalidData dictionary")
        }
        XCTAssertThrowsError(try JSONDecoder().decode(CardCollection.self, from: data))
    }
}

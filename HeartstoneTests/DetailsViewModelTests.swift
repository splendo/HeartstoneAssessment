//
//  DetailsViewModelTests.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 05/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import XCTest
@testable import Heartstone

class DetailsViewModelTests: XCTestCase {

    func testNotFavorite() {
        let card = Card(
            cardId: "123",
            name: "name",
            type: "type",
            img: nil,
            text: nil,
            rarity: "Very",
            classes: nil,
            mechanics: nil
        )
        let model = DetailsViewModel(card: card, isFavorite: false)
        XCTAssertEqual(model.cardId, "123")
        XCTAssertEqual(model.name, "name")
        XCTAssertEqual(model.text, "Rarity: Very")
        XCTAssertNil(model.imageURL)
        XCTAssertFalse(model.isFavorite)
        XCTAssertEqual(model.favoriteButtonTitle, "Add to Favorites")
    }

    func testFavorite() {
        let card = Card(
            cardId: "123",
            name: "name",
            type: "type",
            img: "http://aaa.bb/c.jpg",
            text: nil,
            rarity: nil,
            classes: nil,
            mechanics: nil
        )
        let model = DetailsViewModel(card: card, isFavorite: true)
        XCTAssertEqual(model.cardId, "123")
        XCTAssertEqual(model.name, "name")
        XCTAssertEqual(model.text, "Rarity: Unknown")
        XCTAssertEqual(model.imageURL?.absoluteString, "http://aaa.bb/c.jpg")
        XCTAssertTrue(model.isFavorite)
        XCTAssertEqual(model.favoriteButtonTitle, "Remove from Favorites")
    }
}

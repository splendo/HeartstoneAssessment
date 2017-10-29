//
//  CardsCollectionViewModelTest.swift
//  HearthstoneTests
//
//  Created by Dmitrii on 29/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import XCTest

class CardsCollectionViewModelTest: XCTestCase {

    var modelToTest: CardsCollectionViewModel!
    var dataServiceMock: DataServiceMock!

    override func setUp() {
        super.setUp()
        dataServiceMock = DataServiceMock()
        modelToTest = CardsCollectionViewModel(dataService: dataServiceMock)
    }

    func testInitialState() {
        XCTAssertEqual(modelToTest.cardsAmount(), 0)
        XCTAssertNil(modelToTest.card(index: 0))
    }

    func testRefresh() {
        modelToTest.refresh()
        XCTAssertEqual(modelToTest.cardsAmount(), 3)
        XCTAssertEqual(modelToTest.card(index: 0)!.cardId, "1")
        XCTAssertEqual(modelToTest.card(index: 1)!.cardId, "2")
        XCTAssertEqual(modelToTest.card(index: 2)!.cardId, "3")
    }

    func testRefreshTypeFilter() {
        var typeFilter = CardsCollectionFilter()
        typeFilter.type = .Hero
        modelToTest.currentFilter = typeFilter
        modelToTest.refresh()
        XCTAssertEqual(modelToTest.cardsAmount(), 1)
        XCTAssertEqual(modelToTest.card(index: 0)!.cardId, "3")
    }

    func testRefreshPlayerClassFilter() {
        var classFilter = CardsCollectionFilter()
        classFilter.playerClass = .Warlock
        modelToTest.currentFilter = classFilter
        modelToTest.refresh()
        XCTAssertEqual(modelToTest.cardsAmount(), 2)
        XCTAssertEqual(modelToTest.card(index: 0)!.cardId, "2")
        XCTAssertEqual(modelToTest.card(index: 1)!.cardId, "3")
    }

    func testRefreshPlayerClassRarityFilter() {
        var filter = CardsCollectionFilter()
        filter.playerClass = .Warlock
        filter.rarity = .Legendary
        modelToTest.currentFilter = filter
        modelToTest.refresh()
        XCTAssertEqual(modelToTest.cardsAmount(), 1)
        XCTAssertEqual(modelToTest.card(index: 0)!.cardId, "2")
    }
}


class DataServiceMock: DataService {
    override func cards() -> [Card] {

        let card1 = Card(cardId: "1", imgURL: "", name: "1")
        card1.type = .Minion
        card1.playerClass = .Priest
        card1.rarity = .Epic

        let card2 = Card(cardId: "2", imgURL: "", name: "2")
        card2.type = .Weapon
        card2.playerClass = .Warlock
        card2.rarity = .Legendary

        let card3 = Card(cardId: "3", imgURL: "", name: "3")
        card3.type = .Hero
        card3.playerClass = .Warlock
        card3.rarity = .Free

        return [card1, card2, card3]
    }
}

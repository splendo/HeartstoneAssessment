//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import XCTest
@testable import HearthstoneHelper

class CardCellInteractorTests: XCTestCase {
    var presenter: MockCardCellPresenter!
    var sut: CardCellInteractor!

    override func setUp() {
        super.setUp()

        presenter = MockCardCellPresenter()
        sut = CardCellInteractor(presenter: presenter)
    }

    func test_updateCard_presentsLoading() {
        sut.updateCard(from: Card(
                cardId: "id",
                name: "name",
                type: "type",
                rarity: nil,
                mechanics: nil,
                img: nil,
                playerClass: nil))

        XCTAssertEqual(presenter.presentLoadingCallCount, 1)
    }

    func test_updateCard_withNoImageURL_presentsNoImage() {
        sut.updateCard(from: Card(
                cardId: "id",
                name: "name",
                type: "type",
                rarity: nil,
                mechanics: nil,
                img: nil,
                playerClass: nil))
        
        XCTAssertEqual(presenter.presentNoImageCallCount, 1)
    }
    
    // todo: tests when networking is mocked
}
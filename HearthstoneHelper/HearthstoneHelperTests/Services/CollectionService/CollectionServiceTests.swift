//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import XCTest
@testable import HearthstoneHelper

class CollectionServiceTests: XCTestCase {
    let fetchingExpectationTimeout = 1.0

    var collectionRetriever: MockCollectionRetriever!
    var sut: CollectionService!

    override func setUp() {
        super.setUp()

        collectionRetriever = MockCollectionRetriever()
        sut = CollectionService(retriever: collectionRetriever)
    }

    func test_getCollection_defaultOrder_sortsAscending() {
        let cardWithName: (String) -> Card = { name in
            Card(cardId: "id", name: name, type: "type", rarity: nil, mechanics: nil, img: nil, playerClass: nil)
        }

        let fetchingExpectation = expectation(description: "Wait for collection fetching")

        let inputCards = [cardWithName("b"), cardWithName("a")]
        collectionRetriever.retrievalResult = .success(inputCards)

        sut.getCollection { result in
            switch (result) {

            case .success(let cards):
                let cardNames = cards.map { $0.name }
                let sortedInput = inputCards.map { $0.name }.sorted(by: <)
                
                XCTAssertEqual(cardNames, sortedInput)
            case .failure(let error):
                XCTFail("Unexpected error during fetching: \(error)")
            }
            fetchingExpectation.fulfill()
        }

        wait(for: [fetchingExpectation], timeout: fetchingExpectationTimeout)
    }
}

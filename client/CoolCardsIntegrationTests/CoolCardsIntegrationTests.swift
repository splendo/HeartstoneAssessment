//
//  CoolCardsAPIIntegrationTests.swift
//  CoolCardsIntegrationTests
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import XCTest
@testable import CoolCards

class CoolCardsAPIIntegrationTests: XCTestCase {
    
    func testGetCards() {
        let cardsExpectation = expectation(description: "We should some cards")
        CoolCardsAPI.getCards(completion: { cards, error in
            XCTAssert(error == nil)
            XCTAssert(cards !=  nil)
            XCTAssert(cards!.count > 0)
            cardsExpectation.fulfill()
        })
        waitForExpectations(timeout: 10, handler: { error in
            XCTAssertNil(error)
        })
    }
    
    func testGetCard() {
        let cardExpectation = expectation(description: "We should get a card")
        let cardId = "1"
        CoolCardsAPI.getCard(cardId: cardId, completion: { card, error in
            XCTAssert(error == nil)
            XCTAssert(card != nil)
            XCTAssert(card!.cardId == cardId)
            cardExpectation.fulfill()
        })
        waitForExpectations(timeout: 10, handler: { error in
            XCTAssertNil(error)
        })
    }

}

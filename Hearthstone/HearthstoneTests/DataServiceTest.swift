//
//  DataServiceTest.swift
//  HearthstoneTests
//
//  Created by Dmitrii on 29/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import XCTest

class DataServiceTest: XCTestCase {

    var dataServiceToTest: DataService!

    var apiClientMock: APIClientMock!
    var notificationCenterMock: NotificationCenterMock!

    override func setUp() {
        super.setUp()
        apiClientMock = APIClientMock()
        notificationCenterMock = NotificationCenterMock()
        dataServiceToTest = DataService(
            apiClient: apiClientMock,
            notificationCenter: notificationCenterMock
        )
    }

    func testLoadCardsSuccess() {
        apiClientMock.successLoad = true
        let exp = expectation(description: "testLoadCardsSuccess")
        dataServiceToTest.loadCards {
            let cards = self.dataServiceToTest.cards()
            XCTAssertNotNil(cards)
            XCTAssertEqual(cards.count, 3)
            XCTAssertEqual(cards[0].name, "1")
            XCTAssertEqual(cards[1].name, "2")
            XCTAssertEqual(cards[2].name, "3")
            XCTAssertTrue(self.notificationCenterMock.postCalled)
            XCTAssertEqual(self.notificationCenterMock.name, dataServiceCardsUpdatedNotification)
            XCTAssertNotNil(self.notificationCenterMock.userInfo)
            XCTAssertNil(self.notificationCenterMock.userInfo![dataServiceCardsUpdatedNotificationErrorKey])
            exp.fulfill()
        }
        waitForExpectations(timeout: 2.0, handler: nil)
    }

    func testLoadCardsFailure() {
        apiClientMock.successLoad = false
        let exp = expectation(description: "testLoadCardsSuccess")
        dataServiceToTest.loadCards {
            let cards = self.dataServiceToTest.cards()
            XCTAssertNotNil(cards)
            XCTAssertEqual(cards.count, 0)
            XCTAssertTrue(self.notificationCenterMock.postCalled)
            XCTAssertEqual(self.notificationCenterMock.name, dataServiceCardsUpdatedNotification)
            XCTAssertNotNil(self.notificationCenterMock.userInfo)
            XCTAssertNotNil(self.notificationCenterMock.userInfo![dataServiceCardsUpdatedNotificationErrorKey])
            exp.fulfill()
        }
        waitForExpectations(timeout: 2.0, handler: nil)
    }
}


class APIClientMock: APIClient {

    var successLoad = true

    func cards() -> [Card] {

        let card1 = Card(cardId: "3", imgURL: "", name: "3")
        card1.type = .Minion
        card1.playerClass = .Priest
        card1.rarity = .Epic

        let card2 = Card(cardId: "2", imgURL: "", name: "2")
        card2.type = .Weapon
        card2.playerClass = .Warlock
        card2.rarity = .Legendary

        let card3 = Card(cardId: "1", imgURL: "", name: "1")
        card3.type = .Hero
        card3.playerClass = .Warlock
        card3.rarity = .Free

        return [card1, card2, card3]
    }

    override func loadCards(completion: @escaping RequestCompletion) {
        DispatchQueue.global().async {
            if self.successLoad {
                completion(self.cards(), nil)
            } else {
                let error = NSError(domain: "", code: 0, userInfo: nil)
                completion(nil, error)
            }
        }
    }
}


class NotificationCenterMock: NotificationCenterProtocol {

    var postCalled = false
    var name: NSNotification.Name?
    var object: Any?
    var userInfo: [AnyHashable : Any]?

    func post(name: NSNotification.Name, object: Any?, userInfo: [AnyHashable : Any]? = nil) {
        postCalled = true
        self.name = name
        self.object = object
        self.userInfo = userInfo
    }
}

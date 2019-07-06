//
//  DetailsCoordinatorTests.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 06/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import XCTest
import CoreData
@testable import Heartstone

class DetailsCoordinatorTests: XCTestCase {

    var dependency: MockDependency!
    var coordinator: DetailsCoordinator<MockDependency>!
    let card = Card.mockCard()

    var mockStorage: MockCardStorage {
        return dependency.cardStorage as! MockCardStorage
    }

    override func setUp() {
        super.setUp()

        let navigation = UINavigationController(rootViewController: UIViewController())
        dependency = MockDependency()
        coordinator = DetailsCoordinator(
            dependency: dependency,
            navigation: navigation,
            card: card
        )
    }

    override func tearDown() {
        coordinator = nil
        dependency = nil
        super.tearDown()
    }

    func testInit() {
        XCTAssertNotNil(dependency)
        XCTAssertNotNil(coordinator)
    }

    func testMarkAsFavorite() {
        XCTAssertFalse(mockStorage.isCreateCardCalled)
        XCTAssertFalse(mockStorage.isDeleteCardCalled)
        coordinator.onToggleFavorite(UIViewController())
        XCTAssertTrue(mockStorage.isCreateCardCalled)
        XCTAssertFalse(mockStorage.isDeleteCardCalled)
    }

    func testRemoveFromFavorite() {
        XCTAssertFalse(mockStorage.isCreateCardCalled)
        XCTAssertFalse(mockStorage.isDeleteCardCalled)

        let expectation = self.expectation(description: "Created")
        var cardItem: CardItem?

        dependency.cardStorage.storage.performAndSave(context: dependency.cardStorage.storage.privateContext, block: { context in

            let entity = NSEntityDescription.entity(forEntityName: "CardItem", in: context)!
            cardItem = CardItem.init(entity: entity, insertInto: context)

            cardItem?.cardId = "123"
            cardItem?.name = "name"
            cardItem?.img = "img"
            cardItem?.type = "type"
            cardItem?.rarity = "rarity"
            cardItem?.updatedAt = Date()

        }) { _ in
            expectation.fulfill()
        }

        wait(for: [expectation], timeout: 5)
        mockStorage.findCardValue = cardItem
        coordinator.onToggleFavorite(UIViewController())
        XCTAssertFalse(mockStorage.isCreateCardCalled)
        XCTAssertTrue(mockStorage.isDeleteCardCalled)
    }
}

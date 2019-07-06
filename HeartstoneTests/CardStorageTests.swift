//
//  CardStorageTests.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 05/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import XCTest
import CoreData
@testable import Heartstone

class CardStorageTests: XCTestCase {

    var cardStorage: CardStorage!
    var expectation: XCTestExpectation?

    override func setUp() {
        super.setUp()
        cardStorage = CoreDataCardStorage(storage: Storage(container: MockDependency.mockPersistentContainer()))
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(contextDidSave(_:)),
            name: NSNotification.Name.NSManagedObjectContextDidSave,
            object: cardStorage.storage.privateContext
        )
    }

    override func tearDown() {
        let fetchAll: NSFetchRequest<CardItem> = CardItem.fetchRequest()
        cardStorage.storage.mainContext.performAndSave { context in
            try? context.fetch(fetchAll).forEach {
                context.delete($0)
            }
        }
        cardStorage = nil
        super.tearDown()
    }

    func contextDidSave(_ notification: Notification) {
        expectation?.fulfill()
    }

    func testInit() {
        XCTAssertNotNil(cardStorage)
    }

    func testEmpty() {
        XCTAssertFalse(cardStorage.isExists(cardId: "123"))
    }

    func testCreate() {
        XCTAssertFalse(cardStorage.isExists(cardId: "card-id"))
        let card = Card(cardId: "card-id", name: "some name", type: "type", img: nil, text: nil, rarity: nil, classes: nil, mechanics: nil)
        expectation = self.expectation(description: "Created")
        cardStorage.createCard(from: card)
        wait(for: [expectation!], timeout: 5)

        XCTAssertTrue(cardStorage.isExists(cardId: "card-id"))
    }

    func testDelete() {
        // Not exists
        XCTAssertFalse(cardStorage.isExists(cardId: "card-id"))
        let card = Card(cardId: "card-id", name: "some name", type: "type", img: nil, text: nil, rarity: nil, classes: nil, mechanics: nil)
        expectation = self.expectation(description: "Created")
        cardStorage.createCard(from: card)
        wait(for: [expectation!], timeout: 5)
        // Exists
        XCTAssertTrue(cardStorage.isExists(cardId: "card-id"))
        expectation = self.expectation(description: "Delete")
        cardStorage.deleteCard(by: "card-id")
        wait(for: [expectation!], timeout: 5)
        // Not exists again
        XCTAssertFalse(cardStorage.isExists(cardId: "card-id"))
    }
}

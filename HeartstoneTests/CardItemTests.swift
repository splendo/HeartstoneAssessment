//
//  CardItemTests.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 05/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import XCTest
import CoreData
@testable import Heartstone

class CardItemTests: XCTestCase {

    var storage: Storage!

    override func setUp() {
        super.setUp()
        storage = Storage(name: "Heartstone")
    }

    override func tearDown() {
        let fetchAll: NSFetchRequest<CardItem> = CardItem.fetchRequest()
        storage.mainContext.performAndSave { context in
            try? context.fetch(fetchAll).forEach {
                context.delete($0)
            }
        }
        storage = nil
        super.tearDown()
    }

    func testInit() {
        XCTAssertNotNil(storage)
    }

    func testCreateCardItem() {
        let expectation = self.expectation(description: "Created")
        storage.performAndSave(context: storage.privateContext, block: { context in

            let entity = NSEntityDescription.entity(forEntityName: "CardItem", in: context)!
            let cardItem = CardItem.init(entity: entity, insertInto: context)

            cardItem.cardId = "123"
            cardItem.name = "name"
            cardItem.img = "img"
            cardItem.type = "type"
            cardItem.rarity = "rarity"
            cardItem.updatedAt = Date()

        }) { [weak self] state in

            let fetchAll: NSFetchRequest<CardItem> = CardItem.fetchRequest()
            self?.storage.mainContext.performAndSave { context in
                do {
                    let results = try context.fetch(fetchAll)
                    XCTAssertEqual(results.count, 1)
                    guard let cardItem = results.first else {
                        XCTFail()
                        return
                    }
                    let card = cardItem.toCard()
                    XCTAssertEqual(card.cardId, "123")
                    XCTAssertEqual(card.name, "name")
                    XCTAssertEqual(card.img, "img")
                    XCTAssertEqual(card.type, "type")
                    XCTAssertEqual(card.rarity, "rarity")
                } catch {
                    XCTFail()
                }
                expectation.fulfill()
            }
        }
        wait(for: [expectation], timeout: 5)
    }
}

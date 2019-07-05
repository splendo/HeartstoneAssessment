//
//  FavoritesCoordinatorTests.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 05/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import XCTest
import CoreData
@testable import Heartstone

class FavoritesCoordinatorTests: XCTestCase {

    var dependency: MockDependency!
    var coordinator: FavoritesCoordinator<MockDependency>!

    override func setUp() {
        super.setUp()
        dependency = MockDependency()
        coordinator = FavoritesCoordinator(dependency: dependency)
    }

    override func tearDown() {
        let fetchAll: NSFetchRequest<CardItem> = CardItem.fetchRequest()
        dependency.cardStorage.storage.mainContext.performAndSave { context in
            try? context.fetch(fetchAll).forEach {
                context.delete($0)
            }
        }
        coordinator = nil
        dependency = nil
        super.tearDown()
    }

    func testInit() {
        XCTAssertNotNil(dependency)
        XCTAssertNotNil(coordinator)
    }

    func testStart() {
        coordinator.start()
        XCTAssertTrue(coordinator.navigationViewController.visibleViewController is FavoritesCollectionViewController)
    }

    func testDetails() {
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

        }) { state in
            expectation.fulfill()
        }
        wait(for: [expectation], timeout: 5)
        guard let item = cardItem else {
            XCTFail()
            return
        }
        coordinator.start()
        coordinator.didSelectCardItem(item)
        XCTAssertNotNil(coordinator.childCoordinators.first(where: { $0 is DetailsCoordinator }))
    }
}

//
//  HomeCoordinatorTests.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 05/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import XCTest
@testable import Heartstone

class HomeCoordinatorTests: XCTestCase {

    var dependency: MockDependency!
    var coordinator: HomeCoordinator<MockDependency>!

    override func setUp() {
        super.setUp()
        dependency = MockDependency()
        coordinator = HomeCoordinator(dependency: dependency)
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

    func testLoadingScreen() {
        let expectation = self.expectation(description: "Data Fetched")
        let card = Card(cardId: "card-id", name: "some name", type: "type", img: nil, text: nil, rarity: nil, classes: nil, mechanics: nil)
        let mock = (dependency.dataProvider as! MockDataProvider)
        mock.onFetch = { completion in
            completion(.success([card]))
            DispatchQueue.main.async {
                expectation.fulfill()
            }
        }
        coordinator.start()
        let firstVisible = coordinator.navigationViewController.visibleViewController
        XCTAssertTrue(firstVisible is LoadingViewController)
        wait(for: [expectation], timeout: 2)
        let secondVisible = coordinator.navigationViewController.visibleViewController
        XCTAssertTrue(secondVisible is CardsCollectionViewController)
    }

    func testErrorScreen() {
        let expectation = self.expectation(description: "Data Fetched")
        let mock = (dependency.dataProvider as! MockDataProvider)
        mock.onFetch = { completion in
            completion(.success([]))
            DispatchQueue.main.async {
                expectation.fulfill()
            }
        }
        coordinator.start()
        let firstVisible = coordinator.navigationViewController.visibleViewController
        XCTAssertTrue(firstVisible is LoadingViewController)
        wait(for: [expectation], timeout: 2)
        let secondVisible = coordinator.navigationViewController.visibleViewController
        XCTAssertTrue(secondVisible is ErrorViewController)
    }

    func testShowDetails() {
        let card = Card(cardId: "card-id", name: "some name", type: "type", img: nil, text: nil, rarity: nil, classes: nil, mechanics: nil)
        coordinator.start()
        coordinator.didSelectCard(card)
        XCTAssertNotNil(coordinator.childCoordinators.first(where: { $0 is DetailsCoordinator }))
    }
}

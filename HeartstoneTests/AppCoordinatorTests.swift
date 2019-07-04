//
//  AppCoordinatorTests.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import XCTest
@testable import Heartstone

class AppCoordinatorTests: XCTestCase {

    var dependency: MockDependency!
    var coordinator: AppCoordinator<MockDependency>!

    override func setUp() {
        super.setUp()
        dependency = MockDependency()
        coordinator = AppCoordinator(dependency: dependency)
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

    func testStart() {
        coordinator.start()

        // Check total count
        XCTAssertEqual(coordinator.childCoordinators.count, 2)

        // Home is created
        XCTAssertNotNil(
            coordinator.childCoordinators.first(where: { $0 is HomeCoordinator })
        )

        // And Favorites
        XCTAssertNotNil(
            coordinator.childCoordinators.first(where: { $0 is FavoritesCoordinator })
        )
    }

    func testStop() {
        coordinator.start()
        XCTAssertFalse(coordinator.childCoordinators.isEmpty)
        coordinator.stop()
        XCTAssertTrue(coordinator.childCoordinators.isEmpty)
    }
}

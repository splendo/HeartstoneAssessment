//
//  TestTabNavigation.swift
//  HearthstoneUITests
//
//  Created by Epsilon User on 29/8/22.
//

import XCTest

class TestTabNavigation: XCTestCase {
    let app = XCUIApplication()

    override func setUpWithError() throws {
        try super.setUpWithError()
        
        app.launch()
        continueAfterFailure = false
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
    }

    func testInitialTabSelected() {
        let tabBar = app.tabBars.firstMatch
        if tabBar.exists {
            XCTAssertTrue(app.staticTexts["Cards"].exists, "Cards tab should've been selected")
        }
    }
    
    func testChangeTabSelected() {
        let tabBar = app.tabBars.firstMatch
        if tabBar.exists {
            tabBar.buttons["Favorites"].tap()
            XCTAssertTrue(app.staticTexts["Favorites"].exists, "Favorites tab should've been selected")
        }
    }
}

//
//  TestWatermark.swift
//  HearthstoneUITests
//
//  Created by Stavros Tsikinas on 29/8/22.
//

import XCTest

class TestWatermark: XCTestCase {

    let app = XCUIApplication()

    override func setUpWithError() throws {
        try super.setUpWithError()
        continueAfterFailure = false
        app.launch()
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
    }

    func testWatermarkImageOnEmptyView() {
        let tabBar = app.tabBars.firstMatch
        if tabBar.exists {
            tabBar.buttons["Favorites"].tap()
            XCTAssertTrue(app.staticTexts["Favorites"].exists, "Favorites tab should've been selected")
            let collectionView = app.collectionViews.element.firstMatch
            if collectionView.cells.count == 0 {
                XCTAssertTrue(collectionView.images["oops"].waitForExistence(timeout: 3.0))
            }
        }
    }
}

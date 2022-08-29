//
//  TestNavigationToCard.swift
//  HearthstoneUITests
//
//  Created by Stavros Tsikinas on 29/8/22.
//

import XCTest

class TestNavigationToCard: XCTestCase {

    let app = XCUIApplication()
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        app.launch()
        continueAfterFailure = false
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
    }

    func testMoveToFirstCardView() {
        
        
        /// Tap on first cell (titled: AFK)
        let indexPath = IndexPath(row: 0, section: 0)
        
        //XCTAssertEqual(app.collectionViews.count, 1)
        
        XCTAssertTrue(app.staticTexts["Cards"].exists)
        let collectionView = app.collectionViews.element.firstMatch
        if collectionView.cells.count > 0 {
            collectionView.cells.element(boundBy: indexPath.row).tap()
            let title = NSPredicate(format: "label BEGINSWITH 'AFK'")
            app.staticTexts.element(matching: title)
            app.navigationBars.buttons["Cards"].tap()
        }
    }
    
    func testAddFavoriteButton() {
        
        let indexPath = IndexPath(row: 2, section: 0)
        
        XCTAssertTrue(app.staticTexts["Cards"].exists)
        let collectionView = app.collectionViews.element.firstMatch
        if collectionView.cells.count > 0 {
            // Move to detail view
            collectionView.cells.element(boundBy: indexPath.row).tap()
            // Tap add to Favorites
            app.navigationBars.buttons["addToFavorites"].tap()
            
            // Validate save successful
            let alert = app.alerts.firstMatch
            let alertExpectation = alert.waitForExistence(timeout: 10)
            XCTAssertTrue(alertExpectation, "Added to Favorites alert should've been present")
            let alertDescription = alert.staticTexts["Card added to Favorites"]
            XCTAssertTrue(alertDescription.exists, "Added to Favorites should've been successful")
            alert.buttons["OK"].tap()
        }
    }
    
    func testDeleteFavoriteButton() {
        
        let indexPath = IndexPath(row: 2, section: 0)
        
        XCTAssertTrue(app.staticTexts["Cards"].exists)
        let collectionView = app.collectionViews.element.firstMatch
        if collectionView.cells.count > 0 {
            // Move to detail view
            collectionView.cells.element(boundBy: indexPath.row).tap()
            // Tap add to Favorites
            app.navigationBars.buttons["addToFavorites"].tap()
            
            // Validate delete successful
            let alert = app.alerts.firstMatch
            let alertExpectation = alert.waitForExistence(timeout: 10)
            XCTAssertTrue(alertExpectation, "Deleted from Favorites alert should've been present")
            let alertDescription = alert.staticTexts["Card deleted from Favorites"]
            XCTAssertTrue(alertDescription.exists, "Deleted from Favorites should've been successful")
            alert.buttons["OK"].tap()
        }
    }
    
}

//
//  TestInitFavoritesProtocol.swift
//  HearthstoneTests
//
//  Created by Stavros Tsikinas on 26/8/22.
//

import XCTest
@testable import Hearthstone

class TestInitFavoritesProtocol: XCTestCase {
    
    var testCoreData: TestCoreDataService!

    override func setUpWithError() throws {
        try super.setUpWithError()
        testCoreData = TestCoreDataService()
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
        testCoreData = nil
    }
    
    func testCardViewModelInitIsFavorite() {
        
        let mockUpdater = MockUpdateFavoritesProtocol(for: self, with: testCoreData)
        
        let cardVM = testCardViewModel()
        cardVM.delegate = mockUpdater
        
        let expectation = expectation(description: "testCardViewModelInitIsFavorite")
        
        testCoreData.save(cardVM.cardID) { saved in
            XCTAssertTrue(saved)
            cardVM.initFavorite {
                XCTAssertTrue(cardVM.isFavorite)
                expectation.fulfill()
            }
        }
        
        waitForExpectations(timeout: 3.0) { error in
            XCTAssertNil(error, "testCardViewModelInitIsFavorite unsuccessful")
        }
    }
    
    func testCardViewModelInitIsntFavorite() {
        let mockUpdater = MockUpdateFavoritesProtocol(for: self, with: testCoreData)
        
        let cardVM = testCardViewModel()
        cardVM.delegate = mockUpdater
        
        let expectation = expectation(description: "testCardViewModelInitIsntFavorite")
        cardVM.initFavorite {
            XCTAssertFalse(cardVM.isFavorite)
            expectation.fulfill()
        }
        
        waitForExpectations(timeout: 3.0) { error in
            XCTAssertNil(error, "testCardViewModelInitIsntFavorite unsuccessful")
        }
    }

}

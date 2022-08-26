//
//  TestUpdateFavoritesProtocol.swift
//  HearthstoneTests
//
//  Created by Stavros Tsikinas on 26/8/22.
//

import XCTest
@testable import Hearthstone

class TestUpdateFavoritesProtocol: XCTestCase {

    var testCoreData: TestCoreDataService!

    override func setUpWithError() throws {
        try super.setUpWithError()
        testCoreData = TestCoreDataService()
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
        testCoreData = nil
    }
    
    func testViewModelBecameFavorite() throws {
        let mockUpdater = MockUpdateFavoritesProtocol(for: self, with: testCoreData)
        
        let cardVM = testCardViewModel()
        cardVM.delegate = mockUpdater
        
        mockUpdater.expectDidBecomeFavorite()
        cardVM.updateFavorite()
        
        waitForExpectations(timeout: 3.0) { error in
            XCTAssertNil(error, "testViewModelBecameFavorite unsuccessful")
        }
        
        let didBecomeFavorite = try XCTUnwrap(mockUpdater.isFavorite, "Unable to unwrap isFavorite from MockUpdateFavoritesProtocol")
        XCTAssertTrue(didBecomeFavorite, "CardViewModel's isFavorite param should've been true.")
        
    }
    
    func testViewModelDeletedFromFavorites() throws {
        let mockUpdater = MockUpdateFavoritesProtocol(for: self, with: testCoreData)
        
        let cardVM = testCardViewModel(favorite: true)
        cardVM.delegate = mockUpdater
        
        mockUpdater.expectDidBecomeFavorite()
        cardVM.updateFavorite()
        
        waitForExpectations(timeout: 3.0) { error in
            XCTAssertNil(error, "testViewModelDeletedFromFavorites unsuccessful")
        }
        
        let isFavorite = try XCTUnwrap(mockUpdater.isFavorite, "Unable to unwrap isFavorite from MockUpdateFavoritesProtocol")
        XCTAssertFalse(isFavorite, "CardViewModel's isFavorite param should've been false.")
        
    }

}

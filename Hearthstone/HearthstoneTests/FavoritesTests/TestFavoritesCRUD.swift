//
//  TestFavoritesCRUD.swift
//  HearthstoneTests
//
//  Created by Stavros Tsikinas on 24/8/22.
//

import XCTest
@testable import Hearthstone

class TestFavoritesCRUD: XCTestCase {

    var sut: TestCoreDataService!
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        sut = TestCoreDataService()
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
        sut = nil
    }

    func testNewSaveToFavorites() {
        
        let cardID = "ANewCard_01"
        
        expectation(forNotification: .NSManagedObjectContextDidSave, object: sut.context)
        
        sut.context.perform { [weak self] in
            self?.sut.save(cardID) { saved in
                XCTAssertTrue(saved)
            }
        }
        
        waitForExpectations(timeout: 2.0) { error in
            XCTAssertNil(error, "Save unsuccessful")
        }
        
    }
    
    func testUnableToSaveDuplicate() {
        let cardID = "AnExistingCard_02"
        
        expectation(forNotification: .NSManagedObjectContextDidSave, object: sut.context)
        
        sut.context.perform { [weak self] in
            self?.sut.save(cardID) { saved in
                XCTAssertTrue(saved)
                self?.sut.save(cardID) { savedAgain in
                    XCTAssertFalse(savedAgain)
                }
            }
            
        }
        
        waitForExpectations(timeout: 5.0) { error in
            XCTAssertNil(error, "Save and Exists unsuccessful")
        }
    }
    
    func testFavoriteExists() {
        let cardID = "AnExistingCard_03"
        
        expectation(forNotification: .NSManagedObjectContextDidSave, object: sut.context)
        
        sut.context.perform { [weak self] in
            self?.sut.save(cardID) { saved in
                XCTAssertTrue(saved)
                self?.sut.exists(with: cardID) { exists in
                    XCTAssertTrue(exists)
                }
            }
            
        }
        
        waitForExpectations(timeout: 5.0) { error in
            XCTAssertNil(error, "Exists unsuccessful")
        }
    }
    
    func testFavoriteDelete() {
        let cardID = "CardToDelete_04"
        
        expectation(forNotification: .NSManagedObjectContextDidSave, object: sut.context)
        
        sut.context.perform { [weak self] in
            self?.sut.save(cardID) { saved in
                XCTAssertTrue(saved)
                self?.sut.delete(cardID: cardID) { success in
                    XCTAssertTrue(success)
                }
            }
            
        }
        
        waitForExpectations(timeout: 5.0) { error in
            XCTAssertNil(error, "Delete unsuccessful")
        }
    }
    
    func testFetchFavorite() {
        
        
        expectation(forNotification: .NSManagedObjectContextDidSave, object: sut.context)
        let numberOfFavsToAdd = 6
        
        sut.context.perform { [weak self] in
            self?.sut.addCards(of: numberOfFavsToAdd) { saved in
                XCTAssertTrue(saved)
                self?.sut.getFavorites { favorites in
                    XCTAssertFalse(favorites.isEmpty)
                    XCTAssertEqual(numberOfFavsToAdd, favorites.count)
                }
            }
        }
        
        waitForExpectations(timeout: 3.0) { error in
            XCTAssertNil(error, "Get Favorites unsuccessful")
        }
    }
    
}

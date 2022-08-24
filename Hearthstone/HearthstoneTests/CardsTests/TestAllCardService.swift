//
//  TestAllCardService.swift
//  HearthstoneTests
//
//  Created by Stavros Tsikinas on 8/8/22.
//

import XCTest
@testable import Hearthstone

class TestAllCardService: XCTestCase {
    
    var sut: CardsDataService!
    var elements: [Card]?

    override func setUpWithError() throws {
        try super.setUpWithError()
        sut = CardsDataService(type: .AllCards)
        elements = testCardArray()
    }

    override func tearDownWithError() throws {
        sut = nil
        elements = nil
        try super.tearDownWithError()
    }
    
    func testServiceIsCorrectType() {
        
        XCTAssertTrue(sut.type == CardsDataService.ServiceType.AllCards, "Service should be of type: \(CardsDataService.ServiceType.AllCards)")
        
    }
    
    func testServiceReturnsAllCards() {
        
        let expectation = expectation(description: "testServiceReturnsAllCards")
        
        sut.handleParsed(elements ?? []) { [weak self] cardVMs in
            XCTAssertNotEqual(cardVMs.count, 0, "Service should return value > 0")
            XCTAssertEqual(cardVMs.count, self?.elements?.count ?? 0, "Service should return as many VMs as the input")
            expectation.fulfill()
        }
        
        waitForExpectations(timeout: 2.0) { error in
            XCTAssertNil(error, "testServiceReturnsAllCards unsuccessful")
        }
        
    }
    
    func testServiceReturnsFeaturedOnly() {
        
        let expectation = expectation(description: "testServiceReturnsFeaturedOnly")
        
        sut.handleParsed(elements ?? []) { [weak self] cardVMs in
            
            if let featured = self?.sut.featuresFilter(is: true, for: cardVMs) {
                XCTAssertNotEqual(featured.count, 0, "Dummy data have provided 3 cards with Hsiao Favorite elements")
                XCTAssertEqual(featured.count, 3, "Dummy data have provided 3 cards with Hsiao Favorite elements")
            } else {
                XCTFail()
            }
            expectation.fulfill()
        }
        
        waitForExpectations(timeout: 2.0) { error in
            XCTAssertNil(error, "testServiceReturnsFeaturedOnly unsuccessful")
        }
        
        
        
    }

}

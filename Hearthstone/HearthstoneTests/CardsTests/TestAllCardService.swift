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
        
        let cardVMs = sut.handleParsed(elements ?? [], from: nil)
        
        XCTAssertNotEqual(cardVMs.count, 0, "Service should return value > 0")
        
        XCTAssertEqual(cardVMs.count, elements?.count ?? 0, "Service should return as many VMs as the input")
        
    }
    
    func testServiceReturnsFeaturedOnly() {
        
        let cardVMs = sut.handleParsed(elements ?? [], from: nil)
        let featured = sut.featuresFilter(is: true, for: cardVMs)
        
        XCTAssertNotEqual(featured.count, 0, "Dummy data have provided 3 cards with Hsiao Favorite elements")
        
        XCTAssertEqual(featured.count, 3, "Dummy data have provided 3 cards with Hsiao Favorite elements")
        
    }

}

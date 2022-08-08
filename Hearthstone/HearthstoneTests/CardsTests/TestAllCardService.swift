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

    override func setUpWithError() throws {
        try super.setUpWithError()
        sut = CardsDataService(type: .AllCards)
    }

    override func tearDownWithError() throws {
        sut = nil
        
        try super.tearDownWithError()
    }
    
    func testServiceIsCorrectType() {
        
        XCTAssertTrue(sut.type == CardsDataService.ServiceType.AllCards, "Service should be of type: \(CardsDataService.ServiceType.AllCards)")
        
    }
    
    func testServiceReturnsAllCards() {
        let testArray = testCardArray()
        
        let cardVMs = sut.handleParsed(testArray)
        
        XCTAssertNotEqual(cardVMs.count, 0, "Service should return value > 0")
        
        XCTAssertEqual(cardVMs.count, testArray.count, "Service should return as many VMs as the input")
        
    }

}

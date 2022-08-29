//
//  TestDetailCardViewModel.swift
//  HearthstoneTests
//
//  Created by Stavros Tsikinas on 23/8/22.
//

import XCTest
@testable import Hearthstone

class TestDetailCardViewModel: XCTestCase {

    override func setUpWithError() throws {
        try super.setUpWithError()
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
    }
    
    func testDetailCardViewModelType() {
        var card = Card()
        card.type = "Hero"
        
        let vm = CardViewModel(card: card)
        
        XCTAssertTrue(vm.type != nil, "Card type has been defined and is: 'Hero'")
        
    }

    func testDetailCardViewModelDescription() {
        let card = Card()
        let vm = CardViewModel(card: card)
        
        XCTAssert(vm.description == "No Information to show", "Description should've been: 'No Information to show'")
    }

}

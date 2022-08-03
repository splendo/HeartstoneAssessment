//
//  TestCardViewModel.swift
//  HearthstoneTests
//
//  Created by Epsilon User on 28/7/22.
//

import XCTest
@testable import Hearthstone

class TestCardViewModel: XCTestCase {

    override func setUpWithError() throws {
        super.setUp()
        
    }
    
    func testCardViewModelInitialization() {
        let cardVM = CardViewModel(card: testCard(name: "a Card", img: "https://www.cardlink.card/card.png"), select: {})
        
        XCTAssertNotNil(cardVM.title, "No title found on cardVM")
        XCTAssertNotNil(cardVM.image, "No image path found on cardVM")
    }
    
    func testCardViewModelTitlePlaceholder() {
        
        let cardVM = CardViewModel(card: testCard(), select: {})
        
        XCTAssertEqual(cardVM.title, CardViewModel.placeholderTitle, "Placeholder title should've been provided on an empty Card")
        
    }

}

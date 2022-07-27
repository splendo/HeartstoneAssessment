//
//  TestCardsFileExists.swift
//  HearthstoneTests
//
//  Created by Epsilon User on 27/7/22.
//

import XCTest

class TestCardsFileExists: XCTestCase {

    override func setUpWithError() throws {
        super.setUp()
    }
    
    func testJSONFileExists() {
        guard let _ = Bundle.main.url(forResource: "cards", withExtension: "json") else {
            XCTFail("File is Missing")
            return
        }
        
    }

}

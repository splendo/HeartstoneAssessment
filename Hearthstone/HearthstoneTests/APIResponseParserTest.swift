//
//  APIResponseParserTest.swift
//  HearthstoneTests
//
//  Created by Dmitrii on 29/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import XCTest

class APIResponseParserTest: XCTestCase {

    override func setUp() {
        super.setUp()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    func testProcessCardsResponseValidJson() {
        let path = Bundle.main.path(forResource: "testCards", ofType: "json")
        let jsonData = try? Data(contentsOf: URL(fileURLWithPath: path!))
        let completion: RequestCompletion = { (response, error) in
            XCTAssertNotNil(response)
            let cards = response as? [Card]
            XCTAssertNotNil(cards)
            XCTAssertEqual(cards!.count, 2)
        }
        APIResponseParser.processCardsResponse(responseData: jsonData!, error: nil, completion: completion)
    }

    func testProcessCardsResponseAPIError() {
        let testError = NSError(domain: "", code: 0, userInfo: nil)
        let completion: RequestCompletion = { (response, error) in
            XCTAssertNil(response)
            let errorObj = error as? NSError
            XCTAssertNotNil(errorObj)
            XCTAssertEqual(errorObj, testError)
        }
        APIResponseParser.processCardsResponse(responseData: nil, error: testError, completion: completion)
    }

    func testProcessCardsResponseSerialisationError() {
        let path = Bundle.main.path(forResource: "testCards_corrupted", ofType: "json")
        let jsonData = try? Data(contentsOf: URL(fileURLWithPath: path!))
        let completion: RequestCompletion = { (response, error) in
            XCTAssertNil(response)
            let errorObj = error as? NSError
            XCTAssertNotNil(errorObj)
            XCTAssertEqual(errorObj!.code, apiResponseProcessingErrorCodeParsing)
            XCTAssertEqual(errorObj!.domain, apiResponseProcessingErrorDomain)
        }
        APIResponseParser.processCardsResponse(responseData: jsonData, error: nil, completion: completion)
    }

    func testProcessCardsResponseFormatError() {
        let path = Bundle.main.path(forResource: "testCards_wrongFormat", ofType: "json")
        let jsonData = try? Data(contentsOf: URL(fileURLWithPath: path!))
        let completion: RequestCompletion = { (response, error) in
            XCTAssertNil(response)
            let errorObj = error as? NSError
            XCTAssertNotNil(errorObj)
            XCTAssertEqual(errorObj!.code, apiResponseProcessingErrorCodeFormat)
            XCTAssertEqual(errorObj!.domain, apiResponseProcessingErrorDomain)
        }
        APIResponseParser.processCardsResponse(responseData: jsonData, error: nil, completion: completion)
    }
}

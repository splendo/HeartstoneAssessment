//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import XCTest
@testable import HearthstoneHelper

class HttpClientTests: XCTestCase {
    var sut: Http.Client!

    override func setUp() {
        super.setUp()

        sut = Http.Client()
    }

    func test_getRequest_succeeds() {
        let requestExpectation = XCTestExpectation(description: "Performing network request")
        
        sut.get(url: URL(string: "http://www.example.com")!) { result in
            switch result {
            case .success:
                break
            case .failure(let error):
                XCTFail("Unexpected error: \(error)")
            }

            requestExpectation.fulfill()
        }
        
        wait(for: [requestExpectation], timeout: 6.0)
    }
}
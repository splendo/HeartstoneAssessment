//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import XCTest
@testable import HearthstoneHelper

class LocalCollectionRetrieverTests: XCTestCase {
    let retrievalExpectationTimeout = 1.0

    var fileData: Data!
    var sut: LocalCollectionRetriever!

    override func setUp() {
        super.setUp()

        sut = LocalCollectionRetriever(fileReader: { _ in self.fileData })
    }

    func test_retrieveCollection_returnsNilData_completesWithError() {
        fileData = nil

        let retrievalExpectation = expectation(description: "Wait for collection retrieval")

        sut.retrieveCollection { result in
            switch result {
            case .success(_):
                XCTFail("Data retrieval unexpectedly succeeded")
            case .failure(.fileNotFound), .failure(.invalidFormat(_)):
                XCTFail("Unexpected error during data retrieval: \(result)")
            case .failure(.fileNotReadable):
                break
            }

            retrievalExpectation.fulfill()
        }

        wait(for: [retrievalExpectation], timeout: retrievalExpectationTimeout)
    }

    func test_retrieveCollection_returnsInvalidData_completesWithError() {
        fileData = "This is not a valid JSON".data(using: .utf8)

        let retrievalExpectation = expectation(description: "Wait for collection retrieval")

        sut.retrieveCollection { result in
            switch result {
            case .success(_):
                XCTFail("Data retrieval unexpectedly succeeded")
            case .failure(.fileNotReadable), .failure(.fileNotFound):
                XCTFail("Unexpected error during data retrieval: \(result)")
            case .failure(.invalidFormat(_)):
                break
            }

            retrievalExpectation.fulfill()
        }

        wait(for: [retrievalExpectation], timeout: retrievalExpectationTimeout)
    }
}

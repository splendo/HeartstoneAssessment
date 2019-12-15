//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation
import XCTest
@testable import HearthstoneHelper

class ImageServiceTests: XCTestCase {
    let fetchTimeout = 1.0

    var imageCache: MockImageCache!
    var imageRetriever: MockImageRetriever!

    var sut: ImageService!

    override func setUp() {
        super.setUp()

        imageCache = MockImageCache()
        imageRetriever = MockImageRetriever()

        sut = ImageService(imageCache: imageCache, imageRetriever: imageRetriever)
    }

    func test_fetchImage_imageIsCached_returnsCachedValue() {
        let imageUrl = URL(string: "http://not-a-website.com")!
        let fetchExpectation = XCTestExpectation(description: "Fetching image")
        let cachedData = Data()

        imageCache.retrievalResult = .success(cachedData)

        sut.fetch(from: imageUrl) { result in
            switch result {
            case .success(let data):
                XCTAssertEqual(data, cachedData)
            case .failure(let error):
                XCTFail("Unexpected error: \(error)")
            }

            fetchExpectation.fulfill()
        }

        wait(for: [fetchExpectation], timeout: fetchTimeout)

        XCTAssertEqual(imageCache.retrieveCallCount, 1)
        XCTAssertEqual(imageCache.retrieveUrlArgument, imageUrl)
        XCTAssertEqual(imageCache.storeCallCount, 0)
        XCTAssertEqual(imageRetriever.retrieveCallCount, 0)
    }

    func test_fetchImage_imageIsNotCached_retrievesImage_storesImageInCache() {
        let imageUrl = URL(string: "http://not-a-website.com")!
        let fetchExpectation = XCTestExpectation(description: "Fetching image")
        let retrievedData = Data()

        imageCache.retrievalResult = .failure(.nothingStored)
        imageRetriever.retrievalResult = .success(retrievedData)

        sut.fetch(from: imageUrl) { result in
            switch result {
            case .success(let data):
                XCTAssertEqual(data, retrievedData)
            case .failure(let error):
                XCTFail("Unexpected error: \(error)")
            }

            fetchExpectation.fulfill()
        }

        wait(for: [fetchExpectation], timeout: fetchTimeout)

        XCTAssertEqual(imageCache.retrieveCallCount, 1)
        XCTAssertEqual(imageCache.retrieveUrlArgument, imageUrl)
        XCTAssertEqual(imageCache.storeCallCount, 1)
        XCTAssertEqual(imageCache.storeUrlArgument, imageUrl)
        XCTAssertEqual(imageRetriever.retrieveCallCount, 1)
        XCTAssertEqual(imageRetriever.retrieveUrlArgument, imageUrl)
    }
}
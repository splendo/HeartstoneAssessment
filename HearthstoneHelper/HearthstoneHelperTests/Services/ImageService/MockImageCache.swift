//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation
@testable import HearthstoneHelper

class MockImageCache: ImageCaching {
    private(set) var storeCallCount = 0
    private(set) var storeUrlArgument: URL?
    private(set) var retrieveCallCount = 0
    private(set) var retrieveUrlArgument: URL?

    var retrievalResult: Result<Data, CacheError>?

    func store(imageData: Data, for url: URL) {
        storeCallCount += 1
        storeUrlArgument = url
    }

    func retrieve(by url: URL) -> Result<Data, CacheError> {
        guard let retrievalResult = retrievalResult else {
            fatalError("retrievalResult expectation was not set")
        }

        retrieveCallCount += 1
        retrieveUrlArgument = url

        return retrievalResult
    }
}
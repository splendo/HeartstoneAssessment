//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation
@testable import HearthstoneHelper

class MockImageRetriever: ImageRetriever {
    private(set) var retrieveCallCount = 0
    private(set) var retrieveUrlArgument: URL?
    var retrievalResult: Result<Data, Error>?

    func retrieve(from url: URL, completion: @escaping Completion) -> HearthstoneHelper.Cancelable {
        guard let retrievalResult = retrievalResult else {
            fatalError("retrievalResult expectation was not set")
        }

        retrieveCallCount += 1
        retrieveUrlArgument = url
        
        completion(retrievalResult)

        return MockCancelable()
    }
}
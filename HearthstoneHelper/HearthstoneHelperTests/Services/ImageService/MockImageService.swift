//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation
@testable import HearthstoneHelper

class MockImageService: ImageProviding {
    private(set) var fetchCallCount = 0
    private(set) var fetchCallUrlArgument: URL?

    var fetchResult: Result<Data, Error>?
    var fetchReturnValue: Cancelable?

    func fetch(from url: URL, completion: @escaping ImageRetriever.Completion) -> Cancelable? {
        guard let fetchResult = fetchResult else {
            fatalError("fetchResult expectation was not set")
        }
        
        fetchCallCount += 1
        fetchCallUrlArgument = url

        completion(fetchResult)

        return fetchReturnValue ?? MockCancelable()
    }
}
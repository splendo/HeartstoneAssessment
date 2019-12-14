//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

@testable import HearthstoneHelper

class MockCollectionRetriever: CollectionRetriever {
    var retrievalResult: Result<[Card], Error> = .success([])

    func retrieveCollection(completion: @escaping Completion) {
        completion(retrievalResult)
    }
}
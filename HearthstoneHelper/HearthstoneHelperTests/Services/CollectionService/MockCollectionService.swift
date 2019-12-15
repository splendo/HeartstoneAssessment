//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

@testable import HearthstoneHelper

class MockCollectionService: CollectionProviding {
    private(set) var getCollectionCallCount = 0
    var getCollectionResult: Result<[Card], Error>!

    func getCollection(completion: @escaping CollectionRetriever.Completion) {
        getCollectionCallCount += 1
        completion(getCollectionResult)
    }
    
    func getCollection(sortBy order: SortOrder, 
                       filterWith filter: @escaping CardFilter, 
                       completion: @escaping CollectionRetriever.Completion) {
        getCollection(completion: completion)
    }
}
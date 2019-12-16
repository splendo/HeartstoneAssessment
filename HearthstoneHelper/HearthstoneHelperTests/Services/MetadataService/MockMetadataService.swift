//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

@testable import HearthstoneHelper

class MockMetadataService: MetadataProviding {
    private(set) var fetchMetadataCallCount = 0
    private(set) var fetchMetadataCallArgument: Card.ID?
    private(set) var updateMetadataCallCount = 0
    private(set) var updateMetadataCallArgumentMetadata: CardMetadata?
    private(set) var updateMetadataCallArgumentId: Card.ID?
    var fetchMetadataResult: CardMetadata?


    func fetchMetadata(forCardWithId id: Card.ID) -> CardMetadata {
        fetchMetadataCallCount += 1
        fetchMetadataCallArgument = id

        return fetchMetadataResult ?? CardMetadata(favorite: .notFavorite)
    }

    func update(metadata: CardMetadata, forCardWithId id: Card.ID) {
        updateMetadataCallCount += 1
        updateMetadataCallArgumentMetadata = metadata
        updateMetadataCallArgumentId = id
    }
}
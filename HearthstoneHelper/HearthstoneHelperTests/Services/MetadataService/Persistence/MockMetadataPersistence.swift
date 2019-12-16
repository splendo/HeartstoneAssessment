//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license]
//
// Copyright (c) 2019 rencevio. All rights reserved.

@testable import HearthstoneHelper

class MockMetadataPersistence: MetadataPersisting {
    private(set) var persistMetadataCallCount = 0
    private(set) var persistMetadataCallArgumentMetadata: CardMetadata?
    private(set) var persistMetadataCallArgumentId: Card.ID?
    private(set) var retrieveMetadataCallCount = 0
    private(set) var retrieveMetadataCallArgument: Card.ID?
    var retrieveMetadataResult: CardMetadata?

    func persist(metadata: CardMetadata, forCardWithId id: Card.ID) {
        persistMetadataCallCount += 1
        persistMetadataCallArgumentMetadata = metadata
        persistMetadataCallArgumentId = id
    }

    func retrieveMetadata(forCardWithId id: Card.ID) -> CardMetadata? {
        retrieveMetadataCallCount += 1
        retrieveMetadataCallArgument = id
        
        return retrieveMetadataResult
    }
}
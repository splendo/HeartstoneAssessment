//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

protocol MetadataProviding {
    func fetchMetadata(forCardWithId id: Card.ID) -> CardMetadata
    func update(metadata: CardMetadata, forCardWithId id: Card.ID)
}

// Stores card metadata in memory; the metadata is lazily loaded
class MetadataService: MetadataProviding {
    let persistence: MetadataPersisting
    let defaultMetadata = CardMetadata(favorite: .notFavorite)
    
    private var metadata: [Card.ID: CardMetadata] = [:]

    init(persistence: MetadataPersisting) {
        self.persistence = persistence
    }

    func fetchMetadata(forCardWithId id: Card.ID) -> CardMetadata {
        guard let storedMetadata = metadata[id] else {
            if let persistedMetadata = persistence.retrieveMetadata(forCardWithId: id) {
                metadata[id] = persistedMetadata
                return persistedMetadata
            } else {
                return defaultMetadata
            }
        }

        return storedMetadata
    }

    func update(metadata: CardMetadata, forCardWithId id: Card.ID) {
        persistence.persist(metadata: metadata, forCardWithId: id)
        self.metadata[id] = metadata
    }
}

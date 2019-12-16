//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import XCTest
@testable import HearthstoneHelper

class MetadataServiceTests: XCTestCase {
    var persistence: MockMetadataPersistence!

    var sut: MetadataService!

    override func setUp() {
        super.setUp()

        persistence = MockMetadataPersistence()
        sut = MetadataService(persistence: persistence)
    }

    func test_updateMetadata_persists_fetchingDoesNotReadFromPersistence() {
        let cardId = "ID"
        let metadata = CardMetadata(favorite: .favorite)

        sut.update(metadata: metadata, forCardWithId: cardId)

        XCTAssertEqual(persistence.persistMetadataCallCount, 1)
        XCTAssertEqual(persistence.persistMetadataCallArgumentId, cardId)

        let _ = sut.fetchMetadata(forCardWithId: cardId)

        XCTAssertEqual(persistence.retrieveMetadataCallCount, 0)
    }

    func test_nothingIsStoredAndPersisted_fetchMetadata_triesReadingFromPersistence_returnsDefaultValue() {
        let cardId = "ID"
        
        let metadata = sut.fetchMetadata(forCardWithId: cardId)
        
        XCTAssertEqual(persistence.retrieveMetadataCallCount, 1)
        XCTAssertEqual(metadata.favorite, .notFavorite)
    }
}

//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import XCTest
@testable import HearthstoneHelper

class DetailsInteractorTests: XCTestCase {
    var presenter: MockDetailsPresenter!
    var imageService: MockImageService!
    var metadataService: MockMetadataService!

    let card = Card(
            cardId: "ID",
            name: "name",
            type: "type",
            rarity: "rarity",
            mechanics: nil,
            img: URL(string: "www.image.com")!,
            playerClass: "class",
            flavor: "flavor")

    var sut: DetailsInteractor!

    override func setUp() {
        presenter = MockDetailsPresenter()
        imageService = MockImageService()
        metadataService = MockMetadataService()

        sut = DetailsInteractor(
                withInfoFrom: card,
                presenter: presenter,
                imageService: imageService,
                metadataService: metadataService)

        super.setUp()
    }

    func test_loadDetails_presentsName() {

        sut.loadDetails()

        XCTAssertEqual(presenter.presentCardNameCallCount, 1)
        XCTAssertEqual(presenter.presentCardNameCallArgument, card.name)
    }

    func test_loadDetails_fetchesAndPresentsImage() {
        let imageData = Data()
        imageService.fetchResult = .success(imageData)

        sut.loadDetails()

        XCTAssertEqual(imageService.fetchCallCount, 1)
        XCTAssertEqual(imageService.fetchCallUrlArgument, card.img)
        XCTAssertEqual(presenter.presentImageCallCount, 1)
        XCTAssertEqual(presenter.presentImageCallArgument, imageData)
    }

    func test_loadDetails_fetchesAndPresentsMetadata() {
        let metadata = CardMetadata(favorite: .favorite)
        
        imageService.fetchResult = .success(Data())
        metadataService.fetchMetadataResult = metadata

        sut.loadDetails()

        XCTAssertEqual(metadataService.fetchMetadataCallCount, 1)
        XCTAssertEqual(metadataService.fetchMetadataCallArgument, card.cardId)
        XCTAssertEqual(presenter.presentFavoriteStatusCallCount, 1)
        XCTAssertEqual(presenter.presentFavoriteStatusCallArgument, metadata.favorite)
    }

    func test_toggleFavoriteStatus_fetchesMetadata_presentsAndStoresUpdatedValue() {
        let metadata = CardMetadata(favorite: .notFavorite)

        metadataService.fetchMetadataResult = metadata

        sut.toggleFavoriteStatus()

        XCTAssertEqual(metadataService.fetchMetadataCallCount, 1)
        XCTAssertEqual(metadataService.fetchMetadataCallArgument, card.cardId)

        XCTAssertEqual(presenter.presentFavoriteStatusCallCount, 1)
        XCTAssertEqual(presenter.presentFavoriteStatusCallArgument, metadata.favorite.toggled())

        XCTAssertEqual(metadataService.updateMetadataCallCount, 1)
        XCTAssertEqual(metadataService.updateMetadataCallArgumentId, card.cardId)
        XCTAssertEqual(metadataService.updateMetadataCallArgumentMetadata?.favorite, metadata.favorite.toggled())
    }
}
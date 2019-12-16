//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation
import XCTest
@testable import HearthstoneHelper

class CardCellInteractorTests: XCTestCase {
    var presenter: MockCardCellPresenter!
    var imageService: MockImageService!
    var metadataService: MockMetadataService!

    var sut: CardCellInteractor!

    override func setUp() {
        super.setUp()

        presenter = MockCardCellPresenter()
        imageService = MockImageService()
        metadataService = MockMetadataService()

        sut = CardCellInteractor(presenter: presenter, imageService: imageService, metadataService: metadataService)
    }

    func test_updateCard_presentsName() {
        let card = Card(
                cardId: "id",
                name: "name",
                type: "type",
                rarity: nil,
                mechanics: nil,
                img: nil,
                playerClass: nil,
                flavor: nil)

        sut.updateCard(from: card)

        XCTAssertEqual(presenter.presentNameCallCount, 1)
        XCTAssertEqual(presenter.presentNameCallArgument, card.name)
    }

    func test_updateCard_presentsLoading() {
        sut.updateCard(from: Card(
                cardId: "id",
                name: "name",
                type: "type",
                rarity: nil,
                mechanics: nil,
                img: nil,
                playerClass: nil,
                flavor: nil))

        XCTAssertEqual(presenter.presentLoadingCallCount, 1)
    }

    func test_updateCard_withNoImageURL_presentsNoImage() {
        sut.updateCard(from: Card(
                cardId: "id",
                name: "name",
                type: "type",
                rarity: nil,
                mechanics: nil,
                img: nil,
                playerClass: nil,
                flavor: nil))

        XCTAssertEqual(presenter.presentNoImageCallCount, 1)
    }

    func test_updateCard_imageFetchSuccessful_presentsImage() {
        let imageUrl = URL(string: "www.legit-image.com")
        let card = Card(
                cardId: "id",
                name: "name",
                type: "type",
                rarity: nil,
                mechanics: nil,
                img: imageUrl,
                playerClass: nil,
                flavor: nil)

        let fetchedData = Data()
        imageService.fetchResult = .success(fetchedData)

        sut.updateCard(from: card)

        XCTAssertEqual(imageService.fetchCallCount, 1)
        XCTAssertEqual(imageService.fetchCallUrlArgument, imageUrl)
        XCTAssertEqual(presenter.presentImageCallCount, 1)
        XCTAssertEqual(presenter.presentImageCallArgument, fetchedData)
    }

    func test_updateCard_imageFetchFails_presentsError() {
        enum FetchError: Error {
            case error
        }

        let imageUrl = URL(string: "www.not-an-image.com")
        let card = Card(
                cardId: "id",
                name: "name",
                type: "type",
                rarity: nil,
                mechanics: nil,
                img: imageUrl,
                playerClass: nil,
                flavor: nil)

        imageService.fetchResult = .failure(FetchError.error)

        sut.updateCard(from: card)

        XCTAssertEqual(imageService.fetchCallCount, 1)
        XCTAssertEqual(imageService.fetchCallUrlArgument, imageUrl)
        XCTAssertEqual(presenter.presentImageCallCount, 0)
        XCTAssertEqual(presenter.presentErrorCallCount, 1)
    }

    func test_updateCard_twice_cancelsFirstRequest() {
        let imageTask = MockCancelable()

        let card = Card(
                cardId: "id",
                name: "name",
                type: "type",
                rarity: nil,
                mechanics: nil,
                img: URL(string: "www.image.com"),
                playerClass: nil,
                flavor: nil)

        imageService.fetchResult = .success(Data())
        imageService.fetchReturnValue = imageTask

        sut.updateCard(from: card)
        sut.updateCard(from: card)

        XCTAssertEqual(imageService.fetchCallCount, 2)
        XCTAssertEqual(imageTask.cancelCallCount, 1)
    }

    func test_updateCard_metadataIsStored_presentsMetadata() {
        let card = Card(
                cardId: "id",
                name: "name",
                type: "type",
                rarity: nil,
                mechanics: nil,
                img: URL(string: "www.image.com"),
                playerClass: nil,
                flavor: nil)
        
        let metadata = CardMetadata(favorite: .favorite)

        imageService.fetchResult = .success(Data())
        metadataService.fetchMetadataResult = metadata

        sut.updateCard(from: card)

        XCTAssertEqual(presenter.presentFavoriteStatusCallCount, 1)
        XCTAssertEqual(presenter.presentFavoriteStatusCallArgument, metadata.favorite)
    }
}
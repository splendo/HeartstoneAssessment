//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

protocol DetailsInteracting {
    func loadDetails()
    func toggleFavoriteStatus()
}

protocol DetailsDataStore {
    var card: Card { get }
}

class DetailsInteractor: DetailsInteracting, DetailsDataStore {
    let card: Card
    let presenter: DetailsPresenting
    let imageService: ImageProviding
    let metadataService: MetadataProviding

    var cardMetadata: CardMetadata

    init(withInfoFrom card: Card,
         presenter: DetailsPresenting,
         imageService: ImageProviding,
         metadataService: MetadataProviding) {
        self.card = card
        self.presenter = presenter
        self.imageService = imageService
        self.metadataService = metadataService

        self.cardMetadata = CardMetadata(favorite: .notFavorite)
    }

    func loadDetails() {
        presenter.present(cardName: card.name)

        if let flavor = card.flavor {
            presenter.present(cardFlavor: flavor)
        }

        if let imageUrl = card.img {
            imageService.fetch(from: imageUrl) { [weak presenter] result in
                if case let .success(data) = result {
                    presenter?.presentImage(from: data)
                }
            }
        }

        loadMetadata()
    }

    func toggleFavoriteStatus() {
        let newToggledState = cardMetadata.favorite.toggled()

        cardMetadata = CardMetadata(favorite: newToggledState)

        presenter.present(favoriteStatus: newToggledState)
        metadataService.update(metadata: cardMetadata, forCardWithId: card.cardId)
    }

    private func loadMetadata() {
        cardMetadata = metadataService.fetchMetadata(forCardWithId: card.cardId) ?? cardMetadata
        
        presenter.present(favoriteStatus: cardMetadata.favorite)
    }
}
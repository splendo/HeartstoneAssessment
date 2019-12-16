//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

protocol DetailsInteracting {
    func loadDetails()
    func set(favoriteStatus: Bool)
}

protocol DetailsDataStore {
    var card: Card { get }
}

class DetailsInteractor: DetailsInteracting, DetailsDataStore {
    let card: Card
    let presenter: DetailsPresenting
    let imageService: ImageProviding

    init(withInfoFrom card: Card, presenter: DetailsPresenting, imageService: ImageProviding) {
        self.card = card
        self.presenter = presenter
        self.imageService = imageService
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

        // fav
    }

    func set(favoriteStatus: Bool) {
        
    }
}
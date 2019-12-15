//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

protocol CollectionInteracting {
    func fetchCollection()
}

final class CollectionInteractor: CollectionInteracting {
    private let collectionService: CollectionProviding
    private let presenter: CollectionPresenting

    init(presenter: CollectionPresenting, collectionService: CollectionProviding) {
        self.presenter = presenter
        self.collectionService = collectionService
    }

    func fetchCollection() {
        collectionService.getCollection(
                sortBy: .ascending,
                filterWith: cardFilter) { [weak presenter] result in
            guard let presenter = presenter else { return }

            switch result {
            case .success(let cards):
                presenter.present(collection: Collection(cards: cards))
            case .failure(let error):
                fatalError("\(error)")
            }
        }
    }

    private var cardFilter: (Card) -> Bool =
            { card in
                card.mechanics?.first { $0.name == "Deathrattle" } != nil
                        && card.rarity == "Legendary"
            }
}
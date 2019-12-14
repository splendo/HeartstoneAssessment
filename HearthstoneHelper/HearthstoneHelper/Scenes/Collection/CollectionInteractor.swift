//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

protocol CollectionInteracting {
    func fetchCollection()
}

final class CollectionInteractor: CollectionInteracting {
    private let presenter: CollectionPresenting
    private let collectionProvider: CollectionProviding

    init(presenter: CollectionPresenting, collectionProvider: CollectionProviding) {
        self.presenter = presenter
        self.collectionProvider = collectionProvider
    }

    func fetchCollection() {
        collectionProvider.getCollection { [weak presenter] result in
            guard let presenter = presenter else { return }

            switch result {
            case .success(let cards):
                presenter.present(collection: Collection(cards: cards))
            case .failure(let error):
                fatalError("\(error)")
            }
        }
    }
}
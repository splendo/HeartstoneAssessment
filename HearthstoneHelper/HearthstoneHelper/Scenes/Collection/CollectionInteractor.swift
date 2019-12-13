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

    init(presenter: CollectionPresenting) {
        self.presenter = presenter
    }

    func fetchCollection() {
        presenter.present(collection: Collection(cards: [
            Card(cardId: "1", name: "Foo", rarity: "rare", mechanics: nil, img: nil),
            Card(cardId: "2", name: "Bar", rarity: "rare", mechanics: nil, img: nil),
            Card(cardId: "3", name: "Baz", rarity: "rare", mechanics: nil, img: nil)
        ]))
    }
}
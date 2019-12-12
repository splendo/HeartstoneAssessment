//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

protocol CollectionInteracting {
    func fetchCollection()
}

class CollectionInteractor: CollectionInteracting {
    private let presenter: CollectionPresenting

    init(presenter: CollectionPresenting) {
        self.presenter = presenter
    }

    func fetchCollection() {
        presenter.present(collection: CollectionInfo(cards: ["1", "2", "3"]))
    }
}
//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data

protocol CollectionCreating {
    func createCollectionViewController() -> CollectionDisplaying
}

class CollectionFactory: CollectionCreating {
    let cardCellFactory: CardCellCreating

    init(cardCellFactory: CardCellCreating) {
        self.cardCellFactory = cardCellFactory
    }

    func createCollectionViewController() -> CollectionDisplaying {
        let dataSource = CollectionViewDataSource(cellConfigurator: cardCellFactory.createCellConfigurator())

        let presenter = CollectionPresenter()
        let interactor = CollectionInteractor(
                presenter: presenter,
                collectionProvider: CollectionService(
                        retriever: LocalCollectionRetriever(
                                fileReader: { url in try? Data(contentsOf: url) })
                )
        )

        let viewController = CollectionViewController(dataSource: dataSource, interactor: interactor)
        presenter.view = viewController

        return viewController
    }
}
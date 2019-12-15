//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data

protocol CollectionCreating {
    func createCollectionViewController(imageService: ImageProviding) -> CollectionDisplaying
}

class CollectionFactory: CollectionCreating {
    let cardCellFactory: CardCellCreating
    let collectionServiceFactory: CollectionServiceCreating

    init(cardCellFactory: CardCellCreating, collectionServiceFactory: CollectionServiceCreating) {
        self.cardCellFactory = cardCellFactory
        self.collectionServiceFactory = collectionServiceFactory
    }

    func createCollectionViewController(imageService: ImageProviding) -> CollectionDisplaying {
        let cellConfigurator = cardCellFactory.createCellConfigurator(imageService: imageService)
        let dataSource = CollectionViewDataSource(cellConfigurator: cellConfigurator)

        let collectionService = collectionServiceFactory.create()

        let presenter = CollectionPresenter()
        let interactor = CollectionInteractor(
                presenter: presenter,
                collectionService: collectionService
        )

        let viewController = CollectionViewController(dataSource: dataSource, interactor: interactor)
        presenter.view = viewController

        return viewController
    }
}
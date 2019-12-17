//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data

protocol CollectionCreating {
    func createCollectionViewController(imageService: ImageProviding, 
                                        metadataService: MetadataProviding) -> CollectionDisplaying
}

class CollectionFactory: CollectionCreating {
    let cardCellFactory: CardCellCreating
    let collectionServiceFactory: CollectionServiceCreating
    let detailsFactory: DetailsCreating

    init(cardCellFactory: CardCellCreating,
         collectionServiceFactory: CollectionServiceCreating,
         detailsFactory: DetailsCreating) {
        self.cardCellFactory = cardCellFactory
        self.collectionServiceFactory = collectionServiceFactory
        self.detailsFactory = detailsFactory
    }

    func createCollectionViewController(imageService: ImageProviding,
                                        metadataService: MetadataProviding) -> CollectionDisplaying {
        let dataSource = createCollectionViewDataSource(
                imageService: imageService,
                metadataService: metadataService)

        let collectionService = collectionServiceFactory.create()

        let presenter = CollectionPresenter()
        let interactor = CollectionInteractor(
                presenter: presenter,
                collectionService: collectionService
        )
        let router = CollectionRouter(detailsFactory: detailsFactory)

        let viewController = CollectionViewController(
                dataSource: dataSource, 
                interactor: interactor, 
                router: router)
        presenter.view = viewController
        router.sourceVC = viewController

        return viewController
    }

    private func createCollectionViewDataSource(imageService: ImageProviding,
                                                metadataService: MetadataProviding) -> CollectionViewDataSourcing {
        let cellConfigurator =
                cardCellFactory.createCellConfigurator(imageService: imageService, metadataService: metadataService)

        return CollectionViewDataSource(cellConfigurator: cellConfigurator)
    }
}
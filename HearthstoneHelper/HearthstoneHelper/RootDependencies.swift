//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data

final class RootDependencies {
    let imageService: ImageService

    init() {
        let imageServiceFactory = ImageServiceFactory()
        imageService = imageServiceFactory.create()
    }

    func createCollectionViewController() -> CollectionDisplaying {
        let collectionServiceFactory = CollectionServiceFactory()
        let cardCellFactory = CardCellFactory()
        let detailsFactory = DetailsFactory(imageService: imageService)

        let collectionFactory = CollectionFactory(
                cardCellFactory: cardCellFactory,
                collectionServiceFactory: collectionServiceFactory,
                detailsFactory: detailsFactory
        )

        return collectionFactory.createCollectionViewController(imageService: imageService)
    }
}
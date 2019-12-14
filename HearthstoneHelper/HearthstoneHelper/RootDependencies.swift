//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data

final class RootDependencies {
    func createCollectionViewController() -> CollectionViewController {
        let dataSource = CollectionViewDataSource()

        let presenter = CollectionPresenter()
        
        // todo: factorize
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
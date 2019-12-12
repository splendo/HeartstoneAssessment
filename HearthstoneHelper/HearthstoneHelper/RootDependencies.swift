//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

class RootDependencies {
    func createCollectionViewController() -> CollectionViewController {
        let dataSource = CollectionViewDataSource()

        let presenter = CollectionPresenter()
        let interactor = CollectionInteractor(presenter: presenter)

        let viewController = CollectionViewController(dataSource: dataSource, interactor: interactor)
        presenter.view = viewController

        return viewController
    }
}
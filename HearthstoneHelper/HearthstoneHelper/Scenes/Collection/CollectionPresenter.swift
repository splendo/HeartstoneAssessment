//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import class Foundation.DispatchQueue

protocol CollectionPresenting: class {
    func present(collection: Collection)
}

final class CollectionPresenter: CollectionPresenting {
    weak var view: CollectionDisplaying?

    func present(collection: Collection) {
        DispatchQueue.main.async { [weak self] in
            self?.view?.display(cards: collection)
        }
    }
}
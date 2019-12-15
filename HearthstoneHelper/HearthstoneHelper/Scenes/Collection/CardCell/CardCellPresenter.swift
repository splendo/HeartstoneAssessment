//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data
import class UIKit.UIImage

protocol CardCellPresenting {
    func present(name: String)
    func presentNoImage()
    func presentImage(from data: Data)
    func presentLoading()
    func presentError()
}

class CardCellPresenter: CardCellPresenting {
    weak var view: CardCellDisplaying?

    init(view: CardCellDisplaying) {
        self.view = view
    }

    func present(name: String) {
        view?.display(name: name)
    }

    func presentImage(from data: Data) {
        guard let image = UIImage(data: data) else {
            presentError()
            return
        }

        view?.display(state: .image(image))
    }

    func presentLoading() {
        view?.display(state: .loading)
    }

    func presentError() {
        view?.display(state: .noImage)
    }

    func presentNoImage() {
        view?.display(state: .noImage)
    }
}
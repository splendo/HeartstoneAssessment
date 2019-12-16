//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

protocol DetailsPresenting: class {
    func presentImage(from data: Data)
    func present(favoriteStatus: Bool)
    func present(cardName: String)
    func present(cardFlavor: String)
}

class DetailsPresenter: DetailsPresenting {
    weak var view: DetailsDisplaying?

    func presentImage(from data: Data) {
        guard let image = UIImage(data: data) else { return }
        
        view?.display(image: image)
    }

    func present(favoriteStatus: Bool) {
        view?.display(favoriteStatus: favoriteStatus)
    }

    func present(cardName: String) {
        view?.display(cardName: cardName)
    }

    func present(cardFlavor: String) {
        view?.display(cardFlavor: cardFlavor)
    }
}
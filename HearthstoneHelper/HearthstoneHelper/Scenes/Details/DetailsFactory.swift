//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

protocol DetailsCreating {
    func createDetailsViewController(withInfoFrom card: Card) -> DetailsDisplaying
}

class DetailsFactory: DetailsCreating {
    let imageService: ImageProviding
    let metadataService: MetadataProviding

    init(imageService: ImageProviding, metadataService: MetadataProviding) {
        self.imageService = imageService
        self.metadataService = metadataService
    }

    func createDetailsViewController(withInfoFrom card: Card) -> DetailsDisplaying {
        let presenter = DetailsPresenter()
        let interactor = DetailsInteractor(withInfoFrom: card, 
                presenter: presenter, 
                imageService: imageService, 
                metadataService: metadataService)

        let view = DetailsViewController(interactor: interactor)
        presenter.view = view

        return view
    }
}
//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

protocol CardCellConfiguring {
    func configure(_ cell: CardCellView)
}

class CardCellConfigurator: CardCellConfiguring {
    let imageService: ImageProviding

    init(imageService: ImageProviding) {
        self.imageService = imageService
    }

    func configure(_ cell: CardCellView) {
        if (cell.interactor == nil) {
            let presenter = CardCellPresenter(view: cell)
            let interactor = CardCellInteractor(presenter: presenter, imageService: imageService)

            cell.interactor = interactor
        }
    }
}
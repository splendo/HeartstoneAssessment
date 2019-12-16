//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

protocol CardCellInteracting {
    func updateCard(from info: Card)
}

class CardCellInteractor: CardCellInteracting {
    private let presenter: CardCellPresenting
    private let imageService: ImageProviding
    private let metadataService: MetadataProviding

    private var imageTask: Cancelable?

    init(presenter: CardCellPresenting, imageService: ImageProviding, metadataService: MetadataProviding) {
        self.presenter = presenter
        self.imageService = imageService
        self.metadataService = metadataService
    }

    func updateCard(from info: Card) {
        presenter.present(name: info.name)

        let metadata = metadataService.fetchMetadata(forCardWithId: info.cardId)
        presenter.present(favoriteStatus: metadata?.favorite ?? .notFavorite)

        loadImage(for: info.img)
    }

    private func loadImage(for url: URL?) {
        if let imageTask = imageTask {
            imageTask.cancel()
        }
        imageTask = nil

        presenter.presentLoading()

        guard let url = url else {
            presenter.presentNoImage()
            return
        }

        imageTask = imageService.fetch(from: url) { [weak self] result in
            guard let self = self else { return }

            switch result {
            case .success(let data):
                self.presenter.presentImage(from: data)
            case .failure:
                self.presenter.presentError()
            }
        }
    }
}

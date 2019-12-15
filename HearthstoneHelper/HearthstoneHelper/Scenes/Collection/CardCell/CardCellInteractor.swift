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
    private var imageService: ImageProviding
    
    private var imageTask: Cancelable?

    init(presenter: CardCellPresenting) {
        self.presenter = presenter
        // todo: injection
        self.imageService =
                ImageService(imageCache: ImageCache(), imageRetriever: NetworkImageRetriever(httpClient: Http.Client()))
    }

    func updateCard(from info: Card) {
        presenter.present(name: info.name)

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

        imageTask = imageService.fetch(from: url) { result in
            DispatchQueue.main.async { [weak self] in
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
}

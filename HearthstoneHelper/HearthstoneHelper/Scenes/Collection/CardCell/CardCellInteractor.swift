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

    init(presenter: CardCellPresenting) {
        self.presenter = presenter
    }

    func updateCard(from info: Card) {
        presenter.present(name: info.name)
        loadImage(for: info.img)
    }

    private func loadImage(for url: URL?) {
        presenter.presentLoading()

        guard let url = url else {
            self.presenter.presentPlaceholderImage()
            return
        }

        DispatchQueue(label: "temp queue").async { [weak self] in
            guard let self = self else { return }

            // todo: proper downloading
            tempDownloadImage(from: url) { data in
                DispatchQueue.main.async {
                    guard let data = data else {
                        self.presenter.presentErrorImage()
                        return
                    }

                    self.presenter.presentImage(from: data)
                }
            }
        }
    }
}

func tempDownloadImage(from url: URL, completion: @escaping (Data?) -> Void) {
    URLSession.shared.dataTask(with: url) { data, _, error in
        completion(data)
    }.resume()
}
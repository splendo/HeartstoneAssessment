//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

protocol CollectionRouting: Routing {
    func routeToDetailsView(withInfoFrom card: Card)
}

class CollectionRouter: CollectionRouting {
    let detailsFactory: DetailsCreating

    weak var sourceVC: UIViewController?

    init(detailsFactory: DetailsCreating) {
        self.detailsFactory = detailsFactory
    }

    func routeToDetailsView(withInfoFrom card: Card) {
        guard let navigationController = sourceVC?.navigationController else { return }

        let detailsViewController = detailsFactory.createDetailsViewController(withInfoFrom: card)

        navigationController.pushViewController(detailsViewController.asViewController, animated: true)
    }
}
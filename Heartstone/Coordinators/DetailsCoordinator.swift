//
//  DetailsCoordinator.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

protocol DetailsDelegate: class {
    func onDetailsFlowFinished<T>(_ coordinator: Coordinator<T>)
}

/// Card Details Coordinator
class DetailsCoordinator<T: Dependency>: Coordinator<T> {

    private let navigationController: UINavigationController
    private let card: Card

    weak var delegate: DetailsDelegate?

    private(set) lazy var detailViewController = DetailsViewController(nibName: nil, bundle: nil)

    init(dependency: T, navigation: UINavigationController, card: Card) {
        self.navigationController = navigation
        self.card = card
        super.init(dependency: dependency)
    }

    override func start() {
        super.start()
        detailViewController.delegate = self
        detailViewController.viewModel = DetailsViewModel(
            card: card,
            isFavorite: dependency.cardStorage.isExists(cardId: card.cardId)
        )
        navigationController.pushViewController(detailViewController, animated: true)
    }

    override func stop() {
        delegate?.onDetailsFlowFinished(self)
        super.stop()
    }
}

extension DetailsCoordinator: DetailsViewDelegate {
    
    func onDetailsViewFinished(_ viewController: UIViewController) {
        stop()
    }

    func onToggleFavorite(_ viewController: UIViewController) {
        if let cardItem = dependency.cardStorage.findCard(by: card.cardId) {
            dependency.cardStorage.deleteCard(by: cardItem.cardId)
            detailViewController.viewModel = DetailsViewModel(
                card: card,
                isFavorite: false
            )
        } else {
            dependency.cardStorage.createCard(from: card)
            detailViewController.viewModel = DetailsViewModel(
                card: card,
                isFavorite: true
            )
        }
    }
}

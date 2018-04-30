//
// Created by Roman Petryshen on 21/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

class HCAppCoordinator {

    private var childCoordinator: HCCoordinator?

    private let serviceLocator: HCServiceLocatorType

    private let rootViewController: UINavigationController

    init(serviceLocator: HCServiceLocatorType, rootViewController: UINavigationController) {

        self.serviceLocator = serviceLocator
        self.rootViewController = rootViewController
    }

    func start() {

        showCardsList()
    }
}

private extension HCAppCoordinator {

    func showCardsList() {

        let coordinator = HCCardsCoordinator(
                presentationContext: rootViewController,
                apiService: serviceLocator.getApiService(),
                favoritesService: serviceLocator.getFavoriteCardsService())
        coordinator.start()
        childCoordinator = coordinator
    }
}

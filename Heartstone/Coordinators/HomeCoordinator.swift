//
//  HomeCoordinator.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

/// Home (Cards List) Coordinator
class HomeCoordinator<T: Dependency>: Coordinator<T>, RootViewProvider {

    lazy var rootViewController: UIViewController = {
        return navigationVC
    }()

    private(set) lazy var navigationVC: UINavigationController = {
        return UINavigationController(rootViewController: viewController)
    }()

    private(set) lazy var viewController = FavoritesCollectionViewController(
        collectionViewLayout: UICollectionViewFlowLayout()
    )

    override func start() {
        super.start()

        dependency.dataProvider.fetchCardsList { result in
            debugPrint(result)
        }

        viewController.title = NSLocalizedString("Cards", comment: "Cards")
    }
}

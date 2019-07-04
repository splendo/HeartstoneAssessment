//
//  FavoritesCoordinator.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

/// Favorites List Coordinator
class FavoritesCoordinator<T: Dependency>: Coordinator<T>, RootViewProvider {

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

        viewController.title = NSLocalizedString("Favorites", comment: "Favorites")
        viewController.view.backgroundColor = .green
    }
}

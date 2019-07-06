//
//  AppCoordinator.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

/// Main App Coordinator
class AppCoordinator<T: Dependency>: Coordinator<T>, RootViewProvider {

    var rootViewController: UIViewController {
        return tabBarController
    }

    private let window = UIWindow(frame: UIScreen.main.bounds)
    private let tabBarController = UITabBarController()

    override func start() {
        let homeCoordinator = HomeCoordinator(dependency: dependency)
        add(childCoordinator: homeCoordinator)
        homeCoordinator.start()

        let favoritesCoordinator = FavoritesCoordinator(dependency: dependency)
        add(childCoordinator: favoritesCoordinator)
        favoritesCoordinator.start()

        tabBarController.viewControllers = [
            homeCoordinator.rootViewController,
            favoritesCoordinator.rootViewController
        ]
        window.rootViewController = rootViewController
        window.makeKeyAndVisible()
    }
}

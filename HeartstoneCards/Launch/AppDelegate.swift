//
//  AppDelegate.swift
//  HeartstoneCards
//
//  Created by Roman Petryshen on 21/04/2018.
//  Copyright © 2018 Roman Petryshen. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    var appCoordinator: HCAppCoordinator!


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {

        window = UIWindow(frame: UIScreen.main.bounds)

        let navigationVC = UINavigationController()
        navigationVC.view.backgroundColor = .white

        window?.rootViewController = navigationVC
        window?.makeKeyAndVisible()

        appCoordinator = HCAppCoordinator(serviceLocator: HCServiceLocator(), rootViewController: navigationVC)
        appCoordinator.start()

        return true
    }

}


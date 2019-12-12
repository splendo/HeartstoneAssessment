//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    let dependencies = RootDependencies()

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let collectionViewController = dependencies.createCollectionViewController()
        
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.rootViewController = collectionViewController
        window?.makeKeyAndVisible()
        return true
    }
}


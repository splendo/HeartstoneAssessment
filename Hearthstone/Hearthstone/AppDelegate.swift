//
//  AppDelegate.swift
//  Hearthstone
//
//  Created by Dmitrii on 25/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    private var interactor: Interactor!

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        interactor = Interactor(mainWindow: window)
        interactor.appDidLaunched(options: launchOptions)
        return true
    }
}


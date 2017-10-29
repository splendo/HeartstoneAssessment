//
//  Interactor.swift
//  Hearthstone
//
//  Created by Dmitrii on 25/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import UIKit

class Interactor {

    // MARK: - Properties
    private weak var window: UIWindow?
    fileprivate let dataService = DataService()

    // MARK: - Lyfecycle
    init(mainWindow: UIWindow?) {
        window = mainWindow
    }


    // MARK: - Public
    func appDidLaunched(options: [UIApplicationLaunchOptionsKey: Any]?) {
        dataService.loadCards()
        goToCollectionScreen()
        window?.makeKeyAndVisible()
    }


    // MARK: - Private
    private func goToCollectionScreen() {
        let collection = ViewControllerFactory.collectionViewController()
        collection.setDataService(ds: dataService)
        let nc = UINavigationController(rootViewController: collection)
        window?.rootViewController = nc
    }


    // MARK: - Actions
}

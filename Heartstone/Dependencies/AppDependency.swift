//
//  AppDependency.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import CoreData

protocol Dependency {
    var dataProvider: DataProvider { get }
    var cardStorage: CardStorage { get }
}

class AppDependency: Dependency {

    let dataProvider: DataProvider = LocalCardDataProvider()
    let cardStorage: CardStorage

    init() {
        let container = NSPersistentContainer(name: "Heartstone")
        container.loadPersistentStores { (storeDescription, error) in
            debugPrint("CoreData: Inited \(storeDescription)")
            if let error = error {
                assertionFailure("CoreData: Unresolved error \(error)")
            }
        }
        let storage = Storage(container: container)
        cardStorage = CoreDataCardStorage(storage: storage)
    }
}

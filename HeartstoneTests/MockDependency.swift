//
//  MockDependency.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import CoreData
@testable import Heartstone

class MockDataProvider: DataProvider {

    var onFetch: ((DataProvider.FetchCardsCompletion) -> Void)?

    func fetchCardsList(_ completion: @escaping DataProvider.FetchCardsCompletion) {
        onFetch?(completion)
    }
}

class MockCardStorage: CardStorage {

    let storage: Storage

    init(_ storage: Storage) {
        self.storage = storage
    }

    func isExists(cardId: String) -> Bool {
        return false
    }

    func findCard(by cardId: String) -> CardItem? {
        return nil
    }

    func createCard(from card: Card) {
    }

    func deleteCard(by cardId: String) {
    }
}

class MockDependency: Dependency {

    let dataProvider: DataProvider = MockDataProvider()
    let cardStorage: CardStorage = MockCardStorage(
        Storage(container: MockDependency.mockPersistentContainer())
    )

    static func mockPersistentContainer() -> NSPersistentContainer {
        let container = NSPersistentContainer(name: "Heartstone")
        let description = NSPersistentStoreDescription()
        description.type = NSInMemoryStoreType
        container.persistentStoreDescriptions = [description]
        container.loadPersistentStores { (description, error) in
            assert(error == nil)
        }
        return container
    }
}

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

    var findCardValue: CardItem?
    var isExistsValue = false
    var isCreateCardCalled = false
    var isDeleteCardCalled = false

    init(_ storage: Storage) {
        self.storage = storage
    }

    func isExists(cardId: String) -> Bool {
        return isExistsValue
    }

    func findCard(by cardId: String) -> CardItem? {
        return findCardValue
    }

    func createCard(from card: Card) {
        isCreateCardCalled = true
    }

    func deleteCard(by cardId: String) {
        isDeleteCardCalled = true
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

extension Card {
    static func mockCard(cardId: String = "card-id", name: String = "name", type: String = "type") -> Card {
        return Card(
            cardId: cardId,
            name: name,
            type: type,
            img: nil,
            text: nil,
            rarity: nil,
            classes: nil,
            mechanics: nil
        )
    }
}

//
//  MockDependency.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

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
    var dataProvider: DataProvider = MockDataProvider()
    var cardStorage: CardStorage = MockCardStorage(Storage(name: "Heartstone"))
}

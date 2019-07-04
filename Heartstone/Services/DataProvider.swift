//
//  DataProvider.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import Foundation

enum DataProviderError: Error {
    case resourceNotFound
    case parsingFailure(inner: Error)
}

protocol DataProvider {
    typealias FetchCardsResult = Result<[Card], Error>
    typealias FetchCardsCompletion = (FetchCardsResult) -> Void

    func fetchCardsList(_ completion: @escaping FetchCardsCompletion)
}

struct LocalCardDataProvider: DataProvider {

    private let queue = DispatchQueue(label: "LocalCardDataProviderQueue")

    // Completion block will be called on main queue
    func fetchCardsList(_ completion: @escaping FetchCardsCompletion) {
        guard let path = Bundle.main.url(forResource: "cards", withExtension: "json") else {
            DispatchQueue.main.async {
                completion(.failure(DataProviderError.resourceNotFound))
            }
            return
        }

        queue.async {
            do {
                let data = try Data(contentsOf: path)
                let collection = try JSONDecoder().decode(CardCollection.self, from: data)
                // TODO: Debug only
                let filter: (Card) -> Bool = { card in
                    return true
                }
                DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                    completion(.success(collection.cards.filter(filter)))
                }
            } catch {
                DispatchQueue.main.async {
                    completion(.failure(DataProviderError.parsingFailure(inner: error)))
                }
            }
        }
    }
}

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

    static let filter: (Card) -> Bool = { card in
        if
            card.rarity == "Legendary",
            card.mechanics?.first(where: { $0.name == "Deathrattle" }) != nil {
                return true
        }
        return false
    }

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

                let results = collection
                    .cards
                    .filter(LocalCardDataProvider.filter)
                    .sorted(by: { $0.name < $1.name })

                // TODO: Debug only
                DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                    completion(.success(results))
                }
            } catch {
                DispatchQueue.main.async {
                    completion(.failure(DataProviderError.parsingFailure(inner: error)))
                }
            }
        }
    }
}

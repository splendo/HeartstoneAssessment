//
//  DataProvider.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import Foundation

enum DataProviderError: Error {
    case notFound
    case parsingFailed(inner: Error)
}

protocol DataProvider {
    typealias FetchCardsCompletion = (Result<CardCollection, Error>) -> Void
    func fetchCardsList(_ completion: @escaping FetchCardsCompletion)
}

struct LocalCardDataProvider: DataProvider {

    func fetchCardsList(_ completion: @escaping FetchCardsCompletion) {

        guard let path = Bundle.main.url(forResource: "cards", withExtension: "json") else {
            return completion(.failure(DataProviderError.notFound))
        }

        do {
            let data = try Data(contentsOf: path)
            let collection = try JSONDecoder().decode(CardCollection.self, from: data)
            completion(.success(collection))
        } catch {
            completion(.failure(DataProviderError.parsingFailed(inner: error)))
        }
    }
}

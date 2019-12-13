//
// Created by Maxim Berezhnoy on 13/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

final class LocalCollectionRetriever: CollectionRetriever {
    private let retrievalQueue = DispatchQueue(label: "\(LocalCollectionRetriever.self)Queue")
    private let dispatchQueue: DispatchQueue
    private let fileReader: (URL) -> Data?

    init(fileReader: @escaping (URL) -> Data?, dispatchQueue: DispatchQueue = .main) {
        self.fileReader = fileReader
        self.dispatchQueue = dispatchQueue
    }

    enum RetrievalError: Error {
        case fileNotFound
        case fileNotReadable
        case invalidFormat(Error)
    }

    func retrieveCollection(completion: @escaping (Result<[Card], RetrievalError>) -> Void) {
        retrievalQueue.async { [fileReader, weak dispatchQueue] in
            guard let dispatchQueue = dispatchQueue else {
                return
            }

            guard let url = Bundle.main.url(forResource: "cards", withExtension: "json") else {
                dispatchQueue.async {
                    completion(.failure(.fileNotFound))
                }
                return
            }

            guard let data = fileReader(url) else {
                dispatchQueue.async {
                    completion(.failure(.fileNotReadable))
                }
                return
            }

            let jsonDecoder = JSONDecoder()
            do {
                let sets = try jsonDecoder.decode(CardSets.self, from: data)
                dispatchQueue.async {
                    completion(.success(sets.cards))
                }
            } catch {
                dispatchQueue.async {
                    completion(.failure(.invalidFormat(error)))
                }
            }
        }
    }
}
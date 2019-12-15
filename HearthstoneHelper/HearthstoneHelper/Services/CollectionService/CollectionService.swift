//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

// MARK: - Collection sorting
enum SortOrder {
    case ascending
    case descending
}

extension Sequence where Element == Card {
    func sorted(in order: SortOrder) -> [Card] {
        sorted(by: {
            switch order {
            case .ascending: return $0.name < $1.name
            case .descending: return $0.name > $1.name
            }
        })
    }
}

// MARK: - CollectionService
protocol CollectionProviding {
    typealias CardFilter = (Card) -> Bool

    func getCollection(completion: @escaping CollectionRetriever.Completion)
    func getCollection(sortBy order: SortOrder, 
                       filterWith filter: @escaping CardFilter, 
                       completion: @escaping CollectionRetriever.Completion)
}

final class CollectionService: CollectionProviding {
    private let retriever: CollectionRetriever

    init(retriever: CollectionRetriever) {
        self.retriever = retriever
    }

    func getCollection(completion: @escaping CollectionRetriever.Completion) {
        getCollection(sortBy: .ascending, filterWith: { _ in true }, completion: completion)
    }

    func getCollection(sortBy order: SortOrder, 
                       filterWith filter: @escaping CardFilter, 
                       completion: @escaping CollectionRetriever.Completion) {
        retriever.retrieveCollection { result in
            if case let .success(collection) = result {
                let resultCollection = collection
                        .sorted(in: order)
                        .filter(filter)

                completion(.success(resultCollection))
            } else {
                completion(result)
            }
        }
    }
}

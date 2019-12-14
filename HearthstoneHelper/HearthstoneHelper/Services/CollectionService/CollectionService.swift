//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

enum SortOrder {
    case ascending
    case descending
}

protocol CollectionProviding {
    func getCollection(completion: @escaping CollectionRetriever.Completion)
    func getCollection(sortBy order: SortOrder, completion: @escaping CollectionRetriever.Completion)
}

final class CollectionService: CollectionProviding {

    private let retriever: CollectionRetriever

    init(retriever: CollectionRetriever) {
        self.retriever = retriever
    }

    func getCollection(completion: @escaping CollectionRetriever.Completion) {
        getCollection(sortBy: .ascending, completion: completion)
    }

    func getCollection(sortBy order: SortOrder, completion: @escaping CollectionRetriever.Completion) {
        retriever.retrieveCollection { result in
            if case let .success(collection) = result {
                let sortedCollection = collection.sorted {
                    switch order {
                    case .ascending: return $0.name < $1.name
                    case .descending: return $0.name > $1.name
                    }
                }

                completion(.success(sortedCollection))
            } else {
                completion(result)
            }
        }
    }
}
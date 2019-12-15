//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

protocol CollectionServiceCreating {
    func create() -> CollectionProviding
}

class CollectionServiceFactory: CollectionServiceCreating {
    func create() -> CollectionProviding {
        let fileReader: (URL) -> Data? = { url in try? Data(contentsOf: url) }
        let collectionRetriever = LocalCollectionRetriever(fileReader: fileReader)

        return CollectionService(retriever: collectionRetriever)
    }
}
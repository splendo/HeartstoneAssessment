//
// Created by Maxim Berezhnoy on 13/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

protocol CollectionRetriever {
    associatedtype RetrievalError: Error
    
    func retrieveCollection(completion: @escaping (Result<[Card], RetrievalError>) -> Void)
}
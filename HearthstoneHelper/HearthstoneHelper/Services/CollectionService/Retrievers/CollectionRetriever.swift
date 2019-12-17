//
// Created by Maxim Berezhnoy on 13/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

protocol CollectionRetriever {
    typealias Completion = (Result<[Card], Error>) -> Void
    
    func retrieveCollection(completion: @escaping Completion)
}
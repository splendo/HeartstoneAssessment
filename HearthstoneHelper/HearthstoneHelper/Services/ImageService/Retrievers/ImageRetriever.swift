//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

protocol ImageRetriever: class {
    typealias Completion = (Result<Data, Error>) -> Void

    @discardableResult
    func retrieve(from url: URL, completion: @escaping Completion) -> Cancelable
}
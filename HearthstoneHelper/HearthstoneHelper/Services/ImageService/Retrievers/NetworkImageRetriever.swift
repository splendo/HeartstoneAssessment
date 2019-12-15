//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

class NetworkImageRetriever: ImageRetriever {
    private let httpClient: HttpCommunicator

    init(httpClient: HttpCommunicator) {
        self.httpClient = httpClient
    }

    @discardableResult
    func retrieve(from url: URL, completion: @escaping Completion) -> Cancelable {
        httpClient.get(url: url) { result in
            DispatchQueue.main.async {
                switch (result) {
                case .success(let data):
                    completion(.success(data))
                case .failure(let error):
                    completion(.failure(error))
                }
            }
        }
    }
}
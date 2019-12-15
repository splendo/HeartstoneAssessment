//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

protocol ImageProviding {
    @discardableResult
    func fetch(from url: URL, completion: @escaping ImageRetriever.Completion) -> Cancelable?
}

class ImageService: ImageProviding {
    private let imageCache: ImageCaching
    private let imageRetriever: ImageRetriever

    init(imageCache: ImageCaching, imageRetriever: ImageRetriever) {
        self.imageCache = imageCache
        self.imageRetriever = imageRetriever
    }

    @discardableResult
    func fetch(from url: URL, completion: @escaping ImageRetriever.Completion) -> Cancelable? {
        let cacheResult = imageCache.retrieve(by: url)
        
        if case let .success(data) = cacheResult {
            completion(.success(data))
            return nil
        } else {
            return imageRetriever.retrieve(from: url) { [weak imageCache] result in
                switch result {
                case .success(let data):
                    imageCache?.store(imageData: data, for: url)
                    completion(.success(data))
                case .failure(let error):
                    completion(.failure(error))
                }
            }
        }
    }
}
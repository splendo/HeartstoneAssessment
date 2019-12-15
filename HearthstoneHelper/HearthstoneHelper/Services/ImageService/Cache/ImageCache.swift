//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

enum CacheError: Error {
    case nothingStored
}

protocol ImageCaching: class {
    func store(imageData: Data, for url: URL)
    func retrieve(by url: URL) -> Result<Data, CacheError>
}

class ImageCache: ImageCaching {
    let defaultCacheLimitBytes = 30_000_000

    private let cache = { NSCache<NSString, NSData>() }()

    init(cacheLimitBytes: Int? = nil) {
        cache.totalCostLimit = cacheLimitBytes ?? defaultCacheLimitBytes
    }

    func store(imageData: Data, for url: URL) {
        cache.setObject(imageData as NSData, forKey: url.absoluteString as NSString)
    }

    func retrieve(by url: URL) -> Result<Data, CacheError> {
        let cachedData = cache.object(forKey: url.absoluteString as NSString)

        if let cachedData = cachedData {
            return .success(Data(referencing: cachedData))
        } else {
            return .failure(.nothingStored)
        }
    }
}
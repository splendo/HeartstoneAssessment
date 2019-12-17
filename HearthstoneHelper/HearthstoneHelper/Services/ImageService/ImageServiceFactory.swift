//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

protocol ImageServiceCreating {
    func create() -> ImageProviding
}

class ImageServiceFactory: ImageServiceCreating {
    func create() -> ImageProviding {
        let httpClient = Http.Client()
        let imageCache = ImageCache()
        let imageRetriever = NetworkImageRetriever(httpClient: httpClient)

        return ImageService(imageCache: imageCache, imageRetriever: imageRetriever)
    }
}
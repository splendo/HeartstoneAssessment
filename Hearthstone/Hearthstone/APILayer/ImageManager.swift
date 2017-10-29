//
//  ImageManager.swift
//  Hearthstone
//
//  Created by Dmitrii on 26/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import UIKit

class ImageManager {

    // MARK: - Properties
    static let sharedInstance = ImageManager()

    private let maxImagesInCache = 50
    var imageCache = [String: UIImage]()

    // MARK: - Public
    func downloadImage(urlString: String, completion: @escaping (_ image: UIImage?, _ url: String)->()) {
        if let cachedImage = cachedImageForURL(urlString) {
            completion(cachedImage, urlString)
        } else if let url = URL(string: urlString) {
            DispatchQueue.global().async {
                do {
                    let data = try Data(contentsOf: url)
                    guard let image = UIImage(data: data) else {
                        completion(nil, urlString)
                        return
                    }
                    self.cacheImage(image: image, forURL: urlString)
                    DispatchQueue.main.async() {
                        completion(image, urlString)
                    }
                } catch {
                    print(error.localizedDescription)
                    DispatchQueue.main.async() {
                        completion(nil, urlString)
                    }
                }
            }
        }
    }

    // MARK: - Private
    private func cacheImage(image: UIImage, forURL url: String) {
        if imageCache.count > maxImagesInCache {
            imageCache.remove(at: imageCache.startIndex)
        }
        imageCache[url] = image
    }

    private func cachedImageForURL(_ url: String) -> UIImage? {
        return imageCache[url]
    }

    private func clearCache() {
        imageCache.removeAll()
    }
}

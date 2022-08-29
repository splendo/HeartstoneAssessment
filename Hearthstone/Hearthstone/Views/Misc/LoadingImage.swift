//
//  LoadingImage.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 29/8/22.
//

import UIKit

// Add UI Tests
class LoadingImage: UIImageView {
    
    let activityIndicator = UIActivityIndicatorView()
    
    func load(from url: URL, with mode: UIView.ContentMode) {
        
        activityIndicator.color = .primaryColor
        addSubview(activityIndicator)
        activityIndicator.addAnchors(cXAnchor: centerXAnchor, cYAnchor: centerYAnchor)
        activityIndicator.startAnimating()
        
        if let cachedImage  = imageCache.object(forKey: url as AnyObject) as? UIImage {
            image = cachedImage
            activityIndicator.stopAnimating()
            return
        }
        
        URLSession.shared.dataTask(with: url) {  [weak self] data, response, error in
            guard
                let httpURLResponse = response as? HTTPURLResponse,
                httpURLResponse.statusCode == 200,
                let data = data, error == nil,
                let image = UIImage(data: data) else {
                        self?.addPlaceholderImage(from: url, with: mode)
                        return
                }
                DispatchQueue.main.async() {
                    self?.image = image
                    self?.contentMode = mode
                    imageCache.setObject(image, forKey: url as AnyObject)
                    self?.activityIndicator.stopAnimating()
                }
        }.resume()
        
    }

}

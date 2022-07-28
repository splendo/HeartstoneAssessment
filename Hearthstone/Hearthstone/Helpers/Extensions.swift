//
//  Extensions.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 28/7/22.
//

import Foundation
import UIKit


extension UIImageView {
    
    // Create a placeholder image, since some cards don't have "working" img paths.
    // Also, seems cooler ;)
    static var placeholderImageUrl = URL(string: "https://via.placeholder.com/500x500.png?text=No+Image+Found")!
    
    func load(from url: URL) {
        
        DispatchQueue.global().async { [weak self] in
            if let imgData = try? Data(contentsOf: url) {
                if let img = UIImage(data: imgData) {
                    DispatchQueue.main.async {
                        self?.image = img
                    }
                } else {
                    self?.addPlaceholderImage()
                }
            } else {
                self?.addPlaceholderImage()
            }
        }
    }
    
    private func addPlaceholderImage() {
        
        if let imgData = try? Data(contentsOf: UIImageView.placeholderImageUrl) {
            if let img = UIImage(data: imgData) {
                DispatchQueue.main.async { [weak self] in
                    self?.image = img
                }
            }
        }
    }
    
}

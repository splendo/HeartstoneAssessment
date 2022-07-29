//
//  Extensions.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 28/7/22.
//

import Foundation
import UIKit


// - MARK: UIKit Extensions
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

extension HomeTabViewController {
    
    func initView() {
        view.backgroundColor = .systemBackground
        UITabBar.appearance().barTintColor = .systemBackground
        tabBar.tintColor = .label
    }
    
}

extension HomeTabViewController {
    
    func createTabNavigation(for root: UIViewController, with title: String? = "Title", and icon: String? = nil) -> UIViewController {
        let navigation = UINavigationController(rootViewController: root)
        if let title = title {
            navigation.tabBarItem.title = title
        }
        if let icon = icon {
            navigation.tabBarItem.image = UIImage(
                systemName: icon,
                withConfiguration:UIImage.SymbolConfiguration(scale: .large)
            )
        }
        navigation.navigationBar.prefersLargeTitles = true
        root.navigationItem.title = title
        root.navigationItem.largeTitleDisplayMode = .always
        
        return navigation
    }
    
}

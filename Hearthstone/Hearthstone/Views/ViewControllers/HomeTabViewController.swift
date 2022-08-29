//
//  HomeTabViewController.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 26/7/22.
//

import UIKit

class HomeTabViewController: UITabBarController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initView()
        
        let favoritesService = attachFavoritesService()
        
        let allVC = CardsCollectionViewController(collectionViewLayout: UICollectionViewFlowLayout())
        allVC.dataService = CardsDataService(type: .AllCards, favoritesService: favoritesService)
        let favVC = CardsCollectionViewController(collectionViewLayout: UICollectionViewFlowLayout())
        favVC.dataService = CardsDataService(type: .Favorites, favoritesService: favoritesService)
        
        viewControllers = [
            createTabNavigation(for: allVC, with: "Cards", and: "lanyardcard.fill"),
            createTabNavigation(for: favVC, with: "Favorites", and: "star.fill")
        ]
    }
}

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
        
        let allVC = CardsCollectionViewController(collectionViewLayout: UICollectionViewFlowLayout())
        allVC.service = CardsDataService(type: .AllCards)
        let favVC = CardsCollectionViewController(collectionViewLayout: UICollectionViewFlowLayout())
        favVC.service = CardsDataService(type: .Favorites)
        
        viewControllers = [
            createTabNavigation(for: allVC, with: "Cards", and: "lanyardcard.fill"),
            createTabNavigation(for: favVC, with: "Favorites", and: "star.fill")
        ]
    }
}

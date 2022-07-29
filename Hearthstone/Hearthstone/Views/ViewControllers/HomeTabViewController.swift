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
        viewControllers = [
            createTabNavigation(for: CardsCollectionViewController(collectionViewLayout: UICollectionViewFlowLayout()), with: "Cards", and: "lanyardcard.fill"),
            createTabNavigation(for: CardsCollectionViewController(collectionViewLayout: UICollectionViewFlowLayout()), with: "Favorites", and: "star.fill")
        ]
    }
}

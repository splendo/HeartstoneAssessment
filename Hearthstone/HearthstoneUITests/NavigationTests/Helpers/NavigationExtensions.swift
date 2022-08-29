//
//  NavigationExtensions.swift
//  HearthstoneUITests
//
//  Created by Stavros Tsikinas on 29/8/22.
//

import Foundation
import UIKit


extension UICollectionViewController {
    
    func selectCard(at indexPath: IndexPath) {
        collectionView(collectionView, didSelectItemAt: indexPath)
    }
    
}

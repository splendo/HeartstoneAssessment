//
//  FavoritesProvider.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import Foundation

class FavoritesProvider {
    private static let favoritesKey = "com.coolcards.favorites"
    
    public static func setFavorite(cardId: String, favorite: Bool) {
        if var favorites = UserDefaults.standard.value(forKey: favoritesKey) as? [String] {
            if (favorite && !favorites.contains(cardId)) {
                favorites.append(cardId)
            } else if (!favorite && favorites.contains(cardId)) {
                favorites = favorites.filter({ $0 != cardId })
            }
            UserDefaults.standard.set(favorites, forKey: favoritesKey)
        } else {
            UserDefaults.standard.set([cardId], forKey: favoritesKey)
        }
        UserDefaults.standard.synchronize()
    }
    
    public static func getFavorite(cardId: String) -> Bool {
        if let favorites = UserDefaults.standard.value(forKey: favoritesKey) as? [String] {
            return favorites.contains(cardId)
        }
        return false
    }
}

//
//  CacheDataManager.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/12/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

class CacheDataManager {
    
    let cache: NSCache<NSString, CardsCacheObject>
    
    init() {
        self.cache = NSCache<NSString, CardsCacheObject>()
    }
    
    // trying to loading cards from cache
    func loadCards() -> [Card]? {
        if let cachedVersion = cache.object(forKey: CardsCacheObject.cacheKey) {
            return cachedVersion.cards
        }
        return nil
    }
    
    // loading cards from DB
    func cacheCards(_ items: [Card])  {
        let cardObject = CardsCacheObject(items: items)
        cache.setObject(cardObject, forKey: CardsCacheObject.cacheKey)
    }
}

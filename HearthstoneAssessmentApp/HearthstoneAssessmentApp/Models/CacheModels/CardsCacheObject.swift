//
//  CardsCacheObject.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/12/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

class CardsCacheObject {
    
    static let cacheKey: NSString = "CachedCardsKey"
    
    var cards: Array<Card>
    
    required init(items: Array<Card>) {
        self.cards = items
    }
    
}

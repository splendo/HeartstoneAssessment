//
//  CardService.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation
import RealmSwift

class CardService {
    
    let loadDataManager: LoadDataManager = LoadDataManager()
    let realmDataManager: RealmDataManager = RealmDataManager()
    
    func getCards(onSuccess: @escaping ([Card]) -> Void, onFail: @escaping (Error) -> Void) {
        loadDataManager.loadCards(onSuccess: { (cards) in
            onSuccess(cards)
        }) { (error) in
            onFail(error)
        }
    }
    
    func markCardAsFavorite(card: Card) {        
        realmDataManager.updateCard(card: card)
    }
}

//
//  CardDetailListInterator.swift
//  HearthstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/15/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

extension CardListInteractor {
    
    func markFavourite(_ card: Card) {        
        self.cardService.markCardAsFavorite(card: card)
    }
    
}

//
//  CoolCardsDetailViewModel.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import Foundation

final class CoolCardsDetailViewModel: NSObject {
    public var delegate:CoolCardsDetailViewModelDelegate?
    public var card:Card?
    public func load(cardId: String) {
        CoolCardsAPI.getCard(cardId:cardId, completion: { card, error in
            self.card = card
            if error == nil {
                self.delegate?.didLoadModel()
            } else {
                self.delegate?.didFailModelLoadWithError()
            }
        })
    }
}

protocol CoolCardsDetailViewModelDelegate: class {
    func didLoadModel()
    func didFailModelLoadWithError()
}

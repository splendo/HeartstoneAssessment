//
//  CoolCardsViewModel.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import Foundation

final class CoolCardsViewModel: NSObject {
    public var delegate:CoolCardsViewModelDelegate?
    public var cards:[Card]?
    public func load() {
        CoolCardsAPI.getCards(completion: { cards, error in
            self.cards = cards
            if error == nil {
                self.delegate?.didLoadModel()
            } else {
                self.delegate?.didFailModelLoadWithError()
            }
        })
    }
}

protocol CoolCardsViewModelDelegate: class {
    func didLoadModel()
    func didFailModelLoadWithError()
}

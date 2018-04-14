//
//  CardListInteractor.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

protocol CardListView: BaseViewProtocol {
    func setCards(cards: Array<Card>)
}

class CardListInteractor {
    
    // delegate view
    weak private var cardListView: CardListView?
    
    // services
    private let cardService: CardService
    
    // lifecycle
    
    init(pageSize: Int) {
        cardService = CardService()        
    }
    
    func attachView(view: CardListView) {
        cardListView = view
    }
    
    func detachView() {
        cardListView = nil
    }
    
    // MARK: - service methods
    
    func loadCards(applyOffset: Bool = false) {
        self.cardListView?.startLoading()
        self.cardService.getCards(onSuccess: { [weak self] (cards) in
            self?.cardListView?.finishLoading()
            self?.cardListView?.setCards(cards: cards)
        }) { [weak self] (error) in
            self?.cardListView?.finishLoading()
            self?.cardListView?.setError(error: error)
        }
    }    
    
}

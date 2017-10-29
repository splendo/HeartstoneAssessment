//
//  CardsCollectionViewModel.swift
//  Hearthstone
//
//  Created by Dmitrii on 25/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import Foundation

class CardsCollectionViewModel {

    // MARK: - Properties
    private(set) var dataService: DataService
    private var cardsToDisplay = [Card]()

    var currentFilter = CardsCollectionFilter()

    // MARK: - Lyfecycle
    init(dataService: DataService) {
        self.dataService = dataService
    }

    // MARK: - Public
    func cardsAmount() -> Int {
        return cardsToDisplay.count
    }

    func refresh() {
        cardsToDisplay = dataService.cards()
        applyFiltersIfNecessary()
    }

    func card(index: Int) -> Card? {
        guard index>=0 && index < cardsToDisplay.count else {return nil}
        return cardsToDisplay[index]
    }

    // MARK: - Private
    private func applyFiltersIfNecessary() {

        // type
        if let typeToFilter = currentFilter.type {
            cardsToDisplay = cardsToDisplay.filter({ (card) -> Bool in
                return card.type == typeToFilter
            })
        }

        // player class
        if let classToFilter = currentFilter.playerClass {
            cardsToDisplay = cardsToDisplay.filter({ (card) -> Bool in
                return card.playerClass == classToFilter
            })
        }

        // mechanic
        if let mechanicToFilter = currentFilter.mechanicsName {
            cardsToDisplay = cardsToDisplay.filter({ (card) -> Bool in
                for cardMecahnic in card.mechanics {
                    if cardMecahnic.name == mechanicToFilter {
                        return true
                    }
                }
                return false
            })
        }

        // rarity
        if let rarityToFilter = currentFilter.rarity {
            cardsToDisplay = cardsToDisplay.filter({ (card) -> Bool in
                return card.rarity == rarityToFilter
            })
        }
    }
}

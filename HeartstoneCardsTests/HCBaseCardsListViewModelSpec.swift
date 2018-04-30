//
//  HCBaseCardsListViewModelSpec.swift
//  HeartstoneCardsTests
//
//  Created by Roman Petryshen on 30/04/2018.
//  Copyright © 2018 Roman Petryshen. All rights reserved.
//


import Foundation
import Quick
import Nimble

@testable import HeartstoneCards

class HCBaseCardsListViewModelSpec: QuickSpec {
    override func spec() {
        describe("HCBaseCardsListViewModel") {

            let storage = HCFavoritesUserDefaultsStorage(with: UserDefaults.standard, storageKey: "HCBaseCardsListViewModelSpec")
            storage.deleteAll()
            let favoritesService = HCFavoriteCardsService(storage: storage)

            var model: HCBaseCardsListViewModel!
            model = HCBaseCardsListViewModel(favoriteCardsService: favoritesService)

            let createCard:((String) -> HCCardModel) = { cardId in
                return HCCardModel(cardId: cardId, name: "", img: "", imgGold: nil, rarity: nil, type: nil, playerClass: nil, cardSet: nil)
            }

            let cards = [
                createCard("id1"),
                createCard("id2"),
                createCard("id3")
            ]

            model.models = cards

            it("should return correct count") {
                expect(model.getModelsCount()).to(equal(cards.count))
            }

            it("should return correct model id") {
                expect(model.getModelIdAt(0)).to(equal(cards[0].cardId))
                expect(model.getModelIdAt(cards.count-1)).to(equal(cards[cards.count-1].cardId))
            }
        }
    }
}

//
// Created by Roman Petryshen on 24/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation

protocol HCCardDetailsViewModelDelegate: HCListViewModelDelegate {
//    func wantsToSelectModel(index: Int)
//    func wantToOpenFilters()
}

class HCCardDetailsViewModel: HCBaseCardsListViewModel {

    var selectedModelIndex: Int = 0
    var delegate: HCCardDetailsViewModelDelegate?

    required init(favoriteCardsService: HCFavoriteCardsService, selectedModelIndex: Int) {
        self.selectedModelIndex = selectedModelIndex
        super.init(favoriteCardsService: favoriteCardsService)
    }

    func setFavoriteModelAt(_ index: Int, isFavorite: Bool) {
        let identifier = getModelIdAt(index)
        isFavorite ? favoriteCardsService.add(identifier) : favoriteCardsService.remove(identifier)
    }

    func toggleFavoriteStateForModelAt(_ index: Int) {
        let identifier = getModelIdAt(index)
        let shouldBeFavorite = !isFavoriteModelAt(index)
        shouldBeFavorite ? favoriteCardsService.add(identifier) : favoriteCardsService.remove(identifier)
    }

    public func loadMoreData() {
        self.delegate?.wantToLoadMoreData()
    }
}

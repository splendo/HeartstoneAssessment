//
// Created by Roman Petryshen on 21/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation



class HCCardsListViewModel: HCBaseCardsListViewModel {

    var delegate: HCCardsListViewModelDelegate?

    public func loadMoreData() {
        self.delegate?.wantToLoadMoreData()
    }

    public func didSelectModel(at index: Int) {
        self.delegate?.wantsToSelectModel(index: index)
    }

    public func showFilters() {
        self.delegate?.wantToOpenFilters()
    }

    public func showSorting() {
        self.delegate?.wantToOpenSorting()
    }
}

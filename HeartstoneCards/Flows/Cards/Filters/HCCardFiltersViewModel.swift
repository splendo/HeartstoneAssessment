//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation

protocol HCCardFiltersViewModelDelegate: class {
    func wantsToDismiss()
    func wantsToApply(filterData: HCApiFiltersData)
}

class HCCardFiltersViewModel {

    var filtersData: HCApiFiltersData

    weak var delegate: HCCardFiltersViewModelDelegate?

    init(filtersData: HCApiFiltersData, delegate: HCCardFiltersViewModelDelegate) {
        self.filtersData = filtersData
        self.delegate = delegate
    }
}


extension HCCardFiltersViewModel: HCSelectionTableDelegate {

    func didPressApplyButton() {
        delegate?.wantsToApply(filterData: filtersData)
    }

    func didPressCancelButton() {
        delegate?.wantsToDismiss()
    }

    func didSelect(row: Int, in section: Int) {
        let filter = filtersData.filters[section]
        let item = filter.items[row]

        if (filter.isItemSelected(item)) {
            filter.removeSelected(item)
        } else {
            filter.addSelected(item)
        }
    }
}



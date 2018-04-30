//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation

class HCFiltersTableDataAdapter: HCSelectionTableDataAdapterType {

    typealias DataModel = HCApiFiltersData

    private let dataModel: DataModel

    required init(dataModel: DataModel) {
        self.dataModel = dataModel
    }

    func sectionsCount() -> Int {
        return dataModel.filters.count
    }

    func rowsCountIn(_ section: Int) -> Int {
        let filter = dataModel.filters[section]
        return filter.items.count
    }

    func titleForSection(_ section: Int) -> String {
        return dataModel.filters[section].title
    }

    func titleForRow(row: Int, in section: Int) -> String {
        let filter = dataModel.filters[section]
        let item = filter.items[row]
        return item.title
    }

    func isSelected(row: Int, in section: Int) -> Bool {
        let filter = dataModel.filters[section]
        let item = filter.items[row]
        let isSelected = filter.isItemSelected(item)
        return isSelected
    }
}

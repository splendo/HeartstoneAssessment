//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation

public struct HCApiFiltersData {

    private(set) var key: String
    private(set) var filters: [HCApiFilter]

    init(key: String, filters: [HCApiFilter]) {
        self.key = key
        self.filters = filters
    }

    static func cardFilters(key: String = "filter") -> HCApiFiltersData {
        return HCApiFiltersData(key: key, filters: HCApiFiltersData.getPredefinedCardsFilters())
    }

    static func cardSorting(key: String = "sort") -> HCApiFiltersData {
        return HCApiFiltersData(key: key, filters: HCApiFiltersData.getPredefinedSortingPresets())
    }
}

extension HCApiFiltersData {

    static func getPredefinedCardsFilters() -> [HCApiFilter] {
        return [
            HCApiFilter(title: "Rarity", key: "rarity", items: [
                HCApiFilterItem(title: "Common", value: "Common"),
                HCApiFilterItem(title: "Epic", value: "Epic"),
                HCApiFilterItem(title: "Free", value: "Free"),
                HCApiFilterItem(title: "Legendary", value: "Legendary")
            ]),
            HCApiFilter(title: "Faction", key: "faction", items: [
                HCApiFilterItem(title: "Alliance", value: "Alliance"),
                HCApiFilterItem(title: "Neutral", value: "Neutral"),
                HCApiFilterItem(title: "Horde", value: "Horde")
            ])
        ].sorted {
            $0.title < $1.title
        }
    }

    static func getPredefinedSortingPresets() -> [HCApiFilter] {
        return [
            HCApiFilter(title: "Order", key: "_order", items: [
                HCApiFilterItem(title: "Ascending", value: "asc"),
                HCApiFilterItem(title: "Descending", value: "desc"),
            ], isMultiSelectionAllowed: false),
            HCApiFilter(title: "Sort by", key: "_sort", items: [
                HCApiFilterItem(title: "Name", value: "name"),
                HCApiFilterItem(title: "Rarity", value: "rarity"),
                HCApiFilterItem(title: "Type", value: "type"),
            ], isMultiSelectionAllowed: false)
        ]
    }
}

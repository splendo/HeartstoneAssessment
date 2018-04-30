//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation


protocol HCApiFilterItemType {
    var title: String { get }
    var value: String { get }
}

protocol HCApiFilterType {
    var title: String { get }
    var key: String { get }
    var items: [HCApiFilterItemType] { get }
    var isMultiSelectionAllowed: Bool { get set }
}

struct HCApiFilterItem: HCApiFilterItemType {
    let title: String
    let value: String
}

class HCApiFilter: HCApiFilterType {
    typealias Item = HCApiFilterItemType
    typealias ItemsArray = [HCApiFilterItemType]

    let title: String
    let key: String
    let items: ItemsArray
    var isMultiSelectionAllowed: Bool = true

    private(set) var selectedItems: ItemsArray

    init(title: String, key: String, items: ItemsArray, isMultiSelectionAllowed: Bool = true) {
        self.title = title
        self.key = key
        self.items = items
        self.selectedItems = ItemsArray()
        self.isMultiSelectionAllowed = isMultiSelectionAllowed
    }

    func addSelected(_ item: Item) {
        if isMultiSelectionAllowed {
            if (!isItemSelected(item)) {
                selectedItems.append(item)
            }
        } else {
            selectedItems = [item]
        }
    }

    func removeSelected(_ item: Item) {
        selectedItems = selectedItems.filter {
            $0.value != item.value
        }
    }

    func isItemSelected(_ item: Item) -> Bool {
        return selectedItems.contains {
            $0.value == item.value
        }
    }
}

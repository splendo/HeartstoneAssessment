//
//  HCApiFIlterSpec.swift
//  HeartstoneCardsTests
//
//  Created by Roman Petryshen on 30/04/2018.
//  Copyright © 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import Quick
import Nimble

@testable import HeartstoneCards

class HCApiFilterSpec: QuickSpec {
    override func spec() {
        describe("HCApiFilter") {

            var filter: HCApiFilter!

            beforeEach {
                filter = HCApiFilter(title: "", key: "f", items: [])
            }

            it("should insert item") {
                filter.addSelected(HCApiFilterItem(title: "", value: "i"))
                expect(filter.selectedItems.count).to(equal(1))
            }

            it("should remove appropriate item") {
                let item1 = HCApiFilterItem(title: "", value: "i1")
                let item2 = HCApiFilterItem(title: "", value: "i2")
                filter.addSelected(item1)
                filter.addSelected(item2)
                filter.removeSelected(item1)
                expect(filter.selectedItems.first?.value).to(equal(item2.value))
            }

            it("should keep only one selected item when multiselection=false") {
                filter.isMultiSelectionAllowed = false
                let item1 = HCApiFilterItem(title: "", value: "i1")
                let item2 = HCApiFilterItem(title: "", value: "i2")
                filter.addSelected(item1)
                filter.addSelected(item2)
                expect(filter.selectedItems.count).to(equal(1))
                expect(filter.selectedItems.first?.value).to(equal(item2.value))
            }

            it("should check selection") {
                let item1 = HCApiFilterItem(title: "", value: "i1")
                expect(filter.isItemSelected(item1)).to(beFalsy())
                filter.addSelected(item1)
                expect(filter.isItemSelected(item1)).to(beTruthy())
            }
        }
    }
}

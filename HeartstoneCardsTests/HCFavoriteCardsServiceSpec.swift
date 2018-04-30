//
//  HCFavoriteCardsServiceSpec.swift
//  HeartstoneCardsTests
//
//  Created by Roman Petryshen on 30/04/2018.
//  Copyright © 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import Quick
import Nimble

@testable import HeartstoneCards

class HCFavoriteCardsServiceSpec: QuickSpec {

    override func spec() {
        describe("HCFavoriteCardsService") {

            let createService: (Bool) -> HCFavoriteCardsService = { deleteAll in
                let storage = HCFavoritesUserDefaultsStorage(with: UserDefaults.standard, storageKey: "test")
                if deleteAll {
                    storage.deleteAll()
                }
                return HCFavoriteCardsService(storage: storage)
            }

            var service: HCFavoriteCardsService!

            beforeEach {
                service = createService(true)
            }

            it("should add & remove items") {
                let item1 = "1", item2 = "2"
                service.add(item1)
                service.add(item2)
                expect(service.contains(item1)).to(beTruthy())
                expect(service.contains(item2)).to(beTruthy())

                service.remove(item1)
                expect(service.contains(item1)).to(beFalsy())
                expect(service.contains(item2)).to(beTruthy())

                service.remove(item2)
                expect(service.contains(item1)).to(beFalsy())
                expect(service.contains(item2)).to(beFalsy())
            }

            it("should store & load data") {
                let item = "3"
                expect(service.contains(item)).to(beFalsy())
                service.add(item)

                let newService = createService(false)
                expect(newService.contains(item)).to(beTruthy())
            }
        }
    }

}


//
//  HCApiServiceSpec.swift
//  HeartstoneCardsTests
//
//  Created by Roman Petryshen on 30/04/2018.
//  Copyright © 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import Quick
import Nimble


@testable import HeartstoneCards
@testable import Alamofire

class HCApiServiceSpec: QuickSpec {

    override func spec() {
        describe("ApiFilter") {

            let networkMock = HCNetworkServiceMock(baseUrl: "")
            let service = HCApiService(networkService: networkMock)


            it("should send correct parameters for Cards request") {
                let cursor = HCPaginationCursor(start: 1, limit: 2)
                let filtersData = HCApiFiltersData.cardFilters()

                guard let filter = filtersData.filters.first else {
                    fail("filter shouldn't be nil")
                    return
                }
                guard let filterItem = filter.items.first else {
                    fail("filterItem shouldn't be nil")
                    return
                }
                filtersData.filters.first?.addSelected(filterItem)

                service.getCards(cursor: cursor, filters: filtersData, sortingData: nil) { _ in
                }

                expect(networkMock.t_method).to(equal(HCApiDataStructure.GetCardsRequest().method))
                expect(networkMock.t_path).to(equal(HCApiDataStructure.GetCardsRequest().path))

                guard let parameters = networkMock.t_parameters else {
                    fail("Parameters shouldn't be empty")
                    return
                }

                let keys = Array(parameters.keys)
                expect(keys).to(contain("_limit"))
                expect(keys).to(contain("_start"))
                expect(keys).to(contain(filter.key))
            }
        }
    }

}

fileprivate class HCNetworkServiceMock: HCNetworkService {

    var t_method: HTTPMethod?
    var t_path: String?
    var t_parameters: Alamofire.Parameters?

    override public func fetch<T: Decodable>(method: HTTPMethod, path: String, parameters: Alamofire.Parameters?, completion: @escaping (HCResult<T>) -> Void) {
        t_method = method
        t_path = path
        t_parameters = parameters
    }
}

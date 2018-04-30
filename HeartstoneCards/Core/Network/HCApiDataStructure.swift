//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import Alamofire

public struct HCApiDataStructure {

    public struct GetCardsRequest: HCNetworkDataServiceRequestType {
        var path: String {
            get {
                return "cards"
            }
        }
        var method: HTTPMethod {
            get {
                return HTTPMethod.get
            }
        }

        public struct Parameters: HCNetworkDataServiceParametersType {

            static let limitKey = "_limit"
            static let startKey = "_start"

            var cursor: HCPaginationCursor?
            var filtersData: HCApiFiltersData?
            var sortingData: HCApiFiltersData?

            func toDictionary() -> [String: Any] {

                var availableParameters = [[String: Any]]()
                if let cursor = cursor {
                    let cursorDict = [HCPaginationCursor.startUrlKey: String(cursor.start), HCPaginationCursor.limitUrlKey: String(cursor.limit)]
                    availableParameters.append(cursorDict)
                }
                if let filtersData = filtersData {
                    let filtersDict = filtersData.getSelectedFiltersAsParameters()
                    availableParameters.append(filtersDict)
                }
                if let sortingData = sortingData {
                    let sortingDict = sortingData.getSelectedFiltersAsParameters()
                    availableParameters.append(sortingDict)
                }

                let parameters = availableParameters.reduce(into: [String: Any]()) { (result, dictionary) in
                    result.merge(dictionary) { (current, _) in
                        current
                    }
                }

                return parameters
            }
        }

        public typealias Response = HCPaginatedDataResponse<[HCCardModel]>
    }
}


extension HCApiFiltersData {

    func getSelectedFiltersAsParameters() -> [String: Any] {
        return filters.filter({
            $0.selectedItems.count > 0
        }).reduce(into: [String: Any]()) {

            let val: Any = $1.isMultiSelectionAllowed ? $1.selectedItems.map {
                $0.value
            } : $1.selectedItems.first?.value ?? ""

            $0[$1.key] = val
        }
    }
}


public struct HCPaginationCursor: Decodable {

    static let limitUrlKey = "_limit"
    static let startUrlKey = "_start"

    let start: Int
    let limit: Int
}

public struct HCPaginatedDataResponse<T: Decodable>: Decodable {

    let cursor: HCPaginationCursor?
    let data: T
}

protocol HCNetworkDataServiceRequestType {
    var path: String { get }
    var method: HTTPMethod { get }
}

protocol HCNetworkDataServiceParametersType {
    func toDictionary() -> [String: Any]
}



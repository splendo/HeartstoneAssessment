//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import Alamofire

public protocol HCApiServiceType {
    func getCards(cursor: HCPaginationCursor?, filters: HCApiFiltersData?, sortingData: HCApiFiltersData?, completion: @escaping ((HCResult<HCApiDataStructure.GetCardsRequest.Response>) -> Void))
}

public class HCApiService: HCApiServiceType {

    let networkService: HCNetworkService

    init(baseUrl: String) {
        self.networkService = HCNetworkService(baseUrl: baseUrl)
    }

    init(networkService: HCNetworkService) {
        self.networkService = networkService
    }

    public func getCards(cursor: HCPaginationCursor?, filters: HCApiFiltersData?, sortingData: HCApiFiltersData?, completion: @escaping ((HCResult<HCApiDataStructure.GetCardsRequest.Response>) -> Void)) {
        let parameters = HCApiDataStructure.GetCardsRequest.Parameters(cursor: cursor, filtersData: filters, sortingData: sortingData)
        fetch(HCApiDataStructure.GetCardsRequest(), parameters: parameters, completion: completion)
    }

    func fetch<T: Decodable>(_ request: HCNetworkDataServiceRequestType, parameters: HCNetworkDataServiceParametersType, completion: @escaping ((HCResult<T>) -> Void)) {
        networkService.fetch(method: request.method, path: request.path, parameters: parameters.toDictionary(), completion: completion)
    }
}
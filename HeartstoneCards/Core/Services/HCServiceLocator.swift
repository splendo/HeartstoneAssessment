//
// Created by Roman Petryshen on 21/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation

public protocol HCServiceLocatorType {
    func getApiService() -> HCApiServiceType
    func getFavoriteCardsService() -> HCFavoriteCardsService
}


class HCServiceLocator: HCServiceLocatorType {

    private lazy var apiService: HCApiService = {
        return HCApiService(baseUrl: "https://cards-data.herokuapp.com/")
    }()


    init() {
        setupServices()
    }

    func getApiService() -> HCApiServiceType {
        return apiService
    }

    func getFavoriteCardsService() -> HCFavoriteCardsService {
        return HCFavoriteCardsService(storage: HCFavoritesUserDefaultsStorage())
    }
}


private extension HCServiceLocator {

    private func setupServices() {

    }
}

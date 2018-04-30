//
// Created by Roman Petryshen on 23/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation

class HCCardsDataProvider {

    private static let kDefaultCursorLimit = 10
    private static let kDefaultCursorStartIndex = 0

    typealias ApiService = HCApiServiceType
    typealias Card = HCCardModel

    internal var apiService: ApiService

    private let cursorLimit: Int
    private var cursor: HCPaginationCursor?
    private var models = [Card]()

    public var filtersData: HCApiFiltersData? {
        didSet {
            reset()
        }
    }

    public var sortingData: HCApiFiltersData? {
        didSet {
            reset()
        }
    }

    public var isMoreDataAvailable: Bool {
        return cursor != nil
    }

    init(limit: Int = HCCardsDataProvider.kDefaultCursorLimit, apiService: ApiService) {
        self.cursorLimit = limit
        self.apiService = apiService
        self.cursor = HCPaginationCursor(start: HCCardsDataProvider.kDefaultCursorStartIndex, limit: self.cursorLimit)
    }

    func getCards() -> [Card] {
        return models
    }

    func loadNextPage(completion: @escaping (HCError?) -> ()) {
        apiService.getCards(cursor: self.cursor, filters: filtersData, sortingData: sortingData) { [weak self] (response) in

            switch response {
            case .completed(let paginatedResponse):
                self?.models.append(contentsOf: paginatedResponse.data)
                self?.cursor = paginatedResponse.cursor
                completion(nil)
            case .failed(let error):
                completion(error)
            }
        }
    }

    private func reset() {
        models = [Card]()
        cursor = HCPaginationCursor(start: 0, limit: cursorLimit)
    }
}

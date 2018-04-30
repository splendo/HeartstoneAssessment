//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation


public protocol HCListViewModelType {

    associatedtype Model

    var models: [Model] { get }

    func getModelsCount() -> Int
    func getModelAt(_ index: Int) -> Model?
    func isFavoriteModelAt(_ index: Int) -> Bool
}

extension HCListViewModelType {

    public func getModelsCount() -> Int {
        return models.count
    }

    public func getModelAt(_ index: Int) -> Model? {
        return models[index]
    }
}

protocol HCListViewModelDelegate: class {
    func wantToLoadMoreData()
}

protocol HCCardsListViewModelDelegate: HCListViewModelDelegate {
    func wantsToSelectModel(index: Int)
    func wantToOpenFilters()
    func wantToOpenSorting()
}


class HCBaseCardsListViewModel: HCListViewModelType {

    typealias Model = HCCardModel

    var models = [Model]() {
        didSet {
            onNewDataLoaded?()
        }
    }

    public var isMoreDataAvailable = false
    public var onNewDataLoaded: (() -> ())?

    internal let favoriteCardsService: HCFavoriteCardsService

    init(favoriteCardsService: HCFavoriteCardsService) {
        self.favoriteCardsService = favoriteCardsService
    }

    func isFavoriteModelAt(_ index: Int) -> Bool {
        return favoriteCardsService.contains(getModelIdAt(index))
    }

    func getModelIdAt(_ index: Int) -> String {
        guard let identifier = getModelAt(index)?.cardId else {
            fatalError("Index \(index) is out of range")
        }
        return identifier
    }
}

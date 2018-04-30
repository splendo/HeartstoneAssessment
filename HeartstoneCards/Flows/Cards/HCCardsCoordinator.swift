//
// Created by Roman Petryshen on 21/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

protocol HCCardsCoordinatorType: class {
    var cardsDataProvider: HCCardsDataProvider { get }
    func loadMoreData()
    func displayError(_ error: HCError)
    func updateModelWithNewData()
}

extension HCCardsCoordinatorType {

    func displayError(_ error: HCError) {
        print("\(error)")
    }

    func loadMoreData() {
        self.cardsDataProvider.loadNextPage { [weak self] error in
            if let error = error {
                self?.displayError(error)
            } else {
                self?.updateModelWithNewData()
            }
        }
    }
}

class HCCardsCoordinator: HCCoordinator, HCCardsCoordinatorType {

    private let cardFiltersKey = "filters"
    private let cardSortingKey = "sorting"

    let cardsDataProvider: HCCardsDataProvider
    let favoriteCardsService: HCFavoriteCardsService
    let viewModel: HCCardsListViewModel

    lazy var cardFiltersData = {
        return HCApiFiltersData.getPredefinedCardsFilters()
    }()

    weak var navigationController: UINavigationController?

    required init(presentationContext: UINavigationController, apiService: HCApiServiceType, favoritesService: HCFavoriteCardsService, cardsPerPage: Int = 10) {
        self.cardsDataProvider = HCCardsDataProvider(limit: cardsPerPage, apiService: apiService)
        self.navigationController = presentationContext
        self.favoriteCardsService = favoritesService
        self.viewModel = HCCardsListViewModel(favoriteCardsService: favoriteCardsService)
        super.init(presentationContext: presentationContext)
    }

    override func start() {
        favoriteCardsService.refresh(completion: nil)
        viewModel.delegate = self

        let vc = HCCardsListVC(viewModel: viewModel)
        navigationController?.pushViewController(vc, animated: false)

        loadMoreData()
    }

    // MARK: HCCardsCoordinatorType

    func updateModelWithNewData() {
        self.viewModel.models = self.cardsDataProvider.getCards()
        self.viewModel.isMoreDataAvailable = self.cardsDataProvider.isMoreDataAvailable
    }

    // MARK: Navigation

    func presentDetailsForCard(at index: Int) {

        let coordinator = HCCardDetailsCoordinator(
                presentationContext: navigationController!,
                cardsDataProvider: cardsDataProvider,
                selectedCardIndex: index,
                favoriteCardsService: favoriteCardsService)
        coordinator.delegate = self
        self.childCoordinator = coordinator
        coordinator.start()
    }

    func presentCardFilters() {
        let filtersData = cardsDataProvider.filtersData ?? HCApiFiltersData.cardFilters(key: cardFiltersKey)
        let viewModel = HCCardFiltersViewModel(filtersData: filtersData, delegate: self)
        let vc = HCCardFiltersVC(viewModel: viewModel)
        let nc = UINavigationController(rootViewController: vc)

        navigationController?.present(nc, animated: true)
    }

    func presentCardSorting() {
        let sortingData = cardsDataProvider.sortingData ?? HCApiFiltersData.cardSorting(key: cardSortingKey)
        let viewModel = HCCardFiltersViewModel(filtersData: sortingData, delegate: self)
        let vc = HCCardFiltersVC(viewModel: viewModel)
        let nc = UINavigationController(rootViewController: vc)

        navigationController?.present(nc, animated: true)
    }
}

extension HCCardsCoordinator: HCCardsListViewModelDelegate {

    func wantToLoadMoreData() {
        loadMoreData()
    }

    func wantsToSelectModel(index: Int) {
        presentDetailsForCard(at: index)
    }

    func wantToOpenFilters() {
        presentCardFilters()
    }

    func wantToOpenSorting() {
        presentCardSorting()
    }
}

extension HCCardsCoordinator: HCCardFiltersViewModelDelegate {

    func wantsToDismiss() {
        navigationController?.dismiss(animated: true)
    }

    func wantsToApply(filterData: HCApiFiltersData) {
        navigationController?.dismiss(animated: true)

        if (filterData.key == cardFiltersKey) {
            cardsDataProvider.filtersData = filterData
        }
        else if (filterData.key == cardSortingKey) {
            cardsDataProvider.sortingData = filterData
        }

        loadMoreData()
    }
}
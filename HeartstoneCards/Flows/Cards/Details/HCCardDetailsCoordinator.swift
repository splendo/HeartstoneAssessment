//
// Created by Roman Petryshen on 24/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

class HCCardDetailsCoordinator: HCCoordinator, HCCardsCoordinatorType {

    let cardsDataProvider: HCCardsDataProvider
    let favoriteCardsService: HCFavoriteCardsService
    let viewModel: HCCardDetailsViewModel

    weak var navigationController: UINavigationController?

    required init(presentationContext: UINavigationController, cardsDataProvider: HCCardsDataProvider, selectedCardIndex: Int, favoriteCardsService: HCFavoriteCardsService) {
        self.cardsDataProvider = cardsDataProvider
        self.favoriteCardsService = favoriteCardsService
        self.navigationController = presentationContext
        self.viewModel = HCCardDetailsViewModel(favoriteCardsService: favoriteCardsService, selectedModelIndex: selectedCardIndex)
        super.init(presentationContext: presentationContext)
    }

    override func start() {
        viewModel.delegate = self
        viewModel.models = cardsDataProvider.getCards()
        viewModel.isMoreDataAvailable = cardsDataProvider.isMoreDataAvailable

        let vc = HCCardsDetailsVC(viewModel: viewModel)
        navigationController?.pushViewController(vc, animated: true)
    }


    // MARK: HCCardsCoordinatorType

    func updateModelWithNewData() {
        self.viewModel.models = self.cardsDataProvider.getCards()
        self.viewModel.isMoreDataAvailable = self.cardsDataProvider.isMoreDataAvailable
    }
}


extension HCCardDetailsCoordinator: HCCardDetailsViewModelDelegate {
    func wantToLoadMoreData() {
        self.loadMoreData()
    }
}
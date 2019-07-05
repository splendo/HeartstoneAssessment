//
//  HomeCoordinator.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

/// Home (Cards List) Coordinator
class HomeCoordinator<T: Dependency>: Coordinator<T>, RootViewProvider {

    lazy var rootViewController: UIViewController = {
        return navigationViewController
    }()

    private(set) lazy var navigationViewController = UINavigationController()

    private let title = NSLocalizedString("Cards", comment: "Cards")

    override func start() {
        super.start()

        let loadingViewController = LoadingViewController(nibName: nil, bundle: nil)
        navigationViewController.viewControllers = [loadingViewController]
        navigationViewController.tabBarItem.image = UIImage(named: "placeholder")
        loadingViewController.title = title

        dependency.dataProvider.fetchCardsList { result in
            DispatchQueue.main.async { [weak self] in
                guard let self = self else { return }
                self.processResults(result)
            }
        }
    }

    fileprivate func processResults(_ result: DataProvider.FetchCardsResult) {
        guard case .success(let cards) = result, cards.isEmpty == false else {
            let errorViewController = ErrorViewController(nibName: nil, bundle: nil)
            errorViewController.title = title
            navigationViewController.viewControllers = [errorViewController]
            return
        }

        let cardsViewController =  CardsCollectionViewController(
            viewModel: CardListViewModel(cards: cards),
            layout: UICollectionViewFlowLayout()
        )
        cardsViewController.title = title
        cardsViewController.delegate = self
        navigationViewController.viewControllers = [cardsViewController]
    }
}

extension HomeCoordinator: CardsCollectionViewDelegate {

    func didSelectCard(_ card: Card) {
        let detailsCoordinator = DetailsCoordinator(
            dependency: dependency,
            navigation: navigationViewController,
            card: card
        )
        add(childCoordinator: detailsCoordinator)
        detailsCoordinator.start()
    }
}

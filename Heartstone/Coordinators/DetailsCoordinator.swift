//
//  DetailsCoordinator.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

protocol DetailsDelegate: class {
    func onDetailsFlowFinished<T>(_ coordinator: Coordinator<T>)
}

class DetailsCoordinator<T: Dependency>: Coordinator<T> {

    private let navigationController: UINavigationController
    private let cardInfo: CardMinimumDetails

    weak var delegate: DetailsDelegate?

    private(set) lazy var detailViewController = DetailsViewController(nibName: nil, bundle: nil)

    init(dependency: T,
         navigation: UINavigationController,
         cardInfo: CardMinimumDetails) {
        self.navigationController = navigation
        self.cardInfo = cardInfo
        super.init(dependency: dependency)
    }

    override func start() {
        super.start()
        detailViewController.delegate = self
        navigationController.pushViewController(detailViewController, animated: true)
    }

    override func stop() {
        delegate?.onDetailsFlowFinished(self)
        super.stop()
    }
}

extension DetailsCoordinator: DetailsViewDelegate {
    
    func onDetailsViewFinished(_ viewController: UIViewController) {
        stop()
    }
}

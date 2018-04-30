//
// Created by Roman Petryshen on 21/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

protocol HCCoordinatorType: class {

    var presentationContext: UIViewController? { get }

    func start()

    func cleanup(completion: () -> Void)
}

protocol HCCoordinatorDelegate: class {
    func coordinatorDidComplete(coordinator: HCCoordinatorType)
}


class HCCoordinator: HCCoordinatorType, HCCoordinatorDelegate {

    weak var delegate: HCCoordinatorDelegate?
    var childCoordinator: HCCoordinatorType?
    private(set) weak var presentationContext: UIViewController?

    init(presentationContext: UIViewController?, delegate: HCCoordinatorDelegate? = nil) {
        self.presentationContext = presentationContext
        self.delegate = delegate
    }

    func start() {
        preconditionFailure("Implement in subclass")
    }

    func cleanup(completion: () -> Void) {
        completion()
    }

    func coordinatorDidComplete(coordinator: HCCoordinatorType) {
        childCoordinator = nil
    }
}
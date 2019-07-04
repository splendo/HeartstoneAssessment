//
//  Coordinator.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

protocol RootViewProvider {
    var rootViewController: UIViewController { get }
}

/// Base Coordinator class
class Coordinator<T> {

    let dependency: T
    var childCoordinators = [Coordinator]()

    init(dependency: T) {
        self.dependency = dependency
    }

    deinit {
        // Stop self on release
        stop()
    }

    /// Start coordinator
    func start() { }

    /// Stop current and *all* child coordinators
    func stop() { childCoordinators.removeAll() }

    /// Add given child coordinator
    func add(childCoordinator: Coordinator) {
        assert(childCoordinators.contains(where: { $0 === childCoordinator }) == false)
        childCoordinators.append(childCoordinator)
    }

    /// Remove given child coordinator
    func remove(childCoordinator: Coordinator) {
        childCoordinators.removeAll { $0 === childCoordinator }
    }
}

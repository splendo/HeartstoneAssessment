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

    private weak var parent: Coordinator?

    init(dependency: T) {
        self.dependency = dependency
    }

    deinit {
        // Stop self on release
        stop()
        debugPrint("\(self) is about to deinit")
    }

    /// Start coordinator
    func start() {
        debugPrint("\(self) started")
    }

    /// Stop current and *all* child coordinators
    func stop() {
        // Notify parent that we're finished and parent will remove us
        parent?.onChildFinished(self)
        childCoordinators.forEach { $0.stop() }
    }

    /// Add given child coordinator
    func add(childCoordinator: Coordinator) {
        assert(childCoordinators.contains(where: { $0 === childCoordinator }) == false)
        childCoordinators.append(childCoordinator)
        childCoordinator.parent = self
    }

    /// Remove given child coordinator
    func remove(childCoordinator: Coordinator) {
        childCoordinators.removeAll { $0 === childCoordinator }
        childCoordinator.parent = nil
    }

    func onChildFinished(_ coordinator: Coordinator) {
        assert(coordinator !== self)
        remove(childCoordinator: coordinator)
    }
}

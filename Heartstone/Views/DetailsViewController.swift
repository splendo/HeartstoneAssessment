//
//  DetailsViewController.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

protocol DetailsViewDelegate: class {
    func onDetailsViewFinished(_ viewController: UIViewController)
}

class DetailsViewController: UIViewController {

    weak var delegate: DetailsViewDelegate?

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func didMove(toParent parent: UIViewController?) {
        super.didMove(toParent: parent)
        if parent == nil {
            delegate?.onDetailsViewFinished(self)
        }
    }
}

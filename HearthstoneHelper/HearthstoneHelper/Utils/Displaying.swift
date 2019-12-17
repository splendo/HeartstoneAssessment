//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

protocol Displaying {
    var asViewController: UIViewController { get }
}

extension Displaying where Self: UIViewController {
    var asViewController: UIViewController { self }
}
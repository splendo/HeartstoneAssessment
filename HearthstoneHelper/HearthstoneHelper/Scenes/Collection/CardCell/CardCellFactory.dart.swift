//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

protocol CardCellCreating {
    func createCellConfigurator() -> CardCellConfigurator
}

class CardCellFactory: CardCellCreating {
    func createCellConfigurator() -> CardCellConfigurator {
        CardCellConfigurator()
    }
}
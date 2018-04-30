//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

class HCCardFiltersVC: HCSelectionTableVC {

    private let viewModel: HCCardFiltersViewModel

    required init?(coder aDecoder: NSCoder) {
        fatalError("Not implemented")
    }

    required init(viewModel: HCCardFiltersViewModel) {
        self.viewModel = viewModel
        super.init(dataAdapter: HCFiltersTableDataAdapter(dataModel: viewModel.filtersData))
        self.delegate = self.viewModel
        self.title = "Filter cards"
    }
}


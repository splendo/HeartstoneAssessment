//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data
@testable import HearthstoneHelper

class MockCardCellPresenter: CardCellPresenting {
    private(set) var presentNameCallCount = 0
    private(set) var presentNameCallArgument: String?
    private(set) var presentImageCallCount = 0
    private(set) var presentImageCallArgument: Data?
    private(set) var presentLoadingCallCount: Int = 0
    private(set) var presentErrorCallCount: Int = 0
    private(set) var presentNoImageCallCount: Int = 0

    func present(name: String) {
        presentNameCallCount += 1
        presentNameCallArgument = name
    }

    func presentImage(from data: Data) {
        presentImageCallCount += 1
        presentImageCallArgument = data
    }

    func presentLoading() {
        presentLoadingCallCount += 1
    }

    func presentError() {
        presentErrorCallCount += 1
    }

    func presentNoImage() {
        presentNoImageCallCount += 1
    }
} 
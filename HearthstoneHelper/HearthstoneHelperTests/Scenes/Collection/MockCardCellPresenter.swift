//
// Created by Maxim Berezhnoy on 14/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data
@testable import HearthstoneHelper

class MockCardCellPresenter: CardCellPresenting {
    private(set) var presentNameCalls = [String]()
    private(set) var presentImageCalls = [Data]()
    private(set) var presentLoadingCallCount: Int = 0
    private(set) var presentErrorImageCallCount: Int = 0
    private(set) var presentNoImageCallCount: Int = 0

    func present(name: String) {
        presentNameCalls.append(name)
    }

    func presentImage(from data: Data) {
        presentImageCalls.append(data)
    }

    func presentLoading() {
        presentLoadingCallCount += 1
    }

    func presentError() {
        presentErrorImageCallCount += 1
    }

    func presentNoImage() {
        presentNoImageCallCount += 1
    }
} 
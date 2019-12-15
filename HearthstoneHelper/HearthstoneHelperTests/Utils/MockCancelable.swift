//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

@testable import HearthstoneHelper

class MockCancelable: Cancelable {
    private(set) var cancelCallCount = 0

    func cancel() {
        cancelCallCount += 1
    }
}
//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

@testable import HearthstoneHelper

class MockCollectionPresenter: CollectionPresenting {
    private(set) var presentCallCount = 0
    private(set) var presentCallArgument: Collection?
    
    func present(collection: Collection) {
        presentCallCount += 1
        presentCallArgument = collection
    }
}
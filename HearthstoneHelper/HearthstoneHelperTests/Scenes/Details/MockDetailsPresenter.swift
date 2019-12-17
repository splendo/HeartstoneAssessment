//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data
@testable import HearthstoneHelper

class MockDetailsPresenter: DetailsPresenting {
    private(set) var presentImageCallCount = 0
    private(set) var presentImageCallArgument: Data?

    private(set) var presentFavoriteStatusCallCount = 0
    private(set) var presentFavoriteStatusCallArgument: CardMetadata.FavoriteStatus?

    private(set) var presentCardNameCallCount = 0
    private(set) var presentCardNameCallArgument: String?

    private(set) var presentCardFlavorCallCount = 0
    private(set) var presentCardFlavorCallArgument: String?


    func presentImage(from data: Data) {
        presentImageCallCount += 1
        presentImageCallArgument = data
    }

    func present(favoriteStatus: CardMetadata.FavoriteStatus) {
        presentFavoriteStatusCallCount += 1
        presentFavoriteStatusCallArgument = favoriteStatus
    }

    func present(cardName: String) {
        presentCardNameCallCount += 1
        presentCardNameCallArgument = cardName
    }

    func present(cardFlavor: String) {
        presentCardFlavorCallCount += 1
        presentCardFlavorCallArgument = cardFlavor
    }
}

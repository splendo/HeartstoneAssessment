//
// Created by Maxim Berezhnoy on 13/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

typealias CardId = String

enum CardInfo {
    struct Request {
        let id: CardId
    }

    struct Response {
        let id: CardId
        let name: String
        let image: Data?
    }

    struct ViewModel {
        let id: CardId
        let name: String
        let image: UIImage
    }
}
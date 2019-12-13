//
// Created by Maxim Berezhnoy on 13/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

enum CardInfo {
    struct Request {
        let id: Card.ID
    }

    struct Response {
        let id: Card.ID
        let name: String
        let image: Data?
    }

    struct ViewModel {
        let id: Card.ID
        let name: String
        let image: UIImage
    }
}
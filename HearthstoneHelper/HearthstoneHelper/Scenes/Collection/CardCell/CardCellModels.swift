//
// Created by Maxim Berezhnoy on 13/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

enum CardImage {
    struct Request {
        let url: URL
    }

    struct Response {
        let image: Data?
    }

    struct ViewModel {
        let image: UIImage
    }
}
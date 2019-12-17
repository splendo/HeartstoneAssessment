//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.


struct CardMetadata: Codable {
    enum FavoriteStatus: Int, Codable {
        case favorite
        case notFavorite

        func toggled() -> FavoriteStatus {
            switch self {
            case .favorite: return .notFavorite
            case .notFavorite: return .favorite
            }
        }
    }

    let favorite: FavoriteStatus
}
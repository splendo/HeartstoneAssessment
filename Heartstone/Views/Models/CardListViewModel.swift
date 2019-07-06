//
//  CardListViewModel.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import Foundation

struct CardListViewModel {
    /// List of Cards
    let cards: [Card]
}

extension BorderLayer.Side {
    /// Return border sides for given index path
    /// This is related to card list view with given items per row
    static func border(at indexPath: IndexPath,
                       itemsInRow: Int = BaseCollectionViewController.Constants.itemsInRow) -> BorderLayer.Side {
        var result: BorderLayer.Side = [.bottom, .right]
        if indexPath.row < itemsInRow {
            result.insert(.top)
        }
        if indexPath.row % itemsInRow == 0 {
            result.insert([.left])
        }
        return result
    }
}

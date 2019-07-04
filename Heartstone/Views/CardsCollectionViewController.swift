//
//  CardsCollectionViewController.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

class CardsCollectionViewController: BaseCollectionViewController {

    private let viewModel: CardListViewModel

    init(viewModel: CardListViewModel, layout: UICollectionViewLayout) {
        self.viewModel = viewModel
        super.init(collectionViewLayout: layout)
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func configureCell(_ cell: CardViewCell, at indexPath: IndexPath) {
        let card = viewModel.cards[indexPath.row]
        var borderSides: BorderLayer.Side = []
        if indexPath.row < 3 {
            borderSides.insert(.top)
        }
        if indexPath.row % 3 == 0 {
            borderSides.insert([.left, .bottom])
        } else if (indexPath.row - 1) % 3 == 0 {
            borderSides.insert([.left, .bottom, .right])
        } else if (indexPath.row - 2) % 3 == 0 {
            borderSides.insert([.bottom, .right])
        }
        cell.borderedView.borderSides = borderSides
        cell.titleLabel.text = card.name
    }

    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // TODO: Debug only
        return min(14, viewModel.cards.count)
    }
}

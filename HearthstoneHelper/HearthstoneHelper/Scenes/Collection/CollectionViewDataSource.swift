//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

protocol CollectionViewDataSourcing: UICollectionViewDataSource {
    func register(for collectionView: UICollectionView)
    func set(collection: Collection)
}

final class CollectionViewDataSource: NSObject {
    private var collection = Collection(cards: [])
}

// MARK: - CollectionViewDataSourcing
extension CollectionViewDataSource: CollectionViewDataSourcing {
    func register(for collectionView: UICollectionView) {
        collectionView.register(UICollectionViewCell.self, forCellWithReuseIdentifier: "temp")
        collectionView.dataSource = self
    }

    func set(collection: Collection) {
        self.collection = collection
    }
}

// MARK: - UICollectionViewDataSource
extension CollectionViewDataSource: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        collection.cards.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "temp", for: indexPath)

        let label = UILabel(frame: cell.bounds)
        label.text = collection.cards[indexPath.item].name
        
        cell.subviews.forEach({ $0.removeFromSuperview() })
        cell.addSubview(label)

        return cell
    }
}

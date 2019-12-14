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
    private let cellConfigurator: CardCellConfiguring

    init(cellConfigurator: CardCellConfiguring) {
        self.cellConfigurator = cellConfigurator
    }

    private var collection = Collection(cards: [])
}

// MARK: - CollectionViewDataSourcing
extension CollectionViewDataSource: CollectionViewDataSourcing {
    func register(for collectionView: UICollectionView) {
        collectionView.register(CardCellView.self, forCellWithReuseIdentifier: CardCellView.reuseIdentifier)
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
        let cell = dequeueCardCell(from: collectionView, for: indexPath)
        
        cellConfigurator.configure(cell)
        
        let cardInfo = collection.cards[indexPath.item]
        cell.interactor?.updateCard(from: cardInfo)
        
        return cell
    }
}

// MARK: - CardCellView dequeuing
extension CollectionViewDataSource {
    func dequeueCardCell(from collectionView: UICollectionView, for indexPath: IndexPath) -> CardCellView {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CardCellView.reuseIdentifier, for: indexPath)
        if let cardCell = cell as? CardCellView {
            return cardCell
        } else {
            fatalError("Unexpected cell type while dequeuing in \(CollectionViewDataSource.self): got \(cell)")
        }
    }
}
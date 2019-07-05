//
//  FavoritesCollectionViewController.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit
import CoreData

protocol FavoritesCollectionViewDelegate: class {
    func didSelectCardItem(_ cardItem: CardItem)
}

class FavoritesCollectionViewController: BaseCollectionViewController {

    var fetchedResultsController: NSFetchedResultsController<CardItem>? {
        didSet {
            try? fetchedResultsController?.performFetch()
        }
    }

    weak var delegate: FavoritesCollectionViewDelegate?

    override func configureCell(_ cell: CardViewCell, at indexPath: IndexPath) {
        guard let cardItem = fetchedResultsController?.object(at: indexPath) else {
            return
        }

        cell.viewModel = CardViewModel(
            name: cardItem.name,
            imageURL: cardItem.imageURL,
            borderSides: BorderLayer.Side.border(at: indexPath)
        )
    }
}

extension FavoritesCollectionViewController {

    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return fetchedResultsController?.sections?.count ?? 0
    }

    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return fetchedResultsController?.sections?[section].numberOfObjects ?? 0
    }

    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let cardItem = fetchedResultsController?.object(at: indexPath) else {
            assertionFailure("Can't find Card object for given indexPath \(indexPath)")
            return
        }

        delegate?.didSelectCardItem(cardItem)
    }
}

extension FavoritesCollectionViewController: NSFetchedResultsControllerDelegate {

    func controllerDidChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        DispatchQueue.main.async { [weak collectionView] in
            collectionView?.reloadData()
        }
    }

    func controllerWillChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) { }

    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>,
                    didChange anObject: Any,
                    at indexPath: IndexPath?,
                    for type: NSFetchedResultsChangeType,
                    newIndexPath: IndexPath?) { }
}

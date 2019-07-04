//
//  FavoritesCollectionViewController.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit
import CoreData

class FavoritesCollectionViewController: BaseCollectionViewController {

    var fetchedResultsController: NSFetchedResultsController<CardItem>? {
        didSet {
            try? fetchedResultsController?.performFetch()
        }
    }

    fileprivate var blockOperations = [BlockOperation]()


    deinit {
        blockOperations.forEach {
            $0.cancel()
        }
        blockOperations.removeAll(keepingCapacity: false)
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
        guard let card = fetchedResultsController?.object(at: indexPath) else {
            assertionFailure("Can't find Card object for given indexPath \(indexPath)")
            return
        }

        delegate?.didSelectCardInfo(card)
    }
}

extension FavoritesCollectionViewController: NSFetchedResultsControllerDelegate {

    func controllerWillChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
//        DispatchQueue.main.async { }
    }

    func controllerDidChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        DispatchQueue.main.async { [weak self] in
            self?.collectionView?.performBatchUpdates({ [weak self] in
                self?.blockOperations.forEach {
                    $0.start()
                }
            }) { [weak self] finished in
                self?.blockOperations.removeAll(keepingCapacity: false)
            }
        }
    }

    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>,
                    didChange anObject: Any,
                    at indexPath: IndexPath?,
                    for type: NSFetchedResultsChangeType,
                    newIndexPath: IndexPath?) {

        DispatchQueue.main.async { [weak self] in
            switch type {
            case .insert:
                guard let indexPath = newIndexPath else { break }

                self?.blockOperations.append(
                    BlockOperation {
                        self?.collectionView?.insertItems(at: [indexPath])
                    }
                )
            case .delete:
                guard let indexPath = indexPath else { break }

                self?.blockOperations.append(
                    BlockOperation {
                        self?.collectionView?.deleteItems(at: [indexPath])
                    }
                )
            case .move:
                guard let indexPath = indexPath, let newIndexPath = newIndexPath else { break }

                self?.blockOperations.append(
                    BlockOperation {
                        self?.collectionView?.deleteItems(at: [indexPath])
                        self?.collectionView?.insertItems(at: [newIndexPath])
                    }
                )
            case .update:
                guard let indexPath = indexPath else { break }

                self?.blockOperations.append(
                    BlockOperation{
                        self?.collectionView?.reloadItems(at: [indexPath])
                    }
                )
            @unknown default:
                ()
            }
        }
    }
}

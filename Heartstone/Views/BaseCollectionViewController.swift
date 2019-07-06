//
//  BaseCollectionViewController.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import UIKit

class BaseCollectionViewController: UICollectionViewController {

     struct Constants {
        static let itemsInRow = 3
        static let lineSpacing: CGFloat = 0
        static let rowSpacing: CGFloat = 0
        static let cellHeight: CGFloat = 160

        static func cellWidth(in view: UIView) -> CGFloat {
            assert(itemsInRow >= 0)
            let availableWidth = view.bounds.width - (lineSpacing * CGFloat(itemsInRow - 1))
            return availableWidth / CGFloat(itemsInRow)
        }
    }

    var flowLayout: UICollectionViewFlowLayout? {
        return collectionView?.collectionViewLayout as? UICollectionViewFlowLayout
    }

    func configureCell(_ cell: CardViewCell, at indexPath: IndexPath) {
        /* Call in subclass to configure Cell */
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        flowLayout?.itemSize = CGSize(width: Constants.cellWidth(in: self.view) , height: Constants.cellHeight)
        flowLayout?.minimumLineSpacing = Constants.lineSpacing
        flowLayout?.minimumInteritemSpacing = Constants.rowSpacing

        collectionView?.alwaysBounceVertical = true
        collectionView?.backgroundColor = .lightGray
        collectionView?.register(CardViewCell.self)
    }

    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }

    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        fatalError("Should be overriden in subclass")
    }

    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell: CardViewCell = collectionView.dequeue(at: indexPath)
        configureCell(cell, at: indexPath)
        return cell
    }
}

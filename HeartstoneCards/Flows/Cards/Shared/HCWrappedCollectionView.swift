//
// Created by Roman Petryshen on 23/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

class HCWrappedCollectionView: UIView {

    public let collectionView: UICollectionView

    required init?(coder aDecoder: NSCoder) {
        self.collectionView = UICollectionView(frame: .zero, collectionViewLayout: UICollectionViewFlowLayout())
        super.init(coder: aDecoder)
        setup()
    }

    override init(frame: CGRect) {
        self.collectionView = UICollectionView(frame: .zero, collectionViewLayout: UICollectionViewFlowLayout())
        super.init(frame: frame)
        setup()
    }

    required init(with collectionViewLayout: UICollectionViewLayout) {
        self.collectionView = UICollectionView(frame: .zero, collectionViewLayout: collectionViewLayout)
        super.init(frame: .zero)
        setup()
    }

    func setup() {

        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.backgroundColor = .white
        collectionView.allowsSelection = true
        collectionView.allowsMultipleSelection = false

        addSubview(collectionView)

        NSLayoutConstraint.activate([
            collectionView.topAnchor.constraint(equalTo: topAnchor),
            collectionView.bottomAnchor.constraint(equalTo: bottomAnchor),
            collectionView.leftAnchor.constraint(equalTo: leftAnchor),
            collectionView.rightAnchor.constraint(equalTo: rightAnchor),
        ])
    }
}

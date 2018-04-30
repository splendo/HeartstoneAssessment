//
// Created by Roman Petryshen on 23/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

class HCCardsListDetailsLayout: UICollectionViewFlowLayout {

    override init() {
        super.init()
        setupLayout()
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setupLayout()
    }

    func setupLayout() {
        minimumInteritemSpacing = 0
        minimumLineSpacing = 0
        scrollDirection = .horizontal
        sectionInset = .zero;
        footerReferenceSize = .zero;
        headerReferenceSize = .zero;
    }

    func itemWidth() -> CGFloat {
        return collectionView!.frame.width
    }

    override var itemSize: CGSize {
        set {
            self.itemSize = CGSize(width: itemWidth(), height: collectionView!.bounds.height)
        }
        get {
            return CGSize(width: itemWidth(), height: collectionView!.bounds.height)
        }
    }
}

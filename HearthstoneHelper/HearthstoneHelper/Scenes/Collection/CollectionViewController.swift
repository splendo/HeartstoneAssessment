//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

protocol CollectionDisplaying: class {
    func display(cards: Collection)
}

final class CollectionViewController: UIViewController {
    private let dataSource: CollectionViewDataSourcing
    private let interactor: CollectionInteracting

    private lazy var collectionView: UICollectionView = {
        let margin: CGFloat = 15

        let layout = UICollectionViewFlowLayout()
        layout.sectionInset = UIEdgeInsets(top: margin, left: margin, bottom: margin, right: margin)

        let view = UICollectionView(frame: .zero, collectionViewLayout: layout)
        view.backgroundColor = .white
        view.alwaysBounceVertical = true
        return view
    }()

    init(dataSource: CollectionViewDataSourcing, interactor: CollectionInteracting) {
        self.dataSource = dataSource
        self.interactor = interactor

        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        setupCollectionView()

        interactor.fetchCollection()
    }

    private func setupCollectionView() {
        collectionView.delegate = self
        self.view = collectionView

        dataSource.register(for: collectionView)
    }
}

// MARK: - CollectionDisplaying
extension CollectionViewController: CollectionDisplaying {
    func display(cards: Collection) {
        dataSource.set(collection: cards)
        collectionView.reloadData()
    }
}

// MARK: - UICollectionViewDelegate
extension CollectionViewController: UICollectionViewDelegate {

}

//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

protocol CollectionDisplaying: class, Displaying {
    func display(cards: Collection)
}

final class CollectionViewController: UIViewController {
    private let dataSource: CollectionViewDataSourcing
    private let interactor: CollectionInteracting
    private let router: CollectionRouting

    let itemsPerRow = 3
    let margin: CGFloat = 15
    let cardHeightRatio: CGFloat = 1.51

    private lazy var collectionView: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
        layout.sectionInset = UIEdgeInsets(top: margin, left: margin, bottom: margin, right: margin)

        let view = UICollectionView(frame: .zero, collectionViewLayout: layout)
        view.backgroundColor = Style.ScreenBackground.color
        view.alwaysBounceVertical = true

        return view
    }()

    init(dataSource: CollectionViewDataSourcing, interactor: CollectionInteracting, router: CollectionRouting) {
        self.dataSource = dataSource
        self.interactor = interactor
        self.router = router

        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        navigationItem.title = "Collection"

        setupCollectionView()

        interactor.fetchCollection()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        collectionView.reloadData()
    }

    private func setupCollectionView() {
        collectionView.delegate = self

        view.addSubview(collectionView)

        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.widthAnchor.constraint(equalTo: view.widthAnchor).isActive = true
        collectionView.heightAnchor.constraint(equalTo: view.heightAnchor).isActive = true
        collectionView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        collectionView.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true

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
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let card = dataSource.cardAt(index: indexPath.item)

        router.routeToDetailsView(withInfoFrom: card)
    }
}

// MARK: - UICollectionViewDelegateFlowLayout
extension CollectionViewController: UICollectionViewDelegateFlowLayout {
    public func collectionView(_ collectionView: UICollectionView,
                               layout collectionViewLayout: UICollectionViewLayout,
                               sizeForItemAt indexPath: IndexPath) -> CGSize {
        guard let flowLayout = collectionViewLayout as? UICollectionViewFlowLayout
                else { return .zero }

        let collectionViewWidth = collectionView.bounds.width - margin * 2

        let totalInteritemSpacing = CGFloat(itemsPerRow - 1) * flowLayout.minimumInteritemSpacing

        let itemWidth = (collectionViewWidth - totalInteritemSpacing) / CGFloat(itemsPerRow)

        return CGSize(width: itemWidth, height: itemWidth * cardHeightRatio)
    }
}

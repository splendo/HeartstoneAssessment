//
// Created by Roman Petryshen on 23/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

class HCCardsDetailsVC: UIViewController {

    private let viewModel: HCCardDetailsViewModel
    var favoriteButton: HCFavoriteButton?

    var listView: HCWrappedCollectionView?

    init(viewModel: HCCardDetailsViewModel) {
        self.viewModel = viewModel
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("Not implemented")
    }

    override func loadView() {
        super.loadView()
        listView = createListView()
        self.view = listView
        createFavoriteButton()
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Card details"
        edgesForExtendedLayout = .bottom

        self.viewModel.onNewDataLoaded = { [weak self] in
            self?.listView?.collectionView.reloadData()
        }
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        scrollContentTo(viewModel.selectedModelIndex)
        updateViewWithModelAt(viewModel.selectedModelIndex)
    }


    private func createListView() -> HCWrappedCollectionView {
        let newListView = HCWrappedCollectionView(with: HCCardsListDetailsLayout())
        newListView.backgroundColor = .white
        newListView.collectionView.dataSource = self
        newListView.collectionView.delegate = self
        newListView.collectionView.isPagingEnabled = true
        newListView.collectionView.contentInsetAdjustmentBehavior = .never
        newListView.collectionView.register(UINib(nibName: HCCardsDetailsCell.getNibName(), bundle: nil), forCellWithReuseIdentifier: HCCardsDetailsCell.getIdentifier())
        return newListView
    }

    private func createFavoriteButton() {
        let button = HCFavoriteButton()
        button.addTarget(self, action: #selector(didPressFavoriteButton), for: .touchUpInside)
        let barButton = UIBarButtonItem(customView: button)
        navigationItem.setRightBarButton(barButton, animated: false)
        favoriteButton = button
    }


    @objc private func didPressFavoriteButton() {
        guard let index = getIndexPathForVisibleCell()?.row else {
            return
        }

        if !listView!.collectionView.isDragging
                   && !listView!.collectionView.isTracking {
            viewModel.toggleFavoriteStateForModelAt(index)
        }

        updateViewWithModelAt(index)
    }


    func scrollContentTo(_ index: Int) {
        listView?.collectionView.setNeedsLayout()
        listView?.collectionView.layoutIfNeeded()
        listView?.collectionView.scrollToItem(
                at: IndexPath(row: index, section: 0),
                at: .centeredHorizontally,
                animated: false)
    }

    func updateViewWithModelAt(_ index: Int) {
        favoriteButton?.isFavorite = viewModel.isFavoriteModelAt(index)
    }
}


// MARK: UICollectionViewDataSource & UICollectionViewDelegate

extension HCCardsDetailsVC: UICollectionViewDataSource, UICollectionViewDelegate {

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return viewModel.getModelsCount()
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: HCCardsDetailsCell.getIdentifier(), for: indexPath) as! HCCardsDetailsCell
        if let model = viewModel.getModelAt(indexPath.row) {
            cell.setImageUrlString(model.img)
            cell.titleLabel.text = model.name
            cell.subtitleLabel.text = model.getCardDescription()
        }
        return cell
    }

    func collectionView(_ collectionView: UICollectionView,
                        willDisplay cell: UICollectionViewCell,
                        forItemAt indexPath: IndexPath) {
        if viewModel.isMoreDataAvailable && indexPath.row + 2 == viewModel.getModelsCount() {
            viewModel.loadMoreData()
        }
    }

    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        if let indexPath = getIndexPathForVisibleCell() {
            updateViewWithModelAt(indexPath.row)
        }
    }

    func getIndexPathForVisibleCell() -> IndexPath? {
        return listView?.collectionView.indexPathsForVisibleItems.first
    }
}


fileprivate extension HCCardModel {

    func getCardDescription() -> String {
        var properties = [String]()
        properties.addNonEmpty(cardSet)
        properties.addNonEmpty(type)
        properties.addNonEmpty(rarity)

        return properties.count > 0 ? properties.joined(separator: " | ") : ""
    }

}


extension Array where Element: Hashable {

    mutating func addNonEmpty(_ obj: Element?) {
        if let obj = obj {
            append(obj)
        }
    }

}

//
// Created bblacky Roman Petryshen on 21/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

class HCCardsListVC: UIViewController {

    private let viewModel: HCCardsListViewModel

    var collectionView: UICollectionView?
    var activityIndicator: UIActivityIndicatorView?

    init(viewModel: HCCardsListViewModel) {
        self.viewModel = viewModel
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("Not needed")
    }

    override func loadView() {
        super.loadView()
        title = "Cards"

        let newView = createListView()
        self.view = newView
        collectionView = newView.collectionView
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.collectionView?.reloadData()
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        addActivityIndicator()
        navigationItem.rightBarButtonItem = createFiltersButton()
        navigationItem.leftBarButtonItem = createSortingButton()

        self.viewModel.onNewDataLoaded = { [weak self] in
            self?.collectionView?.reloadData()
            self?.removeActivityIndicator()
        }
    }

    //MARK: View helpers

    private func createListView() -> HCWrappedCollectionView {
        let newListView = HCWrappedCollectionView(with: HCCardsListGridLayout())
        newListView.backgroundColor = .white
        newListView.collectionView.dataSource = self
        newListView.collectionView.delegate = self
        let nib = UINib(nibName: HCCardsListCell.getNibName(), bundle: nil)
        newListView.collectionView.register(nib, forCellWithReuseIdentifier: HCCardsListCell.getIdentifier())
        return newListView
    }

    private func addActivityIndicator() {
        let activityIndicator = UIActivityIndicatorView(activityIndicatorStyle: .gray)
        self.activityIndicator = activityIndicator
        activityIndicator.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(activityIndicator)
        NSLayoutConstraint.activate([
            view.centerXAnchor.constraint(equalTo: activityIndicator.centerXAnchor),
            view.centerYAnchor.constraint(equalTo: activityIndicator.centerYAnchor),
        ])
        activityIndicator.startAnimating()
    }

    private func removeActivityIndicator() {
        self.activityIndicator?.removeFromSuperview()
        self.activityIndicator = nil
    }

    private func createFiltersButton() -> UIBarButtonItem {
        return UIBarButtonItem(title: "Filter", style: .plain, target: self, action: #selector(onFilterButtonPressed))
    }

    private func createSortingButton() -> UIBarButtonItem {
        return UIBarButtonItem(title: "Sort", style: .plain, target: self, action: #selector(onSortButtonPressed))
    }


    // MARK: Actions

    @objc private func onFilterButtonPressed() {
        viewModel.showFilters()
    }

    @objc private func onSortButtonPressed() {
        viewModel.showSorting()
    }
}

// MARK: UICollectionViewDataSource & UICollectionViewDelegate

extension HCCardsListVC: UICollectionViewDataSource, UICollectionViewDelegate {

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return viewModel.getModelsCount()
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: HCCardsListCell.getIdentifier(), for: indexPath) as! HCCardsListCell
        if let model = viewModel.getModelAt(indexPath.row) {
            cell.setImageUrlString(model.img)
            cell.isFavorite = viewModel.isFavoriteModelAt(indexPath.row)
        }
        return cell
    }

    func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        if viewModel.isMoreDataAvailable && indexPath.row + 1 == viewModel.getModelsCount() {
            viewModel.loadMoreData()
        }
    }

    public func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        viewModel.didSelectModel(at: indexPath.row)
    }
}

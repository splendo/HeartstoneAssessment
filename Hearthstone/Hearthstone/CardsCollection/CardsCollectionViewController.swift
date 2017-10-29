//
//  CardsCollectionViewController.swift
//  Hearthstone
//
//  Created by Dmitrii on 25/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import UIKit

class CardsCollectionViewController: UIViewController {

    // MARK: - Properties
    fileprivate let cardCellId = "CardCellId"

    fileprivate var dataModel: CardsCollectionViewModel!
    @IBOutlet private var collectionView: UICollectionView!
    @IBOutlet private var loader: UIActivityIndicatorView!
    @IBOutlet private var screenStateLabel: UILabel!

    // MARK: - Lyfecycle
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(cardsUpdated(_:)),
            name: dataServiceCardsUpdatedNotification,
            object: nil
        )
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Cards"
        setScrennState(.Loading)
    }

    // MARK: - Public
    func setDataService(ds: DataService) {
        dataModel = CardsCollectionViewModel(dataService: ds)
    }

    @objc func cardsUpdated(_ notification: Notification) {
        DispatchQueue.main.async { [weak self] in
            if let error = notification.userInfo?[dataServiceCardsUpdatedNotificationErrorKey] as? Error {
                self?.setScrennState(.Empty, message: error.localizedDescription)
            } else {
                self?.dataModel.refresh()
                self?.collectionView.reloadData()
                if self?.dataModel.cardsAmount() == 0 {
                    self?.setScrennState(.Empty)
                } else {
                    self?.setScrennState(.Items)
                }
            }
        }
    }

    // MARK: - Private
    private func setScrennState(_ newState: ScreenState, message: String? = nil) {
        switch newState {
        case .Empty:
            let text = message ?? "No items to display"
            screenStateLabel.text = text
            screenStateLabel.isHidden = false
            collectionView.isHidden = true
            loader.stopAnimating()
        case .Items:
            screenStateLabel.isHidden = true
            collectionView.isHidden = false
            loader.stopAnimating()
        case .Loading:
            screenStateLabel.isHidden = true
            collectionView.isHidden = true
            loader.startAnimating()
        }
    }


    // MARK: - Actions
    @IBAction func filterButtonPressed() {
        let filterViewController = ViewControllerFactory.collectionFilterViewController()
        filterViewController.setFilter(filter: dataModel.currentFilter)
        filterViewController.delegate = self
        let nc = UINavigationController(rootViewController: filterViewController)
        present(nc, animated: true, completion: nil)
    }
}


extension CardsCollectionViewController: UICollectionViewDelegate {

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let card = dataModel.card(index: indexPath.item) else {return}
        let detailViewController = ViewControllerFactory.detailViewController()
        detailViewController.setCard(card: card)
        navigationController?.pushViewController(detailViewController, animated: true)
    }
}


extension CardsCollectionViewController: UICollectionViewDataSource {

    public func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return dataModel.cardsAmount()
    }

    public func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: cardCellId, for: indexPath) as! CardsCollectionViewCell
        if let card = dataModel.card(index: indexPath.item) {
            let cellModel = CardsCollectionViewCellModel(card: card)
            cell.setModel(cellModel)
        }
        return cell
    }
}


extension CardsCollectionViewController: CollectionFilterViewControllerDelegate {
    func dismissController(controller: UIViewController, filter: CardsCollectionFilter?) {
        if let filter = filter {
            dataModel.currentFilter = filter
            dataModel.refresh()
            collectionView.reloadData()
            if dataModel.cardsAmount() == 0 {
                setScrennState(.Empty)
            } else {
                setScrennState(.Items)
            }
        }
        controller.dismiss(animated: true, completion: nil)
    }
}


enum ScreenState {
    case Items, Empty, Loading
}

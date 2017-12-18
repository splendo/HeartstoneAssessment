//
//  CoolCardsViewController.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import UIKit

class CoolCardsViewController: BaseFlowCollectionViewController {
    
    fileprivate let refreshControl: UIRefreshControl = UIRefreshControl()
    fileprivate let viewModel: CoolCardsViewModel = CoolCardsViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setupUI()
        self.setupCollectionView()
        self.setupBindings()
        self.refresh()
    }
    
    fileprivate func setupUI() {
        self.title = NSLocalizedString("Cards", comment: "Cards View Controller Title")
    }
    
    fileprivate func setupCollectionView() {
        self.collectionView.addSubview(self.refreshControl)
        self.collectionView.registerReusableCell(CoolCardsCollectionViewCell.self)
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
    }
    
    fileprivate func setupBindings() {
        self.viewModel.delegate = self
        self.refreshControl.addTarget(self, action: #selector(refresh), for: .valueChanged)
    }
    
    @objc fileprivate func refresh() {
        self.viewModel.load()
    }
}

extension CoolCardsViewController: UICollectionViewDataSource {
    public func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if let cards = viewModel.cards {
            return cards.count
        } else {
            return 0
        }
    }
    
    open func collectionView(_ collectionView: UICollectionView,
                             cellForItemAt indexPath: IndexPath) -> UICollectionViewCell
    {
        guard let cell = self.collectionView.dequeueReusableCell(withReuseIdentifier: CoolCardsCollectionViewCell.DefaultReuseIdentifier, for: indexPath) as? CoolCardsCollectionViewCell else {
            fatalError("Could not dequeue cell with identifier: \(CoolCardsCollectionViewCell.DefaultReuseIdentifier)")
        }
        
        if let card = self.viewModel.cards?[indexPath.row] {
            cell.populate(imageURL: URL(string: card.img), title: card.name, description: card.type)
            cell.didTap = { [weak self] cell in
                let viewController = CoolCardsDetailViewController(nibName: "CoolCardsDetailViewController", bundle: nil)
                viewController.cardId = card.cardId
                self?.navigationController?.pushViewController(viewController, animated: true)
            }
        }
        return cell
    }
}

extension CoolCardsViewController : CoolCardsViewModelDelegate {
    func didLoadModel() {
        self.collectionView.reloadData()
    }
    
    func didFailModelLoadWithError() {
        
    }
}

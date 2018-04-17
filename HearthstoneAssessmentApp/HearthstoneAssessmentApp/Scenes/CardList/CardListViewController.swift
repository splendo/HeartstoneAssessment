//
//  ViewController.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/10/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import UIKit
import MBProgressHUD

struct CardListContstans {
    static let reuseIdentifier = "CardCell"
    static let segueDetail = "CardDetailSegue"
    static let segueFilter = "FilterSegue"
}

class CardListViewController: UICollectionViewController {
    
    @IBOutlet var cardCollectionView: UICollectionView!
    
    var activityIndicator: MBProgressHUD?
    
    let cardListInteractor = CardListInteractor()
    var currentSelectedIndex: IndexPath?
    var currentFilter: String?
    
    // MARK: - Obervable data source property
    
    var cardsDataSource = [Card]() {
        didSet {
            cardCollectionView.reloadData()
        }
    }

    // MARK: - Lifecycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        cardListInteractor.attachView(view: self)
        cardListInteractor.loadCards()
    }
    
    //MARK: - Segue
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        currentSelectedIndex = indexPath
        self.performSegue(withIdentifier: CardListContstans.segueDetail, sender: self)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == CardListContstans.segueDetail {
            if let destinationVC = segue.destination as? CardDetailViewController {
                destinationVC.cardDetailViewControllerDelegate = self
                destinationVC.currentFilter = self.currentFilter
                destinationVC.currentSelectedIndex = self.currentSelectedIndex
            }
        }
        if segue.identifier == CardListContstans.segueFilter {
            if let destinationVC = segue.destination as? CardFilterViewController {
                destinationVC.filterDelegate = self
            }
        }
    }
    
    //MARK: - Handlers
    
    @IBAction func tapFilter(_ sender: Any) {
        //
    }
    
}

// MARK: - UICollectionViewDataSource, UICollectionViewDelegate

extension CardListViewController {
    
    override func collectionView(_ collectionView: UICollectionView,
                                 numberOfItemsInSection section: Int) -> Int {
        return cardsDataSource.count
    }
    
    override func collectionView(_ collectionView: UICollectionView,
                                 cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CardListContstans.reuseIdentifier, for: indexPath)
        return cell
    }
    
    override func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        if cardsDataSource.count == 0 {
            return
        }
        let cardCell = cell as! CardCollectionViewCell
        cardCell.setCard(cardsDataSource[indexPath.row])
    }
    
}

// MARK: - CardDetailViewControllerDelegate

extension CardListViewController: CardDetailViewControllerDelegate {
    
    func onClose() {
        collectionView?.reloadData()
    }
    
}

// MARK: - CardFilterViewControllerDelegate

extension CardListViewController: CardFilterViewControllerDelegate {
    
    func onPickFilterType(_ filterName: String) {
        currentFilter = filterName
        self.cardListInteractor.filterCards(filterName)
    }
    
}

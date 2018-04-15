//
//  ViewController.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/10/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import UIKit
import MBProgressHUD
import UIScrollView_InfiniteScroll

struct CardListContstans {
    static let reuseIdentifier = "CardCell"
    static let pageSize = 50
    static let sectionCount = 1
    static let segueDetail = "CardDetailSegue"
}

class CardListViewController: UICollectionViewController {
    
    @IBOutlet var cardCollectionView: UICollectionView!
    
    var activityIndicator: MBProgressHUD?
    
    let cardListInteractor = CardListInteractor()
    var currentSelectedIndex: IndexPath?
    
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
                destinationVC.currentSelectedIndex = self.currentSelectedIndex
            }
        }
    }
}

// MARK: - UICollectionViewDataSource, UICollectionViewDelegate

extension CardListViewController {
    
    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return CardListContstans.sectionCount
    }
    
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

//
//  CardDetailViewController.swift
//  HearthstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/15/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation
import UIKit
import ScalingCarousel
import MBProgressHUD

struct CardDetailContstans {
    static let reuseIdentifier = "CardDetailCell"
    static let sectionCount = 1
}

protocol CardDetailViewControllerDelegate: NSObjectProtocol {
    func onClose()
}

class CardDetailViewController: UIViewController {
    
    @IBOutlet var carouselView: ScalingCarouselView!
    
    weak var cardDetailViewControllerDelegate: CardDetailViewControllerDelegate?
    
    let cardListInteractor = CardListInteractor()
    var activityIndicator: MBProgressHUD?
    var currentSelectedIndex: IndexPath?
    var currentFilter: String?
    
    // MARK: - KVO data source property
    
    var cardsDataSource = [Card]() {
        didSet {
            carouselView.reloadData()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        cardListInteractor.attachView(view: self)
        if let f = currentFilter {
            cardListInteractor.filterCards(f)
        } else {
            cardListInteractor.loadCards()
        }        
    }
    
    @IBAction func closeButtonTapped(_ sender: Any) {
        cardDetailViewControllerDelegate?.onClose()
        dismiss(animated: true, completion: nil)
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        if let index = currentSelectedIndex {
            carouselView.scrollToItem(at: index, at: .centeredHorizontally, animated: false)
        }
    }
    
}

// MARK: - UICollectionViewDataSource, UICollectionViewDelegate

extension CardDetailViewController: UICollectionViewDataSource {
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return CardDetailContstans.sectionCount
    }
    
    func collectionView(_ collectionView: UICollectionView,
                                 numberOfItemsInSection section: Int) -> Int {
        return cardsDataSource.count
    }
    
    func collectionView(_ collectionView: UICollectionView,
                                 cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CardDetailContstans.reuseIdentifier, for: indexPath) as! CardDetailCollectionViewCell
        cell.cardDetailViewDelegate = self
        cell.setCard(cardsDataSource[indexPath.row])
        return cell
    }

}

extension CardDetailViewController: CardDetailViewCellDelegate {
    
    func markFavourite(_ card: Card) {
        cardListInteractor.markFavourite(card)
    }
}


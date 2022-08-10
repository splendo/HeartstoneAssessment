//
//  CardsCollectionViewController.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 29/7/22.
//

import UIKit

class CardsCollectionViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {
    
    var cards = [CardViewModel]()
    var service: CardsDataService?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        collectionView.register(CardGridViewCell.self, forCellWithReuseIdentifier: "CardCell")
        setupBar()
        refreshData()
    }
    
    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        cards.count
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 150, height: 250)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 10, left: 10, bottom: 10, right: 10)
    }

    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "CardCell", for: indexPath) as? CardGridViewCell else {
            return UICollectionViewCell()
        }
        if cards.isEmpty {
            return cell
        }
        cell.cardViewModel = cards[indexPath.row]
        
        return cell
    }
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let card = cards[indexPath.row]
        card.select()
    }
    
    private func setupBar() {
        let hsiaoFavButton = UIBarButtonItem(image: UIImage(systemName: "list.star"), style: .plain, target: self, action: nil)
        addButtons(right: [hsiaoFavButton])
    }
    
    private func refreshData() {
        
        DispatchQueue.global().async { [weak self] in
            self?.service?.convert(from: "cards") { items in
                self?.cards = self?.service?.handleParsed(items) ?? []
                DispatchQueue.main.async {
                    if self?.cards.count == 0 {
                        if let viewBounds = self?.view.bounds,
                           let watermarkImage = UIImage(named: "oops") {
                            self?.collectionView.backgroundView = WatermarkView(frame: viewBounds, with: "Oops no cards found!", image: watermarkImage)
                        }
                    }
                    self?.collectionView.reloadData()
                }
            }
        }
    }
    
}

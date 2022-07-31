//
//  CardsCollectionViewController.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 29/7/22.
//

import UIKit

class CardsCollectionViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        collectionView.register(CardGridViewCell.self, forCellWithReuseIdentifier: "CardCell")
    }
    
    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        1
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
        if let imageUrl = URL(
            string: "https://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png") {
            cell.cardImage.load(from: imageUrl, mode: .scaleAspectFit)
        }
        
        return cell
    }
    
}

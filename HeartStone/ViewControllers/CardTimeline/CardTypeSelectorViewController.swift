//
//  CardTypeSelectorViewController.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 27..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import UIKit

class CardTypeSelectorViewController: UIViewController
{
    //MARK: Variables
    
    var allCardTypesList                    = CardType.allTypes
    var selectedHeartStoneCardType          : CardType?
    fileprivate let heartStoneCardCellId    = "HeartStoneCardCollectionViewCell"

    //MARK: Enums
    
    //MARK: IBOutlets
    
    @IBOutlet weak var collectionView: UICollectionView!
    
    //MARK: IBActions
    
    //MARK: Life-cycle
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        setupView()
        setupDelegation()
    }
    
    //MARK: Functions
    
    func setupView()
    {
        title                               = "Select Card Type"
    }
    
    func setupDelegation()
    {
        collectionView.delegate     = self
        collectionView.dataSource   = self
    }
}

//MARK: Extensions

//MARK: UICollectionViewDelegate

extension CardTypeSelectorViewController: UICollectionViewDelegate
{
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath)
    {
        selectedHeartStoneCardType = allCardTypesList[indexPath.row]
        
        let storyboard = UIStoryboard(name: AppScreen.CardTimeline.storyboardId, bundle: nil)
        
        if let vc = storyboard.instantiateViewController(withIdentifier: AppScreen.CardTimeline.viewControllerId) as? CardTimelineViewController
        {
            vc.filteredForCardType = selectedHeartStoneCardType
            navigationController?.pushViewController(vc, animated: true)
        }
    }
}

//MARK: UICollectionViewDataSource

extension CardTypeSelectorViewController: UICollectionViewDataSource
{
    func numberOfSections(in collectionView: UICollectionView) -> Int
    {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int
    {
        return allCardTypesList.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell
    {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: heartStoneCardCellId, for: indexPath) as! HeartStoneCardCollectionViewCell
        
        let currentHeartStoneCardType = allCardTypesList[indexPath.row]
        
        cell.setupCell(forHeartStoneCardType: currentHeartStoneCardType)
        
        return cell
    }
}


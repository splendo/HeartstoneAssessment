//
//  CardTimelineViewController.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 20..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import UIKit

class CardTimelineViewController: UIViewController
{
    //MARK: Variables
    
    var selectedHeartStoneCard                  : HeartStoneCard?
    var heartStoneCardsList                     : [HeartStoneCard] = []
    
    var filteredForCardType                     : CardType?
    fileprivate var filteredForMechanic         : CardMechanic?
    
    fileprivate let heartStoneCardCellId        = "HeartStoneCardCollectionViewCell"
    
    fileprivate var filteredHeartStoneCardsList : [HeartStoneCard] {
    
        guard let filteredForMechanic = filteredForMechanic else
        {
            return heartStoneCardsList
        }
    
        return heartStoneCardsList.filter{
    
            return $0.mechanics.contains(filteredForMechanic)
        }
    }
    
    //MARK: Enums
    
    enum CardTimelineSections : Int
    {
        case cards
        
        static var count: Int
        {
            var current = 0
            while let _ = self.init(rawValue: current) { current += 1 }
            return current
        }
    }
    
    //MARK: IBOutlets
    
    @IBOutlet weak var collectionView: UICollectionView!
    
    //MARK: IBActions
    
    @objc func filterMechanicsClicked()
    {
        let storyboard = UIStoryboard(name: AppScreen.CardFilter.storyboardId, bundle: nil)
        
        if let vc = storyboard.instantiateViewController(withIdentifier: AppScreen.CardFilter.viewControllerId) as? CardFilterViewController
        {
            vc.delegate         = self
            vc.selectedMechanic = filteredForMechanic
            present(vc, animated: true, completion: nil)
        }
    }
    
    //MARK: Life-cycle
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        setupView()
        setupDelegation()
        fetchAllHeartStoneCards(forType: filteredForCardType)
    }
    
    //MARK: Functions
    
    func setupView()
    {
        title       = "All Cards"
        
        if let filteredForCardType = filteredForCardType
        {
            title   = filteredForCardType.rawValue
        }
        
        navigationItem.rightBarButtonItem   = UIBarButtonItem(title: "Filter", style: .done, target: self, action: #selector(filterMechanicsClicked))
    }
    
    func setupDelegation()
    {
        collectionView.delegate     = self
        collectionView.dataSource   = self
    }
    
    func fetchAllHeartStoneCards(forType filterForType: CardType?)
    {
        DispatchQueue.global(qos: .background).async {
            APIManager.fetchAllHeartStoneCards(forType: filterForType) { [weak self] success, responseHeartStoneCardsList in
                
                guard let strongSelf = self,
                    let responseHeartStoneCardsList = responseHeartStoneCardsList, success else
                {
                    return
                }
                
                strongSelf.heartStoneCardsList = responseHeartStoneCardsList
                
                DispatchQueue.main.async {
                    strongSelf.collectionView.reloadSections(IndexSet(CardTimelineSections.cards.rawValue...CardTimelineSections.cards.rawValue))
                }
            }
        }
    }
}

//MARK: Extensions

//MARK: CardFilterViewControllerDelegate

extension CardTimelineViewController: CardFilterViewControllerDelegate
{
    func cardFilterSet(mechanicToFilter mechanicSelected: CardMechanic)
    {
        filteredForMechanic = mechanicSelected
        collectionView.reloadSections(IndexSet(CardTimelineSections.cards.rawValue...CardTimelineSections.cards.rawValue))
    }
    
    func cardFilterCleared()
    {
        filteredForMechanic = nil
        collectionView.reloadSections(IndexSet(CardTimelineSections.cards.rawValue...CardTimelineSections.cards.rawValue))
    }
}

//MARK: UICollectionViewDelegate

extension CardTimelineViewController: UICollectionViewDelegate
{
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath)
    {
        selectedHeartStoneCard = filteredHeartStoneCardsList[indexPath.row]
        
        let storyboard = UIStoryboard(name: AppScreen.CardDetail.storyboardId, bundle: nil)
        
        if let vc = storyboard.instantiateViewController(withIdentifier: AppScreen.CardDetail.viewControllerId) as? CardDetailViewController
        {
            vc.selectedHeartStoneCard = selectedHeartStoneCard
            navigationController?.pushViewController(vc, animated: true)
        }
    }
}

//MARK: UICollectionViewDataSource

extension CardTimelineViewController: UICollectionViewDataSource
{
    func numberOfSections(in collectionView: UICollectionView) -> Int
    {
        return CardTimelineSections.count
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int
    {
        return filteredHeartStoneCardsList.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell
    {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: heartStoneCardCellId, for: indexPath) as! HeartStoneCardCollectionViewCell
        
        let currentHeartStoneCard = filteredHeartStoneCardsList[indexPath.row]
        
        cell.setupCell(forHeartStoneCard: currentHeartStoneCard)
        
        return cell
    }
}

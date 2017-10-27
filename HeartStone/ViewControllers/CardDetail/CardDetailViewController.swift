//
//  CardDetailViewController.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 26..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import UIKit

class CardDetailViewController: UIViewController
{
    //MARK: Variables
    
    var selectedHeartStoneCard              : HeartStoneCard?
    
    fileprivate let headerImageCellId       = "CardDetailHeaderTableViewCell"
    fileprivate var headerImageCellHeight   : CGFloat = 250
    
    fileprivate let cardInfoCellId          = "CardDetailInfoTableViewCell"
    fileprivate let cardInfoCellHeight      : CGFloat = 100
    
    //MARK: Enums
    
    enum CardDetailSections : Int
    {
        case headerImages
        case cardShortInfo
        
        static var count: Int
        {
            var current = 0
            while let _ = self.init(rawValue: current) { current += 1 }
            return current
        }
    }
    
    //MARK: IBOutlets
    
    @IBOutlet weak var tableView: UITableView!
    
    //MARK: IBActions
    
    //MARK: Life-cycle
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        setupView()
        setupDelegation()
    }
    
    override func viewDidLayoutSubviews()
    {
        super.viewDidLayoutSubviews()
        
        headerImageCellHeight = tableView.frame.height - cardInfoCellHeight
    }
    
    //MARK: Functions
    
    func setupView()
    {
        if let selectedHeartStoneCard = selectedHeartStoneCard
        {
            title = selectedHeartStoneCard.name
        }
        
        tableView.isScrollEnabled   = false
        view.backgroundColor        = UIColor(red: 246/255, green: 246/255, blue: 246/255, alpha: 1)
    }
    
    func setupDelegation()
    {
        tableView.delegate      = self
        tableView.dataSource    = self
    }
}

extension CardDetailViewController: UITableViewDelegate
{
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath)
    {
        //
    }
}

extension CardDetailViewController: UITableViewDataSource
{
    func numberOfSections(in tableView: UITableView) -> Int
    {
        return CardDetailSections.count
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return 1
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat
    {
        guard let currentSection = CardDetailSections(rawValue: indexPath.section) else
        {
            return 0
        }
        
        switch currentSection
        {
            case .headerImages:
                return headerImageCellHeight
            
            case .cardShortInfo:
                return cardInfoCellHeight
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell
    {
        guard let currentSection = CardDetailSections(rawValue: indexPath.section) else
        {
            return UITableViewCell()
        }
        
        switch currentSection
        {
            case .headerImages:
                
                var cell = tableView.dequeueReusableCell(withIdentifier: headerImageCellId, for: indexPath) as? CardDetailHeaderTableViewCell
                
                if cell == nil
                {
                    cell = CardDetailHeaderTableViewCell(style: .default, reuseIdentifier: headerImageCellId)
                }
                
                if let strongSelectedHeartStoneCard = selectedHeartStoneCard
                {
                    cell!.setupCell(forHeartStoneCard: strongSelectedHeartStoneCard)
                }
            
                return cell!
            
            case .cardShortInfo:
                
                var cell = tableView.dequeueReusableCell(withIdentifier: cardInfoCellId, for: indexPath) as? CardDetailInfoTableViewCell
            
                if cell == nil
                {
                    cell = CardDetailInfoTableViewCell(style: .default, reuseIdentifier: cardInfoCellId)
                }
                
                if let strongSelectedHeartStoneCard = selectedHeartStoneCard
                {
                    cell!.setupCell(forHeartStoneCard: strongSelectedHeartStoneCard)
                }
                
                return cell!
        }
    }
}

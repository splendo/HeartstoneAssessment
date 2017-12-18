//
//  CoolCardsDetailViewController.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import UIKit
import FontAwesome_swift

class CoolCardsDetailViewController: UIViewController {
    @IBOutlet weak var cardImageView: UIImageView!
    @IBOutlet weak var cardTitleLabel: UILabel!
    @IBOutlet weak var cardDescriptionLabel: UILabel!
    
    public var cardId: String? = nil
    fileprivate let viewModel: CoolCardsDetailViewModel = CoolCardsDetailViewModel()
    fileprivate var favoriteButtonItem: UIBarButtonItem?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setupUI()
        self.setupBindings()
        if let cardId = self.cardId {
            self.viewModel.load(cardId: cardId)
        }
    }
    
    fileprivate func setupUI() {
        self.view.backgroundColor = UIColor.white
        if let cardId = self.cardId {
            let titleFormat = NSLocalizedString("Card %@", comment:"Format for detail view controller title")
            self.title =  String(format: titleFormat, cardId)
        }
        self.cardDescriptionLabel.numberOfLines = 0
        self.setupFavoriteButton()
    }
    
    func setupFavoriteButton() {
        if let cardId = self.cardId {
            let isFavorite = FavoritesProvider.getFavorite(cardId: cardId)
            let icon = isFavorite ? String.fontAwesomeIcon(name: .heart) : String.fontAwesomeIcon(name: .heartO)
            let favoriteButtonItem = UIBarButtonItem(title: icon, style: UIBarButtonItemStyle.plain, target: self, action: #selector(CoolCardsDetailViewController.toggleFavorite))
            let attributes = [NSAttributedStringKey.font: UIFont.fontAwesome(ofSize: 25)] as [NSAttributedStringKey: Any]
            favoriteButtonItem.setTitleTextAttributes(attributes, for: .normal)
            favoriteButtonItem.setTitleTextAttributes(attributes, for: .highlighted)
            
            self.favoriteButtonItem = favoriteButtonItem
            self.navigationItem.rightBarButtonItem = favoriteButtonItem
        }
    }
    
    @objc fileprivate func toggleFavorite() {
        if let cardId = self.cardId {
            let isFavorite = FavoritesProvider.getFavorite(cardId: cardId)
            FavoritesProvider.setFavorite(cardId: cardId, favorite: !isFavorite)
            self.setupFavoriteButton()
        }
    }
    
    fileprivate func setupBindings() {
        self.viewModel.delegate = self
    }

    fileprivate func configureViewWithCard(card: Card) {
        self.cardImageView.setImage(imageURL: URL(string:card.imgGold))
        self.cardTitleLabel.text = card.name
        self.cardDescriptionLabel.text = card.flavor
    }
}

extension CoolCardsDetailViewController : CoolCardsDetailViewModelDelegate {
    func didLoadModel() {
        if let card = self.viewModel.card {
            self.configureViewWithCard(card: card)
        }
    }
    
    func didFailModelLoadWithError() {
        
    }
}

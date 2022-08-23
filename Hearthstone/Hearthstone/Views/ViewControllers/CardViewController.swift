//
//  CardViewController.swift
//  Hearthstone
//
//  Created by Epsilon User on 22/8/22.
//

import UIKit

class CardViewController: UIViewController {
    
    // MARK: - Variables
    
    // Public
    public var card: Card?
    // Private
    private var cardViewModel: CardViewModel!
    private var isFavorite: Bool = true
    private var favoriteButton = UIBarButtonItem()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let cardView = CardView(frame: view.bounds, for: card)
        guard let card = card else {
            return
        }
        
        cardViewModel = CardViewModel(card: card)
        isFavorite = cardViewModel.isFavorite
        
        cardView.cardViewModel = cardViewModel
        view.addSubview(cardView)
        
        setupBar()
    }
    
    // MARK: - Setup Functions
    private func setupBar() {
        favoriteButton = UIBarButtonItem(image: UIBarButtonItem.set(for: isFavorite, toggledName: "heart.fill", nonToggledName: "heart"), style: .plain, target: self, action: #selector(addToFavorites))
        favoriteButton.tintColor = .red
        addButtons(right: [favoriteButton])
    }
    
    // MARK: - Update Functions
    
    
    // MARK: - Obj-C Functions
    @objc func addToFavorites() {
        isFavorite = !isFavorite
        favoriteButton.image = UIBarButtonItem.set(for: isFavorite, toggledName: "heart.fill", nonToggledName: "heart")
    }
}

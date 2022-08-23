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
    public var favoriteService: FavoritesService?
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
        favoriteService?.exists(with: card?.cardId ?? "") { [weak self] exists in
            self?.isFavorite = exists
            DispatchQueue.main.async {
                self?.favoriteButton = UIBarButtonItem(image: UIBarButtonItem.set(for: self?.isFavorite ?? false, toggledName: "heart.fill", nonToggledName: "heart"), style: .plain, target: self, action: #selector(self?.toggleFavorite))
                self?.favoriteButton.tintColor = .red
                self?.addButtons(right: [self?.favoriteButton ?? UIBarButtonItem()])
            }
        }
    }
    
    // MARK: - Update Functions
    private func updateFavorite(from success: Bool) {
        isFavorite = success
        favoriteButton.image = UIBarButtonItem.set(for: isFavorite, toggledName: "heart.fill", nonToggledName: "heart")
    }
    
    
    // MARK: - Obj-C Functions
    @objc func toggleFavorite() {
        if !isFavorite {
            favoriteService?.save(card?.cardId ?? "") { [weak self] success in
                DispatchQueue.main.async {
                    self?.showInfoAlert(with: success ? "Card added to Favorites" : "Unable to add card to Favorites")
                    self?.updateFavorite(from: success)
                }
            }
        } else {
            favoriteService?.delete(cardID: card?.cardId ?? "") { [weak self] success in
                DispatchQueue.main.async {
                    self?.showInfoAlert(with: success ? "Card deleted from Favorites" : "Unable to delete card to Favorites")
                    self?.updateFavorite(from: !success)
                }
                
            }
        }
    }
}

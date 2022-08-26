//
//  CardViewController.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 22/8/22.
//

import UIKit

class CardViewController: UIViewController {
    
    // MARK: - Variables
    
    // Public
    public var card: Card?
    public var favoriteService: FavoritesService?
    public var isFavorite: Bool = true
    public var favoriteButton = UIBarButtonItem()
    // Private
    private var cardViewModel: CardViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let card = card else {
            return
        }
        
        initDetailViewModel(card)
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
    
    // MARK: - Obj-C Functions
    @objc func toggleFavorite() {
        update(for: isFavorite)
    }
}

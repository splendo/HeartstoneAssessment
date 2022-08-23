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
    private var isFavorite: Bool = false
    private var favoriteButton = UIBarButtonItem()

    override func viewDidLoad() {
        super.viewDidLoad()

        setupBar()
        
        let cardView = CardView(frame: view.bounds, for: card)
        guard let card = card else {
            return
        }
        cardView.cardViewModel = CardViewModel(card: card)
        view.addSubview(cardView)
    }
    
    private func setupBar() {
        favoriteButton = UIBarButtonItem(image: UIImage(systemName: "heart"), style: .plain, target: self, action: nil)
        favoriteButton.tintColor = .red
        addButtons(right: [favoriteButton])
    }
}

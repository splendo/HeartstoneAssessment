//
//  CardViewController.swift
//  Hearthstone
//
//  Created by Epsilon User on 22/8/22.
//

import UIKit

class CardViewController: UIViewController {
    
    var card: Card?

    override func viewDidLoad() {
        super.viewDidLoad()

        let cardView = CardView(frame: view.bounds, for: card)
        guard let card = card else {
            return
        }
        cardView.cardViewModel = CardViewModel(card: card)
        view.addSubview(cardView)
    }
    
}

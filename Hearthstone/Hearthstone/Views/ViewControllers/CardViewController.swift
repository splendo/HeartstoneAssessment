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

        title = card?.name
    }
    
}

//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import UIKit

final class DetailsViewController: UIViewController {
    let card: Card

    init(withInfoFrom card: Card) {
        self.card = card
        
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupView()
    }
    
    private func setupView() {
        view.backgroundColor = Style.screenBackgroundColor
        
        navigationItem.title = card.name
    }
}
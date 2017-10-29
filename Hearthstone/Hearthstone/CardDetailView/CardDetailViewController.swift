//
//  CardDetailViewController.swift
//  Hearthstone
//
//  Created by Dmitrii on 25/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import UIKit

class CardDetailViewController: UIViewController {

    // MARK: - Properties
    private let cardDetailCellID = "CardDetailID"

    private let dataModel = CardDetailViewModel()

    @IBOutlet var tableView: UITableView!
    @IBOutlet var mainImageView: UIImageView!

    // MARK: - Lyfecycle
    override func viewDidLoad() {
        super.viewDidLoad()
        title = dataModel.screenName()

        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 140
        
        dataModel.loadMainImage { [weak self] (image, url) in
            self?.mainImageView.image = image
        }
    }

    // MARK: - Public
    func setCard(card: Card) {
        dataModel.setCard(card: card)
    }


    // MARK: - Private


    // MARK: - Actions
}


extension CardDetailViewController: UITableViewDataSource {

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataModel.numberOfDetails()
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: cardDetailCellID) as! CardDetailCell
        cell.model = dataModel.detailModel(index: indexPath.row)
        return cell
    }
}

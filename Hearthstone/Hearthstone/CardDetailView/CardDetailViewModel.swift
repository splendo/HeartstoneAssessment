//
//  CardDetailViewModel.swift
//  Hearthstone
//
//  Created by Dmitrii on 25/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import UIKit

class CardDetailViewModel {

    // MARK: - Properties
    private var card: Card!
    private var details = [CardDetailModel]()

    // MARK: - Lyfecycle


    // MARK: - Public
    func screenName() -> String {
        return card.name
    }

    func setCard(card: Card) {
        self.card = card
        details.append(CardDetailModel(name: "Name", text: card.name))
        if let set = card.cardSet {
            details.append(CardDetailModel(name: "Card Set", text: set))
        }
        if let playerCalss = card.playerClass {
            details.append(CardDetailModel(name: "Player Class", text:playerCalss.rawValue))
        }
        if let type = card.type {
            details.append(CardDetailModel(name: "Type", text: type.rawValue))
        }
        if let rarity = card.rarity {
            details.append(CardDetailModel(name: "Rarity", text: rarity.rawValue))
        }
        if !card.mechanics.isEmpty {
            var mechanicsString = ""
            for mechanic in card.mechanics {
                mechanicsString += mechanic.name.rawValue
                if mechanic.name.rawValue != card.mechanics.last!.name.rawValue {
                    mechanicsString += "\n"
                }
            }
            details.append(CardDetailModel(name: "Mechanics", text: mechanicsString))
        }
        if let flavor = card.flavor {
            details.append(CardDetailModel(name: "Flavor", text: flavor))
        }
        if let how = card.howToGet {
            details.append(CardDetailModel(name: "How To Get", text: how))
        }
        if let attack = card.attack {
            details.append(CardDetailModel(name: "Attack", text: "\(attack)"))
        }
        if let health = card.health {
            details.append(CardDetailModel(name: "Health", text: "\(health)"))
        }
        if let artist = card.artist {
            details.append(CardDetailModel(name: "Artist", text: artist))
        }
    }

    func loadMainImage(completion: @escaping (_ image: UIImage?, _ url: String)->()) {
        ImageManager.sharedInstance.downloadImage(urlString: card.imgURL, completion: completion)
    }

    func numberOfDetails() -> Int {
        return details.count
    }

    func detailModel(index: Int) -> CardDetailModel? {
        guard index >= 0 && index <= details.count else {return nil}
        return details[index]
    }

    // MARK: - Private

}


struct CardDetailModel {
    let name: String
    let text: String
}

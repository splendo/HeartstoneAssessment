//
//  Card.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation
import RealmSwift

class Card: Object {
    @objc dynamic var cardId: String?
    @objc dynamic var name: String?
    @objc dynamic var playerClass: String?
    @objc dynamic var img: String?
    @objc dynamic var type: String?
    @objc dynamic var rarity: String?
    @objc dynamic var isFavorite: Bool = false
    @objc dynamic var isElite: Bool = false
    @objc dynamic var attack: Int = 0
    @objc dynamic var health: Int = 0
    var mechanics = [MechanicCategory]()
    var classes = [String]()
    
    override class func primaryKey() -> String? {
        return "cardId"
    }
}

class MechanicCategory: Object {
    
    convenience init(name: String?) {
        self.init()
        self.name = name
    }
    
    @objc dynamic var name: String? = nil
}




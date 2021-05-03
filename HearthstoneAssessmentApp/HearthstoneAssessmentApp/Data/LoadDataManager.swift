//
//  LoadDataManager.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation
import RealmSwift

struct DataConstants {
    static let FILE_URL = "https://github.com/splendo/HeartstoneAssessment/blob/apps/cards.json"
    static let FILE_NAME = "cards"
    static let ALL = "All"
}

class LoadDataManager {
    
    let realmDataManager: RealmDataManager = RealmDataManager()
    var dataProvider: DataObjectProviderProtocol
    
    init() {
        self.dataProvider = JsonDataProvider(fileName: DataConstants.FILE_NAME, urlString: DataConstants.FILE_URL)
    }
    
    // load card Data by speed priority of an access
    func loadCards(onSuccess: @escaping ([Card]) -> Void, onFail: @escaping (Error) -> Void)  {
        
        if !realmDataManager.realm.isEmpty {
            onSuccess(realmDataManager.loadCards())
            return
        }        
        
        // load first time when no Realm DB exists
        loadDataFromFile(onSuccess: { [weak self] (items) in
            self?.realmDataManager.saveCards(items)
            onSuccess(items)
        }) { (error) in
            onFail(error)
        }
    }
    
    private func saveLoadedData(_ itemsToSave: [Card], comletion: () -> Void) {
        self.realmDataManager.saveCards(itemsToSave)
        comletion()
    }
    
    private func loadDataFromFile(onSuccess: @escaping ([Card]) -> Void, onFail: @escaping (Error) -> Void) {
        dataProvider.loadCardData(onDataLoad: { [weak self] (object) in
            let cards = self?.populateDatabaseObjects(root: object)
            onSuccess(cards!)
        }) { (error) in
            onFail(error)
        }
    }
    
    // MARK: - Helpers
    
    // populate cards to Realm entities
    private func populateDatabaseObjects(root: RootCardsObject) -> [Card] {
        var cards = [Card]()
        if let basicCards = root.basic {
            cards.append(contentsOf: populateFromCodableObject(arrayBasic: basicCards))
        }
        if let classicCards = root.classic {
            cards.append(contentsOf: populateFromCodableObject(arrayBasic: classicCards))
        }
        return cards
    }
    
    private func populateFromCodableObject(arrayBasic:[BasicCard]) -> [Card] {
        var cards = [Card]()
        for classicCard in arrayBasic {
            if classicCard.img != nil {
                cards.append(Card.create(basicCard: classicCard))
            }
        }
        return cards
    }
    
    // populate filter object
    
    static func populateFilters() -> [FilterObject] {
        var filters = [FilterObject]()
        
        filters.append(FilterObject(name: "All types", types: [DataConstants.ALL]))
        
        var typeList = [String]()
        for valueCase in BasicType.cases() {
            typeList.append(valueCase.rawValue)
        }
        filters.append(FilterObject(name: "Type", types: typeList))
        
        var rarityList = [String]()
        for valueCase in BasicRarity.cases() {
            rarityList.append(valueCase.rawValue)
        }
        filters.append(FilterObject(name: "Rarity", types: rarityList))
        
        var playerClassList = [String]()
        for valueCase in BasicPlayerClass.cases() {
            playerClassList.append(valueCase.rawValue)
        }
        filters.append(FilterObject(name: "Player class", types: playerClassList))
        return filters
    }
    
}

class FilterObject {
    
    var filterName: String
    var types: [String]
    
    init(name:String, types:[String]) {
        self.filterName = name
        self.types = types
    }
}

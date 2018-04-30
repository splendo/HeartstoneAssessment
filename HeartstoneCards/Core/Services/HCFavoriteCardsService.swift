//
// Created by Roman Petryshen on 25/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation

public class HCFavoriteCardsService {

    typealias Item = String
    typealias ItemsCollection = Set<Item>

    var items = ItemsCollection()

    let storage: HCFavoritesStorageType

    init(storage: HCFavoritesStorageType) {
        self.storage = storage
        refresh(completion: nil)
    }

    func refresh(completion: (() -> ())?) {
        self.storage.load { [weak self] collection in
            self?.items = collection
            completion?()
        }
    }

    func contains(_ item: Item) -> Bool {
        return items.contains(item)
    }

    func add(_ item: Item) {
        items.insert(item)
        storage.add(item, resultantCollection: items)
    }

    func remove(_ item: Item) {
        items.remove(item)
        storage.remove(item, resultantCollection: items)
    }
}


protocol HCFavoritesStorageType {
    func load(completion: (HCFavoriteCardsService.ItemsCollection) -> ())
    func add(_ item: HCFavoriteCardsService.Item, resultantCollection: HCFavoriteCardsService.ItemsCollection)
    func remove(_ item: HCFavoriteCardsService.Item, resultantCollection: HCFavoriteCardsService.ItemsCollection)
}

class HCFavoritesUserDefaultsStorage: HCFavoritesStorageType {

    let storageKey: String
    let defaults: UserDefaults

    init(with defaults: UserDefaults = UserDefaults.standard, storageKey: String = "FavoriteCards") {
        self.defaults = defaults
        self.storageKey = storageKey
    }

    func load(completion: (HCFavoriteCardsService.ItemsCollection) -> ()) {
        if let collection = self.defaults.value(forKey: storageKey) as? [HCFavoriteCardsService.Item] {
            completion(Set(collection))
        } else {
            completion(HCFavoriteCardsService.ItemsCollection())
        }
    }

    func add(_ item: HCFavoriteCardsService.Item, resultantCollection: HCFavoriteCardsService.ItemsCollection) {
        saveCollection(resultantCollection)
    }

    func remove(_ item: HCFavoriteCardsService.Item, resultantCollection: HCFavoriteCardsService.ItemsCollection) {
        saveCollection(resultantCollection)
    }

    func saveCollection(_ collection: HCFavoriteCardsService.ItemsCollection) {
        defaults.set(Array(collection), forKey: storageKey)
        defaults.synchronize()
    }
    
    func deleteAll() {
        defaults.removeObject(forKey: storageKey)
        defaults.synchronize()
    }
}

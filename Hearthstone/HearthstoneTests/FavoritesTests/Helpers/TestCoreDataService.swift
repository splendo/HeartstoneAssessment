//
//  TestCoreDataService.swift
//  HearthstoneTests
//
//  Created by Stavros Tsikinas on 24/8/22.
//


import CoreData
@testable import Hearthstone

class TestCoreDataService: FavoritesService {
    
    init() {
        let inMemoryDescription = NSPersistentStoreDescription()
        // Create inMemoryStoreType for testing purposes, to avoid "messing" with real DB
        inMemoryDescription.type = NSInMemoryStoreType
        
        let container = NSPersistentContainer(name: "Hearthstone")
        
        container.persistentStoreDescriptions = [inMemoryDescription]
        
        container.loadPersistentStores { _, error in
            if let error = error {
                debugPrint("Couldn't load stores with error: \(error.localizedDescription)")
            }
        }
        
        super.init(with: container)
        
    }
    
    /// Helper function to add multiple cards to favorites, not necessary to project though
    func addCards(of count: Int, completion: @escaping(Bool) -> Void) {
        var cardsArray = [String]()
        for i in 0..<count {
            cardsArray.append("CardToFetch_\(i)")
        }
        
        context.perform { [weak self] in
            for card in cardsArray {
                let newFav = Favorite(context: self!.context)
                newFav.cardID = card
            }
            do {
                try self?.context.save()
                completion(true)
            } catch {
                debugPrint("Unable to store cards with ID: \(cardsArray)")
                completion(false)
            }
        }
        
    }
    
}

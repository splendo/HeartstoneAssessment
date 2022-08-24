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
    
    
    
}

//
//  FavoritesService.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 12/8/22.
//

import Foundation
import CoreData


protocol StoreCardProtocol {
    func save(_ card: Card)
    func update(_ card: Card)
    func delete(cardID: String)}

struct FavoritesService: StoreCardProtocol {
    
    private let container: NSPersistentContainer
    private let context: NSManagedObjectContext
    
    
    init(with persistentContainer: NSPersistentContainer) {
        container = persistentContainer
        context = container.newBackgroundContext()
        context.automaticallyMergesChangesFromParent = true
    }
    
    func save(_ card: Card) {
        context.perform {
            let newFav = Favorite(context: context)
            newFav.cardID = card.cardId
            do {
                try container.viewContext.save()
            } catch {
                debugPrint("Unable to store card with ID: \(card.cardId ?? "")")
            }
        }
        
        
    }
    
    func update(_ card: Card) {
        
    }
    
    func delete(cardID: String) {
        
    }
    
}

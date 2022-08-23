//
//  FavoritesService.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 12/8/22.
//

import Foundation
import CoreData


protocol StoreCardProtocol {
    func save(_ cardID: String, completion: @escaping(Bool) -> Void)
    func update(_ card: Card)
    func delete(cardID: String, completion: @escaping(Bool) -> Void)
}

protocol ReadCardProtocol {
    func exists(with id: String, completion: @escaping(Bool) -> Void)
}

struct FavoritesService: StoreCardProtocol, ReadCardProtocol {
    
    private let container: NSPersistentContainer
    private let context: NSManagedObjectContext
    
    
    init(with persistentContainer: NSPersistentContainer) {
        // Create context that performs on the background, in order to avoid UI lags
        container = persistentContainer
        context = container.newBackgroundContext()
    }
    
    func save(_ cardID: String, completion: @escaping(Bool) -> Void) {
        context.perform {
            exists(with: cardID) { inDB in
                if !inDB {
                    let newFav = Favorite(context: context)
                    newFav.cardID = cardID
                    do {
                        try context.save()
                        completion(true)
                    } catch {
                        debugPrint("Unable to store card with ID: \(cardID)")
                        completion(false)
                    }
                } else {
                    debugPrint("\(cardID) already exists in DB")
                    completion(false)
                }
            }
        }
    }
    
    func update(_ card: Card) {
        
    }
    
    func delete(cardID: String, completion: @escaping(Bool) -> Void) {
        context.perform {
            exists(with: cardID) { inDB in
                if inDB {
                    // Use different approach from save, since we are not have an existing NSManagedObject (Favorite)
                    let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Favorite")
                    fetchRequest.predicate = NSPredicate(format: "cardID = %@", cardID)
                    let request = NSBatchDeleteRequest(fetchRequest: fetchRequest)
                    
                    do {
                        try context.execute(request)
                        try context.save()
                        completion(true)
                    } catch {
                        debugPrint("Unable to delete card with ID: \(cardID)")
                        completion(false)
                    }
                } else {
                    debugPrint("\(cardID) doesn't exists in DB")
                    completion(false)
                }
            }
        }
    }
    
    func exists(with id: String, completion: @escaping(Bool) -> Void) {
        context.perform {
            let request = Favorite.fetchRequest()
            request.predicate = NSPredicate(format: "cardID = %@", id)
            do {
                let count = try context.count(for: request)
                completion(count > 0)
            } catch {
                debugPrint("Unable to read if exists card: \(id)")
                completion(false)
            }
        }
        
    }
    
    
}

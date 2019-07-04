//
//  Storage.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import CoreData

public struct Storage {

    private let container: NSPersistentContainer
    private let saveQueue: OperationQueue

    public init(name: String = "Model") {
        container = NSPersistentContainer(name: name)
        container.loadPersistentStores { (storeDescription, error) in
            debugPrint("CoreData: Inited \(storeDescription)")
            if let error = error {
                debugPrint("CoreData: Unresolved error \(error)")
                return
            }
        }
        container.viewContext.automaticallyMergesChangesFromParent = true
        privateContext = container.newBackgroundContext()

        saveQueue = OperationQueue()
        saveQueue.maxConcurrentOperationCount = 1
        saveQueue.qualityOfService = .utility
    }

    public var mainContext: NSManagedObjectContext {
        return container.viewContext
    }

    public var privateContext: NSManagedObjectContext

    public func newPrivateContext() -> NSManagedObjectContext {
        return container.newBackgroundContext()
    }

    public func performAndSave(context: NSManagedObjectContext,
                               block: @escaping (NSManagedObjectContext) -> Void,
                               completion: NSManagedObjectContext.SaveCompletion? = nil) {

        saveQueue.addOperation {
            context.performAndSave(block, completion: completion)
        }
    }
}

extension NSManagedObjectContext {

    public typealias SaveCompletion = (SaveState) -> Void

    public enum SaveState {
        case saved, rolledBack, hasNoChanges
    }

    public func performWithoutSave(block: @escaping (NSManagedObjectContext) -> Void) {
        self.perform { block(self) }
    }

    public func performAndSave(block: @escaping (NSManagedObjectContext) -> Void) {
        performAndSave(block, completion: nil)
    }

    public func performAndSave(_ block: @escaping (NSManagedObjectContext) -> Void,
                               completion: SaveCompletion? = nil) {
        self.perform {
            block(self)

            guard self.hasChanges else {
                completion?(.hasNoChanges)
                return
            }

            do {
                try self.save()
                completion?(.saved)
            } catch {
                self.rollback()
                completion?(.rolledBack)
            }
        }
    }
}

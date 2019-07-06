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
    public var privateContext: NSManagedObjectContext
    
    public var mainContext: NSManagedObjectContext {
        return container.viewContext
    }

    public init(container: NSPersistentContainer) {
        self.container = container
        self.privateContext = container.newBackgroundContext()

        self.saveQueue = OperationQueue()
        self.saveQueue.maxConcurrentOperationCount = 1
        self.saveQueue.qualityOfService = .utility
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

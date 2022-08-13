//
//  Favorite+CoreDataProperties.swift
//  Hearthstone
//
//  Created by Epsilon User on 12/8/22.
//
//

import Foundation
import CoreData


extension Favorite {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<Favorite> {
        return NSFetchRequest<Favorite>(entityName: "Favorite")
    }

    @NSManaged public var cardID: String?

}

extension Favorite : Identifiable {

}

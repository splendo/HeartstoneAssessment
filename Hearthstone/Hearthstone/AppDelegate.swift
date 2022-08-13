//
//  AppDelegate.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 25/7/22.
//

import UIKit
import CoreData

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        return true
    }
    
    // MARK: - Core Data Stuff
    
    lazy var persistentContainer: NSPersistentContainer = {
        let container = NSPersistentContainer(name: "Hearthstone")
        container.loadPersistentStores { description, error in
            
            if let error = error {
                debugPrint("Unresolved error: \(error)")
            }
            
        }
        return container

    }()

}


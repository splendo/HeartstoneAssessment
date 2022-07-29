//
//  SceneDelegate.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 25/7/22.
//

import UIKit

class SceneDelegate: UIResponder, UIWindowSceneDelegate {

    var window: UIWindow?


    // Init scene programmatically to "get rid" of Storyboards
    func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
        
        guard let windowScene = (scene as? UIWindowScene) else { return }
        
        window = UIWindow(frame: UIScreen.main.bounds)
        
        let homeView = HomeTabViewController()
        window?.rootViewController = homeView
        window?.makeKeyAndVisible()
        window?.windowScene = windowScene
        
    }

//    func sceneDidEnterBackground(_ scene: UIScene) {
//
//    }
}


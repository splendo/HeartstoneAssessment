import UIKit

internal final class SceneDelegate: UIResponder, UIWindowSceneDelegate {
    internal var window: UIWindow?
    
    private let appDependencies = AppDependencies()
    private let router: CardRouter
    
    internal override init() {
         router = CardRouter(appDependencies: self.appDependencies)
        
        super.init()
    }

    internal func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
        guard let windowScene = (scene as? UIWindowScene) else {
            return
        }
        
        let window = UIWindow(windowScene: windowScene)
        
        window.rootViewController = router.rootViewController
        window.makeKeyAndVisible()
        
        self.window = window
    }
}

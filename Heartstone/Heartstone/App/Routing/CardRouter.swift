import UIKit

internal final class CardRouter {
    internal let rootViewController: UINavigationController
    
    internal let appDependencies: AppDependencies
    
    internal init(appDependencies: AppDependencies) {
        self.appDependencies = appDependencies
        let navigationController = UINavigationController()
        
        self.rootViewController = navigationController
        
        let cardOverviewController = CardOverviewViewController(router: self, appDependencies: appDependencies)
        navigationController.viewControllers = [cardOverviewController]
    }
    
    internal func routeToCardDetails(_ card: HeartStoneCard) {
        let detailViewController = CardDetailsViewController(appDependencies: appDependencies, card: card)
        
        rootViewController.pushViewController(detailViewController, animated: true)
    }
    
    internal func routeToFilters(availableFilters: [HeartStoneFilter],
                                 activeFilters: HeartStoneActiveFilters,
                                 onChange: @escaping (HeartStoneActiveFilters) -> Void) {
        let viewController = FilterViewController(availableFilters: availableFilters, activeFilters: activeFilters)
        
        viewController.completion = { activeFilters in
            viewController.dismiss(animated: true)
            
            if let activeFilters = activeFilters {
                onChange(activeFilters)
            }
        }
        
        let navigationController = UINavigationController(rootViewController: viewController)
        
        rootViewController.present(navigationController, animated: true, completion: nil)
    }
    
    internal func routeToSort(from barButton: UIBarButtonItem, onResult: @escaping (Bool) -> Void) {
        let actionSheet = UIAlertController(title: "Sort order", message: nil, preferredStyle: .actionSheet)
        
        let sortAscendingAction = UIAlertAction(title: "Names ascending", style: .default) { _ in
            onResult(true)
        }
        
        let sortDescendingAction = UIAlertAction(title: "Names descending", style: .default) { _ in
            onResult(false)
        }
        
        actionSheet.addAction(sortAscendingAction)
        actionSheet.addAction(sortDescendingAction)
        actionSheet.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        
        actionSheet.popoverPresentationController?.barButtonItem = barButton
        
        rootViewController.present(actionSheet, animated: true, completion: nil)
    }
}

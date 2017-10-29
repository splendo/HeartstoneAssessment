//
//  ViewControllerFactory.swift
//  Hearthstone
//
//  Created by Dmitrii on 25/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import UIKit

class ViewControllerFactory {

    class func mainStoryboard() -> UIStoryboard {
        return UIStoryboard(name: "Main", bundle: nil)
    }

    class func collectionViewController() -> CardsCollectionViewController {
        return mainStoryboard().instantiateViewController(withIdentifier: "CardsCollectionViewController") as! CardsCollectionViewController
    }

    class func detailViewController() -> CardDetailViewController {
        return mainStoryboard().instantiateViewController(withIdentifier: "CardDetailViewController") as! CardDetailViewController
    }

    class func collectionFilterViewController() -> CollectionFilterViewController {
        return mainStoryboard().instantiateViewController(withIdentifier: "CollectionFilterViewController") as! CollectionFilterViewController
    }
}

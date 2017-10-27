//
//  AppURLRoutes.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 20..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import Foundation
import Alamofire

struct AppURLRoutes
{
//    static let baseAPIURL   = "https://omgvamp-hearthstone-v1.p.mashape.com"
//    static let APIKey       = "RyTED2nIQTmshUyDtZPGNsQ57yPyp1KzEeCjsnzCiiymgBgdbs"
    
    struct allHeartStoneCards
    {
        static let address  : String        = "https://raw.githubusercontent.com/splendo/HeartstoneAssessment/master/cards.json"
        static let method   : HTTPMethod    = .get
    }
}

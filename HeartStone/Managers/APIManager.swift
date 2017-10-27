//
//  APIManager.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 20..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import Foundation
import Alamofire
import SwiftyJSON

class APIManager
{
    //MARK: Variables
    
    //MARK: Functions
    
    //Main API request functions
    //Takes: URL string, REST method, and a dictionary of parameters
    //Returns: success Bool, response JSON object
    class func requestAuthenticated(withURL URL: String, method: HTTPMethod, parameters: [String : Any]?, completion: @escaping (_ success: Bool, _ responseJson: JSON?) -> Void) {

        let headers : HTTPHeaders = [:]
//            "X-Mashape-Key" : AppURLRoutes.APIKey
//        ]
        
        //Show default activity spinner in status bar
        DispatchQueue.main.async {
            UIApplication.shared.isNetworkActivityIndicatorVisible = true
        }
        
        //Test encoding to URL encoding for get requests
        let encoding : ParameterEncoding = method == .get ? URLEncoding.default : JSONEncoding.default
        
        //Make request to provided URL with provided parameters
        Alamofire.request(URL, method: method, parameters: parameters, encoding: encoding, headers: headers).responseJSON { response in
            
            //Hide default activity spinner in status bar
            DispatchQueue.main.async {
                UIApplication.shared.isNetworkActivityIndicatorVisible = false
            }
            
            // getting response in JSON
            guard let responseResultValue = response.result.value else
            {
                completion(false, nil)
                return
            }
            
            // handle response
            switch response.result
            {
                case .success:
                    
                    if let strongRequest = response.request,
                        let strongURL = strongRequest.url
                    {
                        print("Successful fetch, successful response on route: \(strongURL)")
                    }
                    
                    completion(true, JSON(responseResultValue))
                
                case .failure:
                    
                    if let strongRequest = response.request,
                        let strongURL = strongRequest.url
                    {
                        print("Failed fetch on route: \(strongURL)")
                    }
                    
                    completion(false, JSON(responseResultValue))
            }
        }
    }
    
    class func fetchAllHeartStoneCards(forType filterForType: CardType? = nil, completion: ((_ success: Bool, _ responseHeartStoneCardsList: [HeartStoneCard]?) -> Void)?) {

        requestAuthenticated(withURL: AppURLRoutes.allHeartStoneCards.address, method: AppURLRoutes.allHeartStoneCards.method, parameters: nil) { success, responseJson in
            
            guard let responseJson = responseJson, success else
            {
                completion?(false, nil)
                return
            }
            
            CardMechanic.deleteAllMechanics()
            
            if let filterForType = filterForType
            {
                completion?(true, HeartStoneCardFactory.createHeartStoneCardsList(fromJSON: responseJson[filterForType.rawValue]))
            }
            else if let dictionaryObject = responseJson.dictionaryObject
            {
                for key in dictionaryObject.keys
                {
                    completion?(true, HeartStoneCardFactory.createHeartStoneCardsList(fromJSON: responseJson[key]))
                }
            }
            else
            {
                completion?(false, nil)
            }
        }
    }
}

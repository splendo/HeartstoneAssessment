//
//  CoolCardsAPI.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import Foundation
import Moya

class CoolCardsAPI {
    static let provider = MoyaProvider<CoolCardsAPIRequest>()
    
    static func getCards(completion: @escaping (([Card]?, Error?) -> Void)) {
        provider.request(.cards) { result in
            switch result {
            case let .success(response):
                do {
                    let results = try JSONDecoder().decode([Card].self, from: response.data)
                    completion(results, nil)
                } catch let exception {
                    print (exception)
                    completion(nil, exception)
                }
            case let .failure(error):
                print (error)
                completion(nil, error)
            }
        }
    }
    
    static func getCard(cardId: String, completion: @escaping ((Card?, Error?) -> Void)) {
        provider.request(.card(cardId: cardId)) { result in
            switch result {
            case let .success(response):
                do {
                    let results = try JSONDecoder().decode(Card.self, from: response.data)
                    completion(results, nil)
                } catch let exception {
                    print (exception)
                    completion(nil, exception)
                }
            case let .failure(error):
                print (error)
                completion(nil, error)
            }
        }
    }
}

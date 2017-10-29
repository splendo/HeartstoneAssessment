//
//  APIResponseParser.swift
//  Hearthstone
//
//  Created by Dmitrii on 29/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import Foundation

let apiResponseProcessingErrorDomain = "apiErrorDomain"
let apiResponseProcessingErrorCodeParsing = 0
let apiResponseProcessingErrorCodeFormat = 1

class APIResponseParser {

    static func processCardsResponse(responseData: Data?, error: Error?, completion: RequestCompletion) {
        guard responseData != nil else {
            completion(nil, error)
            return
        }

        do {
            let response = try JSONSerialization.jsonObject(
                with: responseData!,
                options: JSONSerialization.ReadingOptions.mutableContainers
            )
            guard let apiCardsDictionary = response as? [String : Any] else {
                let error = NSError(
                    domain: apiResponseProcessingErrorDomain,
                    code: apiResponseProcessingErrorCodeFormat,
                    userInfo: nil
                )
                completion(nil, error)
                return
            }
            var result = [Card]()
            for (_, value) in apiCardsDictionary {
                guard let cards = value as? [[String: Any]] else {continue}
                for cardDictionary in cards {
                    guard let newCardObject = Card(apiDictionary: cardDictionary) else {continue}
                    result.append(newCardObject)
                }
            }
            completion(result, nil)
        } catch {
            let error = NSError(
                domain: apiResponseProcessingErrorDomain,
                code: apiResponseProcessingErrorCodeParsing,
                userInfo: nil
            )
            completion(nil, error)
        }
    }
}


// Initialising from API Response

extension Card {
    convenience init?(apiDictionary: [String: Any]) {
        guard let cardIdDict = apiDictionary["cardId"] as? String else {return nil}
        guard let imgURLDict = apiDictionary["img"] as? String else {return nil}
        guard let nameDict = apiDictionary["name"] as? String else {return nil}
        self.init(cardId: cardIdDict, imgURL: imgURLDict, name: nameDict)
        health = apiDictionary["health"] as? Int
        cost = apiDictionary["cost"] as? Int
        attack = apiDictionary["attack"] as? Int
        cardSet = apiDictionary["cardSet"] as? String
        text = apiDictionary["text"] as? String
        flavor = apiDictionary["flavor"] as? String
        artist = apiDictionary["artist"] as? String
        howToGet = apiDictionary["howToGet"] as? String
        if let classDict = apiDictionary["playerClass"] as? String {
            playerClass = CardPlayerClass(rawValue: classDict)
        }
        if let typeDict = apiDictionary["type"] as? String {
            type = CardType(rawValue: typeDict)
        }
        if let rarityDict = apiDictionary["rarity"] as? String {
            rarity = CardRarity(rawValue: rarityDict)
        }
        if let mechanicsDictArray = apiDictionary["mechanics"] as? [[String: String]] {
            for mechanicDict in mechanicsDictArray {
                if let mechanic = CardMechanic(apiDictionary: mechanicDict) {
                    mechanics.append(mechanic)
                }
            }
        }
    }
}

extension CardMechanic {
    init?(apiDictionary: [String: String]) {
        guard let nameString = apiDictionary["name"] else {return nil}
        guard let nameEnum = CardMechanicName(rawValue: nameString) else {return nil}
        name = nameEnum
    }
}

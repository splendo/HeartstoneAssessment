//
//  CardsDataService.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 4/8/22.
//

import Foundation

protocol LocalDownloadProtocol {
    
    func getUrl(from name: String?, typed: String?) -> URL?
    func getJSONString(from url: URL) throws -> String?
    
}

protocol JSONConverterProtocol {
    
    func convert(from json: String, completion: @escaping([Card]) -> Void)
    
}

protocol ResultHadler {
    func handleParsed(_ cards: [Card]) -> [CardViewModel]
}

struct CardsDataService: LocalDownloadProtocol, JSONConverterProtocol, ResultHadler {
    
    let type: ServiceType
    
    let extensionType = "json"
    
    func convert(from json: String, completion: @escaping ([Card]) -> Void) {
        do {
            if let fileUrl = getUrl(from: json, typed: extensionType),
               let jsonString = try getJSONString(from: fileUrl),
               let jsonData = jsonString.data(using: .utf8) {
                let decoder = JSONDecoder()
                let cardsData = try decoder.decode(CardsResponse.self, from: jsonData)
                
                completion(cardsData.getAllCards())
            }
        } catch {
            print(error)
            completion([])
        }
    }
    
    func handleParsed(_ cards: [Card]) -> [CardViewModel] {
        var viewModels = [CardViewModel]()
        for card in cards {
            switch type {
            case .AllCards:
                viewModels.append(CardViewModel(card: card, select: {}))
                break
            default:
                break
            }
        }
        return viewModels
    }
    
    
    internal func getUrl(from name: String?, typed: String?) -> URL? {
        Bundle.main.url(forResource: name, withExtension: typed)
    }
    
    internal func getJSONString(from url: URL) throws -> String? {
        do {
            return try String(contentsOf: url, encoding: .utf8)
        } catch {
            return nil
        }
    }
    
    
    
}

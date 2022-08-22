//
//  CardsDataService.swift
//  Hearthstone
//
//  Created by Stavros Tsikinas on 4/8/22.
//

import Foundation


// MARK: - Service Protocols
protocol LocalDownloadProtocol {
    func getUrl(from name: String?, typed: String?) -> URL?
    func getJSONString(from url: URL) throws -> String?
}

protocol JSONConverterProtocol {
    func convert(from json: String, completion: @escaping([Card]) -> Void)
}

protocol ResultHadlerProtocol {
    func handleParsed(_ cards: [Card], from view: CardsCollectionViewController?) -> [CardViewModel]
    func featuresFilter(is activated: Bool, for cards: [CardViewModel]) -> [CardViewModel]
}


struct CardsDataService: LocalDownloadProtocol, JSONConverterProtocol, ResultHadlerProtocol {

    
    // MARK: - Variables
    
    // Public
    public let type: ServiceType
    
    // Private
    private let extensionType = "json"
    
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
    
    func handleParsed(_ cards: [Card], from view: CardsCollectionViewController?) -> [CardViewModel] {
        var viewModels = [CardViewModel]()
        for card in cards {
            switch type {
            case .AllCards:
                viewModels.append(CardViewModel(card: card, select: {
                    view?.select(card)
                }))
                break
            default:
                break
            }
        }
        return viewModels
    }
    
    func featuresFilter(is activated: Bool, for cards: [CardViewModel]) -> [CardViewModel] {
        if activated {
            return cards.filter(\.isHsiaoFav)
        } else {
            return cards.filter { !$0.isHsiaoFav }
        }
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

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
    func handleParsed(_ cards: [Card], from view: CardsCollectionViewController?, completion: @escaping ([CardViewModel]) -> Void)
    func featuresFilter(for cards: [CardViewModel]) -> [CardViewModel]
}


struct CardsDataService: LocalDownloadProtocol, JSONConverterProtocol, ResultHadlerProtocol {

    
    // MARK: - Variables
    
    // Public
    public let type: ServiceType
    public var favoritesService: FavoritesService?
    
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
    
    func handleParsed(_ cards: [Card], from view: CardsCollectionViewController? = nil, completion: @escaping ([CardViewModel]) -> Void) {
        var viewModels = [CardViewModel]()
        switch type {
        case .AllCards:
            for card in cards {
                let cardVM = CardViewModel(card: card, select: {
                    view?.select(card)
                })
                cardVM.delegate = view
                viewModels.append(cardVM)
            }
            completion(viewModels)
            break
        case .Favorites:
            favoritesService?.getFavorites { favIDs in
                for favID in favIDs {
                    if let card = cards.first(where: { $0.cardId == favID }) {
                        let cardVM = CardViewModel(card: card, select: {
                            view?.select(card)
                        })
                        cardVM.delegate = view
                        viewModels.append(cardVM)
                    }
                }
                completion(viewModels)
            }
        }
    }
    
    func featuresFilter(for cards: [CardViewModel]) -> [CardViewModel] {
        return cards.filter(\.isHsiaoFav)
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

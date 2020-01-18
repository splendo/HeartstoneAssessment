import Foundation

internal final class HearthstoneCardManager {
    internal struct Request {
        internal let sortNamesAscending: Bool
        internal let activeFilters: HearthstoneActiveFilters?
    }
    
    internal struct Result {
        internal let sets: [HearthstoneCardSet]
        
        internal var isEmpty: Bool { sets.isEmpty }
    }
        
    internal init() {}
    
    internal func retrieveCards(_ request: Request, completion: @escaping (Result?, Error?) -> Void) {
        DispatchQueue.global(qos: .background).async {
            guard let url = Bundle.main.url(forResource: "cards", withExtension: "json")  else {
                DispatchQueue.main.async {
                    completion(nil, nil)
                }
                
                return
            }
            
            do {
                let data = try Data(contentsOf: url)
                
                guard let cardSetDictionary = try JSONSerialization.jsonObject(with: data, options: []) as? [String: [Any]] else {
                    print("Failed to cast to [String: Any]")
                    
                    DispatchQueue.main.async {
                        completion(nil, nil)
                    }
                    
                    return
                }
                
                guard let cardSets = HearthstoneCardManager.cardsSetsFrom(cardSetDictionary) else {
                    print("Failed to parse to card sets [String: Any]")
                    
                    DispatchQueue.main.async {
                        completion(nil, nil)
                    }
                    
                    return
                }
                
                let filteredCardSets = HearthstoneCardManager.filter(cardSets, with: request)
                let sortedCardSets = HearthstoneCardManager.sort(filteredCardSets, with: request)
                
                DispatchQueue.main.async {
                    completion(Result(sets: sortedCardSets), nil)
                }
            } catch {
                print("Failed to parse json: \(error)")
                
                DispatchQueue.main.async {
                    completion(nil, nil)
                }
            }
        }
    }
    
    private static func cardsSetsFrom(_ dictionary: [String: [Any]]) -> [HearthstoneCardSet]? {
        let decoder = JSONDecoder()
        
        let sets = dictionary.compactMap { key, cardDictionaries -> HearthstoneCardSet? in
            guard cardDictionaries.isNotEmpty, let data = try? JSONSerialization.data(withJSONObject: cardDictionaries, options: []) else {
                return nil
            }
            
            do {
                let cards = try decoder.decode([HearthstoneCard].self, from: data)
                
                guard cards.isNotEmpty else {
                    return nil
                }
                
                return HearthstoneCardSet(title: key, cards: cards)
            } catch {
                return nil
            }
        }
        
        let sortedSets = sets.sorted { left, right -> Bool in
            left.title < right.title
        }
        
        return sortedSets
    }
    
    private static func sort(_ cardSets: [HearthstoneCardSet], with request: Request) -> [HearthstoneCardSet] {
        return cardSets.map { cardSet in
            let cards = cardSet.cards.sorted { left, right -> Bool in
                if request.sortNamesAscending {
                    return left.name < right.name
                } else {
                    return left.name > right.name
                }
            }
            
            return HearthstoneCardSet(title: cardSet.title, cards: cards)
        }
    }
    
    private static func filter(_ cardSets: [HearthstoneCardSet], with request: Request) -> [HearthstoneCardSet] {
        guard let activeFilters = request.activeFilters, activeFilters.isEmpty == false else {
            return cardSets
        }
        
        let results = cardSets.compactMap { cardSet -> HearthstoneCardSet? in
            let cards = cardSet.cards.filter { card -> Bool in
                for (key, value) in activeFilters.activeFilters {
                    switch key {
                    case "rarity":
                        if value.contains(where: { $0.key == card.rarity }) == false {
                            return false
                        }
                    case "mechanis":
                        guard let mechanics = card.mechanics else {
                            return false
                        }
                        
                        for mechanic in mechanics {
                            if value.contains(where: { $0.key == mechanic.name }) == false {
                                return false
                            }
                        }
                    case "type":
                        if value.contains(where: { $0.key == card.type }) == false {
                            return false
                        }
                    case "playerClasses":
                        if value.contains(where: { $0.key == card.playerClass }) == false {
                            return false
                        }
                    default:
                        assertionFailure("Unsupported filter")
                    }
                }
                
                return true
            }

            guard cards.isNotEmpty else {
                return nil
            }
            
            return HearthstoneCardSet(title: cardSet.title, cards: cards)
        }
        
        return results
    }
}

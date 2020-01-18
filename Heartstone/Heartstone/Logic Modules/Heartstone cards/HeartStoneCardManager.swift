import Foundation

internal final class HeartStoneCardManager {
    internal struct Request {
        internal let sortNamesAscending: Bool
        internal let activeFilters: HeartStoneActiveFilters?
    }
    
    internal struct Result {
        internal let sets: [HeartStoneCardSet]
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
                
                guard let cardSets = HeartStoneCardManager.cardsSetsFrom(cardSetDictionary) else {
                    print("Failed to parse to card sets [String: Any]")
                    
                    DispatchQueue.main.async {
                        completion(nil, nil)
                    }
                    
                    return
                }
                
                let filteredCardSets = HeartStoneCardManager.filter(cardSets, with: request)
                let sortedCardSets = HeartStoneCardManager.sort(filteredCardSets, with: request)
                
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
    
    private static func cardsSetsFrom(_ dictionary: [String: [Any]]) -> [HeartStoneCardSet]? {
        let decoder = JSONDecoder()
        
        let sets = dictionary.compactMap { key, cardDictionaries -> HeartStoneCardSet? in
            guard cardDictionaries.isNotEmpty, let data = try? JSONSerialization.data(withJSONObject: cardDictionaries, options: []) else {
                return nil
            }
            
            do {
                let cards = try decoder.decode([HeartStoneCard].self, from: data)
                
                guard cards.isNotEmpty else {
                    return nil
                }
                
                return HeartStoneCardSet(title: key, cards: cards)
            } catch {
                return nil
            }
        }
        
        let sortedSets = sets.sorted { left, right -> Bool in
            left.title < right.title
        }
        
        return sortedSets
    }
    
    private static func sort(_ cardSets: [HeartStoneCardSet], with request: Request) -> [HeartStoneCardSet] {
        return cardSets.map { cardSet in
            let cards = cardSet.cards.sorted { left, right -> Bool in
                if request.sortNamesAscending {
                    return left.name < right.name
                } else {
                    return left.name > right.name
                }
            }
            
            return HeartStoneCardSet(title: cardSet.title, cards: cards)
        }
    }
    
    private static func filter(_ cardSets: [HeartStoneCardSet], with request: Request) -> [HeartStoneCardSet] {
        guard let activeFilters = request.activeFilters, activeFilters.isEmpty == false else {
            return cardSets
        }
        
        let results = cardSets.compactMap { cardSet -> HeartStoneCardSet? in
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
            
            return HeartStoneCardSet(title: cardSet.title, cards: cards)
        }
        
        return results
    }
}

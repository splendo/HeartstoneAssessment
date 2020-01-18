import Foundation

internal final class HeartStoneCardManager {
    internal struct Request {
        internal let activeFilters: HeartStoneActiveFilters?
    }
    
    internal struct Result {
        internal let cards: [HeartStoneCard]
    }
    
    private struct ParseResult: Codable {
        fileprivate let cards: [HeartStoneCard]
        
        private enum CodingKeys: String, CodingKey {
            case cards = "Basic"
        }
    }
    
    internal init() {}
    
    internal func retrieveCards(_ request: Request, completion: @escaping (Result?, Error?) -> Void) {
        DispatchQueue.global(qos: .background).async {
            guard let url = Bundle.main.url(forResource: "cards", withExtension: "json")  else {
                DispatchQueue.main.async {
                    #warning("TODO: Add error")
                    completion(nil, nil)
                }
                
                return
            }
            
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                
                let result = try decoder.decode(ParseResult.self, from: data)
                let cards = HeartStoneCardManager.filter(result, with: request)
                
                DispatchQueue.main.async {
                    completion(Result(cards: cards), nil)
                }
            } catch {
                print("Failed to parse json: \(error)")
                
                DispatchQueue.main.async {
                    completion(nil, nil)
                }
            }
        }
    }
    
    private static func filter(_ result: ParseResult, with request: Request) -> [HeartStoneCard] {
        guard let activeFilters = request.activeFilters, activeFilters.isEmpty == false else {
            return result.cards
        }
        
        return result.cards.filter { card -> Bool in
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
                default:
                    break
                }
            }
            
            return true
        }
    }
}

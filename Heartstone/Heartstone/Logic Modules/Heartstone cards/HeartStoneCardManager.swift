import Foundation

internal final class HeartStoneCardManager {
    internal struct Request {}
    
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
                
                DispatchQueue.main.async {
                    completion(Result(cards: result.cards), nil)
                }
            } catch {
                print("Failed to parse json: \(error)")
            }
        }
    }
}

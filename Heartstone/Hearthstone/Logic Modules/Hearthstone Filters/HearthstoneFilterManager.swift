import Foundation

internal final class HearthstoneFilterManager {
    internal struct Result: Codable {
        internal let filters: [HearthstoneFilter]
    }
    
    internal init() {}
    
    internal func retrieveFilters(completion: @escaping (Result?, Error?) -> Void) {
        DispatchQueue.global(qos: .background).async {
            guard let url = Bundle.main.url(forResource: "filters", withExtension: "json")  else {
                DispatchQueue.main.async {
                    #warning("TODO: Add error")
                    completion(nil, nil)
                }
                
                return
            }
            
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                
                let filters = try decoder.decode([HearthstoneFilter].self, from: data)
                
                DispatchQueue.main.async {
                    completion(Result(filters: filters), nil)
                }
            } catch {
                print("Failed to parse json: \(error)")
                
                DispatchQueue.main.async {
                    completion(nil, nil)
                }
            }
        }
    }
}

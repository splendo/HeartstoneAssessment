import Foundation

internal struct HeartStoneCard: Codable {
    internal struct Mechanic: Codable {
        internal let name: String
    }
    
    internal let cardId: String
    internal let name: String
    internal let cardSet: String
    internal let type: String
    internal let faction: String?
    internal let rarity: String?
    internal let cost: Int?
    internal let text: String?
    internal let flavor: String?
    internal let artist: String?
    internal let collectible: Bool?
    internal let playerClass: String
    internal let locale: String
    internal let imageUrlString: String?
    internal let mechanics: [Mechanic]?
    
    internal var imageURL: URL? {
        guard let imageUrlString = imageUrlString else {
            return nil
        }
        
        return URL(string: imageUrlString)
    }
    
    private enum CodingKeys: String, CodingKey {
        case cardId, name, cardSet, type, text, playerClass, locale
        case faction, rarity, flavor, artist, collectible, cost, mechanics
        case imageUrlString = "img"
    }
}

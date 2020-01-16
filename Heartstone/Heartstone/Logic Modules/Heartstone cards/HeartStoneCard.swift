import Foundation

internal struct HeartStoneCard: Codable {
    internal let cardId: String
    internal let name: String
    internal let cardSet: String
    internal let type: String
    internal let text: String?
    internal let playerClass: String
    internal let locale: String
    internal let imageUrlString: String?
    
    internal var imageURL: URL? {
        guard let imageUrlString = imageUrlString else {
            return nil
        }
        
        return URL(string: imageUrlString)
    }
    
    private enum CodingKeys: String, CodingKey {
        case cardId, name, cardSet, type, text, playerClass, locale
        case imageUrlString = "img"
    }
}

import Foundation

internal final class FavoritesManager {
    private let userDefaults: UserDefaults
    private let encoder = JSONEncoder()
    private let decoder = JSONDecoder()
    
    internal init(userDefaults: UserDefaults = .standard) {
        self.userDefaults = userDefaults
    }
    
    internal var favoriteCardIds: Set<String> {
        get {
            guard let data = userDefaults.object(forKey: "favorite.cardIds") as? Data else {
                return []
            }
            
            do {
                return try decoder.decode(Set<String>.self, from: data)
            } catch {
                assertionFailure("Could not get Set from data")
                return []
            }
        }
        set {
            do {
                let data = try encoder.encode(newValue)
                
                userDefaults.set(data, forKey: "favorite.cardIds")
            } catch {
                assertionFailure("Failed to encode set")
            }
        }
    }
    
    internal func isFavorite(cardId: String) -> Bool {
        favoriteCardIds.contains(cardId)
    }
    
    internal func addFavorite(cardId: String) {
        favoriteCardIds.insert(cardId)
    }
    
    internal func removeFavorite(cardId: String) {
        favoriteCardIds.remove(cardId)
    }
}

extension FavoritesManager {
    internal func isFavorite(_ card: HearthstoneCard) -> Bool {
        isFavorite(cardId: card.cardId)
    }
    
    internal func addFavorite(_ card: HearthstoneCard) {
        addFavorite(cardId: card.cardId)
    }
    
    internal func removeFavorite(_ card: HearthstoneCard) {
        removeFavorite(cardId: card.cardId)
    }
}

import Foundation

internal struct HearthstoneActiveFilters: Codable, Hashable {
    internal var activeFilters: [String: Set<HearthstoneFilter.Item>] = [:]
    
    internal var isEmpty: Bool { activeFilters.isEmpty }
    
    internal mutating func addValue(_ value: HearthstoneFilter.Item, forKey key: String) {
        var items = activeFilters[key] ?? []
        
        items.insert(value)
        
        activeFilters[key] = items
    }
    
    internal mutating func removeValue(_ value: HearthstoneFilter.Item, forKey key: String) {
        guard var items = activeFilters[key] else {
            return
        }
        
        items.remove(value)
        
        activeFilters[key] = items.isEmpty ? nil : items
    }
    
    internal func containsValue(_ value: HearthstoneFilter.Item, forKey key: String) -> Bool {
        activeFilters[key]?.contains(value) ?? false
    }
}

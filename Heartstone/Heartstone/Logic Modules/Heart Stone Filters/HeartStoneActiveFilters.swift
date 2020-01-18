import Foundation

internal struct HeartStoneActiveFilters: Codable, Hashable {
    internal var activeFilters: [String: Set<HeartStoneFilter.Item>] = [:]
    
    internal var isEmpty: Bool { activeFilters.isEmpty }
    
    internal mutating func addValue(_ value: HeartStoneFilter.Item, forKey key: String) {
        var items = activeFilters[key] ?? []
        
        items.insert(value)
        
        activeFilters[key] = items
    }
    
    internal mutating func removeValue(_ value: HeartStoneFilter.Item, forKey key: String) {
        guard var items = activeFilters[key] else {
            return
        }
        
        items.remove(value)
        
        activeFilters[key] = items.isEmpty ? nil : items
    }
    
    internal func containsValue(_ value: HeartStoneFilter.Item, forKey key: String) -> Bool {
        activeFilters[key]?.contains(value) ?? false
    }
}

import Foundation

internal struct HeartStoneFilter: Codable, Hashable {
    internal struct Item: Codable, Hashable {
        internal let key: String
        internal let title: String
    }
    
    internal let name: String
    internal let key: String
    internal let type: String
    internal let items: [Item]
}

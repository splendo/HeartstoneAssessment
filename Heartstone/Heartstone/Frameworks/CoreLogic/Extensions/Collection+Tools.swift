extension Collection {
    public subscript (safe index: Index) -> Element? {
        return indices.contains(index) ? self[index] : nil
    }
    
    public var isNotEmpty: Bool {
        return isEmpty == false
    }
}

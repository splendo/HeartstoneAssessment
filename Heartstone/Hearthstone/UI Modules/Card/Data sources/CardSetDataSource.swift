import Foundation

internal final class CardSetDataSource {
    internal var sets: [HearthstoneCardSet] = []
    
    internal var sectionCount: Int { sets.count }
    
    internal func setAtSection(_ section: Int) -> HearthstoneCardSet? { sets[safe: section] }
    internal func numberOfItems(inSection section: Int) -> Int { setAtSection(section)?.cards.count ?? 0 }
    internal func cardAt(_ indexPath: IndexPath) -> HearthstoneCard? { setAtSection(indexPath.section)?.cards[safe: indexPath.item] }
}

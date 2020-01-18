import Foundation

internal final class CardSetDataSource {
    internal var sets: [HeartStoneCardSet] = []
    
    internal var sectionCount: Int { sets.count }
    
    internal func setAtSection(_ section: Int) -> HeartStoneCardSet? { sets[safe: section] }
    internal func numberOfItems(inSection section: Int) -> Int { setAtSection(section)?.cards.count ?? 0 }
    internal func cardAt(_ indexPath: IndexPath) -> HeartStoneCard? { setAtSection(indexPath.section)?.cards[safe: indexPath.item] }
}

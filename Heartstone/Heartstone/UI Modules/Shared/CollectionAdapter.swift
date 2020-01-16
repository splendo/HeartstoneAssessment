import UIKit

internal protocol CollectionAdapter: AnyObject {
    func configure(_ collectionView: UICollectionView)
}

import UIKit

internal protocol TableAdapter: AnyObject {
    func configure(_ tableView: UITableView)
}

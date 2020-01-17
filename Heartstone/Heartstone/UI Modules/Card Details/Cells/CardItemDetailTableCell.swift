import UIKit

internal final class CardItemDetailTableCell: UITableViewCell {
    internal struct ViewModel {
        internal var title: String
        internal var detail: String
    }
    
    internal var viewModel: ViewModel? {
        didSet { viewModelUpdated() }
    }
    
    internal override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: .value1, reuseIdentifier: reuseIdentifier)
    }
    
    internal required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func viewModelUpdated() {
        textLabel?.text = viewModel?.title
        detailTextLabel?.text = viewModel?.detail
    }
}

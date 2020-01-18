import UIKit

internal final class FilterTableCell: UITableViewCell {
    internal struct ViewModel {
        internal let title: String
    }
    
    internal var viewModel: ViewModel? {
        didSet { viewModelUpdated() }
    }
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: .default, reuseIdentifier: reuseIdentifier)
        
        configureViews()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func configureViews() {
        imageView?.image = UIImage(systemName: "checkmark")
        imageView?.alpha = 0
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        imageView?.alpha = selected ? 1 : 0
    }
    
    private func viewModelUpdated() {
        textLabel?.text = viewModel?.title
    }
}

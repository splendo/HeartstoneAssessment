import UIKit

internal final class CardTextTableCell: UITableViewCell {
    internal override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: .default, reuseIdentifier: reuseIdentifier)
        
        configureViews()
    }
    
    internal required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    internal override func prepareForReuse() {
        super.prepareForReuse()
        
        textLabel?.text = nil
    }
}

// MARK: - Configure views
extension CardTextTableCell {
    private func configureViews() {
        textLabel?.numberOfLines = 0
    }
}

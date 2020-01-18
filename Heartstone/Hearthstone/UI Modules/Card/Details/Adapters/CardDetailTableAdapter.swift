import UIKit

internal final class CardDetailTableAdapter: NSObject {
    internal typealias ViewModel = CardCollectionCell.ViewModel
    
    fileprivate struct Section {
        fileprivate let sectionTitle: String?
        fileprivate let items: [Item]
        
        fileprivate var count: Int { items.count }
        
        fileprivate func itemAtIndex(_ index: Int) -> Item? {
            items[safe: index]
        }
        
        fileprivate init(items: [Item], sectionTitle: String? = nil) {
            self.sectionTitle = sectionTitle
            self.items = items
        }
    }
    
    fileprivate enum Item {
        case headerImage
        case textItem(String)
        case detailItem(CardItemDetailTableCell.ViewModel)
    }
    
    private var sections: [Section] = []
    private var viewModel: ViewModel?
    
    internal var sectionCount: Int { sections.count }
    
    internal func numberOfItemsInSection(_ section: Int) -> Int { sectionForIndex(section)?.count ?? 0 }
    
    internal func setViewModel(_ viewModel: ViewModel?) {
        self.viewModel = viewModel
        
        sections.removeAll()
        
        guard viewModel != nil else {
            return
        }
        
        sections.append(Section(items: [.headerImage]))
        
        [createTextSection(), createDetailsSection(), createSpecsSection(), createMechanicsSection()]
            .compactMap { $0 }
            .forEach { sections.append($0) }
    }
    
    private func sectionForIndex(_ index: Int) -> Section? { sections[safe: index] }
    
    private func item(at indexPath: IndexPath) -> Item? { sectionForIndex(indexPath.section)?.itemAtIndex(indexPath.item) }
    
    private func createTextSection() -> Section? {
        var items: [Item] = []
        
        if let text = viewModel?.card.text, text.isEmpty == false {
            items.append(.textItem(text))
        }
        
        if let flavor = viewModel?.card.flavor, flavor.isEmpty == false {
            items.append(.textItem(flavor))
        }
        
        if items.isEmpty {
            return nil
        } else {
            return Section(items: items)
        }
    }
    
    private func createDetailsSection() -> Section? {
        var items: [Item] = []
        
        if let cardSet = viewModel?.card.cardSet, cardSet.isEmpty == false {
            items.append(.detailItem(.init(title: "Card Set", detail: cardSet)))
        }
        
        if let type = viewModel?.card.type, type.isEmpty == false {
            items.append(.detailItem(.init(title: "Type", detail: type)))
        }
        
        if let faction = viewModel?.card.faction, faction.isEmpty == false {
            items.append(.detailItem(.init(title: "Faction", detail: faction)))
        }
        
        if let rarity = viewModel?.card.rarity, rarity.isEmpty == false {
            items.append(.detailItem(.init(title: "Rarity", detail: rarity)))
        }
        
        if let artist = viewModel?.card.artist, artist.isEmpty == false {
            items.append(.detailItem(.init(title: "Artist", detail: artist)))
        }
        
        if let playerClass = viewModel?.card.playerClass, playerClass.isEmpty == false {
            items.append(.detailItem(.init(title: "Class", detail: playerClass)))
        }
    
        if items.isEmpty {
            return nil
        } else {
            return Section(items: items, sectionTitle: "Details")
        }
    }
    
    private func createSpecsSection() -> Section? {
        var items: [Item] = []
            
        if let health = viewModel?.card.health {
            items.append(.detailItem(.init(title: "Health", detail: "\(health)")))
        }
            
        if let attack = viewModel?.card.attack {
            items.append(.detailItem(.init(title: "Attack", detail: "\(attack)")))
        }
            
        if let cost = viewModel?.card.cost {
            items.append(.detailItem(.init(title: "Cost", detail:" \(cost)")))
        }
        
        if items.isEmpty {
            return nil
        } else {
            return Section(items: items, sectionTitle: "Specifications")
        }
    }
    
    private func createMechanicsSection() -> Section? {
        guard let mechanics = viewModel?.card.mechanics, mechanics.isEmpty == false else {
            return nil
        }
        
        let items: [Item] = viewModel?.card.mechanics?.map { Item.textItem($0.name) } ?? []
        
        return Section(items: items, sectionTitle: "Mechanics")
    }
}

// MARK: - TableAdapter
extension CardDetailTableAdapter: TableAdapter {
    internal func configure(_ tableView: UITableView) {
        tableView.delegate = self
        tableView.dataSource = self
        
        tableView.estimatedRowHeight = 44
        tableView.rowHeight = UITableView.automaticDimension
        
        tableView.sectionHeaderHeight = 32
        
        tableView.register(cell: CardDetailImageTableCell.self)
        tableView.register(cell: CardItemDetailTableCell.self)
        tableView.register(cell: CardTextTableCell.self)
    }
}

// MARK: - UITableViewDelegate
extension CardDetailTableAdapter: UITableViewDelegate {
    internal func tableView(_ tableView: UITableView, shouldHighlightRowAt indexPath: IndexPath) -> Bool { false }
    
    internal func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        sectionForIndex(section)?.sectionTitle
    }
}

// MARK: - UITableViewDataSource
extension CardDetailTableAdapter: UITableViewDataSource {
    internal func numberOfSections(in tableView: UITableView) -> Int { sectionCount }
    internal func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int { numberOfItemsInSection(section) }
    
    internal func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let item = item(at: indexPath) else {
            fatalError("Invalid index")
        }
        
        switch item {
        case .headerImage:
            let cell = tableView.dequeueReusableCell(for: CardDetailImageTableCell.self, indexPath: indexPath)
            
            cell.setViewModel(viewModel)
            
            return cell
        case .textItem(let text):
            let cell = tableView.dequeueReusableCell(for: CardTextTableCell.self, indexPath: indexPath)
            
            cell.textLabel?.text = text
            
            return cell
        case .detailItem(let viewModel):
            let cell = tableView.dequeueReusableCell(for: CardItemDetailTableCell.self, indexPath: indexPath)
            
            cell.viewModel = viewModel
            
            return cell
        }
    }
}

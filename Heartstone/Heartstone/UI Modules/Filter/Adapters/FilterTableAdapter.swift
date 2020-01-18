import UIKit

internal final class FilterTableAdapter: NSObject {
    fileprivate struct Section {
        fileprivate let filter: HeartStoneFilter
        
        fileprivate var sectionTitle: String { filter.name }
        fileprivate let items: [Item]
        
        fileprivate var count: Int { items.count }
        
        fileprivate init(filter: HeartStoneFilter) {
            self.filter = filter
            self.items = filter.items.map { Item(item: $0) }
        }
        
        fileprivate func itemAtIndex(_ index: Int) -> Item? {
            items[safe: index]
        }
        
        fileprivate func indexAndItem(for item: HeartStoneFilter.Item) -> (Int, Item)? {
            items.enumerated().first { _, value -> Bool in value.item == item }
        }
    }
    
    fileprivate struct Item {
        fileprivate let item: HeartStoneFilter.Item
        
        fileprivate var viewModel: FilterTableCell.ViewModel { FilterTableCell.ViewModel(title: item.title) }
    }
    
    private var sections: [Section] = []
    private var sectionCount: Int { sections.count }
    
    internal let availableFilters: [HeartStoneFilter]
    internal private(set) var activeFilters: HeartStoneActiveFilters
    
    internal init(availableFilters: [HeartStoneFilter], activeFilters: HeartStoneActiveFilters) {
        self.availableFilters = availableFilters
        self.activeFilters = activeFilters
        
        super.init()
        
        reloadData()
    }
    
    private func sectionForIndex(_ index: Int) -> Section? { sections[safe: index] }
    private func item(at indexPath: IndexPath) -> Item? { sectionForIndex(indexPath.section)?.itemAtIndex(indexPath.item) }
    
    private func sectionAndIndexForKey(_ key: String) -> (Int, Section)? {
        sections.enumerated().first { _, section -> Bool in section.filter.key == key }
    }
    
    private func numberOfItemsInSection(_ section: Int) -> Int { sectionForIndex(section)?.count ?? 0 }
    
    private func reloadData() {
        sections = availableFilters.compactMap(Section.init(filter:))
    }
}

// MARK: - TableAdapter
extension FilterTableAdapter: TableAdapter {
    internal func configure(_ tableView: UITableView) {
        tableView.delegate = self
        tableView.dataSource = self
        
        tableView.allowsMultipleSelection = true
        
        tableView.sectionHeaderHeight = 32
        tableView.rowHeight = 44
        
        tableView.register(cell: FilterTableCell.self)
        
        selectedIndexPaths.forEach { tableView.selectRow(at: $0, animated: false, scrollPosition: .none) }
    }
    
    internal var selectedIndexPaths: [IndexPath] {
        var indexPaths: [IndexPath] = []
        
        activeFilters.activeFilters.forEach { key, values in
            guard let indexSection = sectionAndIndexForKey(key) else {
                return
            }
            
            values.forEach { item in
                if let indexItem = indexSection.1.indexAndItem(for: item) {
                    indexPaths.append(IndexPath(item: indexItem.0, section: indexSection.0))
                }
            }
        }
        
        return indexPaths
    }
}

// MARK: - UITableViewDelegate
extension FilterTableAdapter: UITableViewDelegate {    
    internal func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        sectionForIndex(section)?.sectionTitle
    }
    
    internal func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard let section = sectionForIndex(indexPath.section), let item = section.itemAtIndex(indexPath.item) else {
            return
        }
        
        activeFilters.addValue(item.item, forKey: section.filter.key)
    }
    
    internal func tableView(_ tableView: UITableView, didDeselectRowAt indexPath: IndexPath) {
        guard let section = sectionForIndex(indexPath.section), let item = section.itemAtIndex(indexPath.item) else {
            return
        }
        
        activeFilters.removeValue(item.item, forKey: section.filter.key)
    }
}

// MARK: - UITableViewDataSource
extension FilterTableAdapter: UITableViewDataSource {
    internal func numberOfSections(in tableView: UITableView) -> Int { sectionCount }
    internal func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int { numberOfItemsInSection(section) }
    
    internal func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let item = item(at: indexPath) else {
            fatalError("Invalid index")
        }
        
        let cell = tableView.dequeueReusableCell(for: FilterTableCell.self, indexPath: indexPath)
        
        cell.viewModel = item.viewModel
        
        return cell
    }
}

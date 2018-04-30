//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import UIKit

protocol HCSelectionTableDelegate {
    func didPressApplyButton()
    func didPressCancelButton()
    func didSelect(row: Int, in section: Int)
}

protocol HCSelectionTableDataAdapterType: class {
    func sectionsCount() -> Int
    func rowsCountIn(_ section: Int) -> Int
    func titleForSection(_ section: Int) -> String
    func titleForRow(row: Int, in section: Int) -> String
    func isSelected(row: Int, in section: Int) -> Bool
}

class HCSelectionTableVC: UITableViewController {

    let dataAdapter: HCSelectionTableDataAdapterType

    var delegate: HCSelectionTableDelegate?

    required init?(coder aDecoder: NSCoder) {
        fatalError("Not implemented")
    }

    init(dataAdapter: HCSelectionTableDataAdapterType) {
        self.dataAdapter = dataAdapter
        super.init(nibName: nil, bundle: nil)
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.allowsMultipleSelection = false
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        tableView.reloadData()

        navigationItem.leftBarButtonItem = createCancelButton()
        navigationItem.rightBarButtonItem = createApplyButton()
    }

    // MARK: View actions

    @objc private func didPressCancelButton() {
        delegate?.didPressCancelButton()
    }

    @objc private func didPressApplyButton() {
        delegate?.didPressApplyButton()
    }

    // MARK: View helpers

    private func createCancelButton() -> UIBarButtonItem {
        let button = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(didPressCancelButton))
        return button
    }

    private func createApplyButton() -> UIBarButtonItem {
        let button = UIBarButtonItem(title: "Apply", style: .plain, target: self, action: #selector(didPressApplyButton))
        return button
    }
}

// MARK: UITableViewDataSource & UITableViewDelegate

extension HCSelectionTableVC {

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: false)
        delegate?.didSelect(row: indexPath.row, in: indexPath.section)
        tableView.reloadSections(IndexSet(integer: indexPath.section), with: .none)
    }

    override func numberOfSections(in tableView: UITableView) -> Int {
        return dataAdapter.sectionsCount()
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataAdapter.rowsCountIn(section)
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MyCell", for: indexPath as IndexPath)
        cell.selectionStyle = .none
        cell.textLabel!.text = dataAdapter.titleForRow(row: indexPath.row, in: indexPath.section)
        let isSelected = dataAdapter.isSelected(row: indexPath.row, in: indexPath.section)
        cell.accessoryType = isSelected ? .checkmark : .none
        return cell
    }

    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return dataAdapter.titleForSection(section)
    }
}
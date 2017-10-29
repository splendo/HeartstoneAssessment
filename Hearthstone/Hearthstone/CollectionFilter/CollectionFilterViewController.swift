//
//  CollectionFilterViewController.swift
//  Hearthstone
//
//  Created by Dmitrii on 27/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import UIKit

class CollectionFilterViewController: UIViewController {

    // MARK: - Properties
    fileprivate let filterCellId = "FilterCellId"

    @IBOutlet private var tableView: UITableView!

    weak var delegate: CollectionFilterViewControllerDelegate?
    private let dataModel = CollectionFilterViewControllerModel()

    // MARK: - Lyfecycle
    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Filters"
    }


    // MARK: - Public
    func setFilter(filter: CardsCollectionFilter) {
        dataModel.filter = filter
    }

    // MARK: - Private


    // MARK: - Actions
    @IBAction func applyButtonPressed() {
        delegate?.dismissController(controller: self, filter: dataModel.filter)
    }

    @IBAction func cancelButtonPressed() {
        delegate?.dismissController(controller: self, filter: nil)
    }

    @IBAction func resetButtonPressed() {
        dataModel.resetFilters()
        tableView.reloadData()
    }
}


extension CollectionFilterViewController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        guard let cell = tableView.cellForRow(at: indexPath) else {return}
        dataModel.setOptionSelected(indexPath: indexPath, option:cell.textLabel!.text!)
        tableView.reloadSections([indexPath.section], with: .automatic)
    }
}

extension CollectionFilterViewController: UITableViewDataSource {

    func numberOfSections(in tableView: UITableView) -> Int {
        return dataModel.amountOfFilters()
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let count = dataModel.filterPropertyOptions(index: section)?.count ?? 0
        return count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: filterCellId)!
        if let model = dataModel.cellModel(indexPath: indexPath) {
            cell.textLabel?.text = model.title
            if dataModel.isOptionChecked(indexPath: indexPath) {
                cell.accessoryType = .checkmark
            } else {
                cell.accessoryType = .none
            }
        }
        return cell
    }

    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return dataModel.filterPropertyName(index: section)
    }
}


protocol CollectionFilterViewControllerDelegate: class {
    func dismissController(controller: UIViewController, filter: CardsCollectionFilter?)
}

//
//  CardFilterViewController.swift
//  HearthstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/17/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation
import UIKit

protocol CardFilterViewControllerDelegate: NSObjectProtocol {
    func onPickFilterType(_ filterName: String)
}

struct FilterContants {
    static let reuseIdentifier = "FilterCell"
}

class CardFilterViewController: UIViewController {
    
    @IBOutlet weak var filtertableView: UITableView!
    weak var filterDelegate: CardFilterViewControllerDelegate?
    
    let filterArray = LoadDataManager.populateFilters()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    @IBAction func closeButtonTapped(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
}

extension CardFilterViewController: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return filterArray[section].filterName
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return filterArray.count
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return filterArray[section].types.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = filtertableView.dequeueReusableCell(withIdentifier: FilterContants.reuseIdentifier, for: indexPath) as! FilterTableViewCell
        let name = filterArray[indexPath.section].types[indexPath.row]
        cell.setFilterName(name)
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        filterDelegate?.onPickFilterType(filterArray[indexPath.section].types[indexPath.row])
        dismiss(animated: true, completion: nil)
    }
}

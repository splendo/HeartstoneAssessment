//
//  CardFilterViewController.swift
//  HeartStone
//
//  Created by Ben Zatrok on 2017. 10. 27..
//  Copyright © 2017. Amberglass. All rights reserved.
//

import UIKit

protocol CardFilterViewControllerDelegate
{
    func cardFilterSet(mechanicToFilter mechanicSelected: CardMechanic)
    func cardFilterCleared()
}

class CardFilterViewController: UIViewController
{
    //MARK: Variables
    
    var delegate                            : CardFilterViewControllerDelegate?
    var availableMechanics                  : [CardMechanic] = []
    
    fileprivate let cardMechanicCellId      = "cardMechanicCellId"
    fileprivate let cardMechanicCellHeight  : CGFloat = 50
    
    var selectedMechanic                    : CardMechanic?
    
    //MARK: Enums
    
    //MARK: IBOutlets
    
    @IBOutlet weak var tableView: UITableView!
    
    //MARK: IBActions
    
    //MARK: Life-cycle
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        setupView()
        setupDelegation()
    }
    
    //MARK: Functions
    
    func setupView()
    {
        title                   = "Card mechanics"
        
        availableMechanics      = CardMechanic.allMechanics()
    }
    
    func setupDelegation()
    {
        tableView.delegate      = self
        tableView.dataSource    = self
    }
}

extension CardFilterViewController: UITableViewDelegate
{
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath)
    {
        selectedMechanic = selectedMechanic == availableMechanics[indexPath.row] ? nil : availableMechanics[indexPath.row]
        
        dismiss(animated: true) {
            
            if let selectedMechanic = self.selectedMechanic
            {
                self.delegate?.cardFilterSet(mechanicToFilter: selectedMechanic)
            }
            else
            {
                self.delegate?.cardFilterCleared()
            }
        }
    }
}

extension CardFilterViewController: UITableViewDataSource
{
    func numberOfSections(in tableView: UITableView) -> Int
    {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return availableMechanics.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat
    {
        return cardMechanicCellHeight
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell
    {
        let cell                = tableView.dequeueReusableCell(withIdentifier: cardMechanicCellId, for: indexPath)
        let currentMechanic     = availableMechanics[indexPath.row]
        cell.textLabel?.text    = currentMechanic.mechanicName
        cell.accessoryType      = selectedMechanic == currentMechanic ? .checkmark : .none

        return cell
    }
}

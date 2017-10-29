//
//  CollectionFilterViewControllerModel.swift
//  Hearthstone
//
//  Created by Dmitrii on 27/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import Foundation

class CollectionFilterViewControllerModel {

    // MARK: - Properties
    var filter: CardsCollectionFilter?

    // MARK: - Lyfecycle


    // MARK: - Public
    func cellModel(indexPath: IndexPath) -> CollectionFilterCellModel? {
        guard let options = filterPropertyOptions(index: indexPath.section) else {return nil}
        guard indexPath.row >= 0 && indexPath.row < options.count else {return nil}
        let title = options[indexPath.row]
        return CollectionFilterCellModel(title: title, checked: false)
    }

    func setOptionSelected(indexPath: IndexPath, option: String) {
        setValueToFilter(stringValue: option, index: indexPath.section)
    }

    func resetFilters() {
        filter?.type = nil
        filter?.playerClass = nil
        filter?.mechanicsName = nil
        filter?.rarity = nil
    }

    // MARK: - Private


    // MARK: - Filter
    func amountOfFilters() -> Int {
        return 4
    }

    func isOptionChecked(indexPath: IndexPath) -> Bool {
        guard let filter = self.filter else {return false}
        switch indexPath.section {
        case 0:
            if indexPath.row >= 0 && indexPath.row < CardType.allValues.count {
                if filter.type == CardType.allValues[indexPath.row] {
                    return true
                }
            }
        case 1:
            if indexPath.row >= 0 && indexPath.row < CardPlayerClass.allValues.count {
                if filter.playerClass == CardPlayerClass.allValues[indexPath.row] {
                    return true
                }
            }
        case 2:
            if indexPath.row >= 0 && indexPath.row < CardMechanicName.allValues.count {
                if filter.mechanicsName == CardMechanicName.allValues[indexPath.row] {
                    return true
                }
            }
        case 3:
            if indexPath.row >= 0 && indexPath.row < CardRarity.allValues.count {
                if filter.rarity == CardRarity.allValues[indexPath.row] {
                    return true
                }
            }
        default:
            return false
        }
        return false
    }

    func filterPropertyName(index: Int) -> String? {
        switch index {
        case 0:
            return "Card Type"
        case 1:
            return "Player Class"
        case 2:
            return "Mechanic"
        case 3:
            return "Rarity"
        default:
            return nil
        }
    }

    func filterPropertyOptions(index: Int) -> [String]? {
        switch index {
        case 0:
            var options = [String]()
            for item in CardType.allValues {
                options.append(item.rawValue)
            }
            return options
        case 1:
            var options = [String]()
            for item in CardPlayerClass.allValues {
                options.append(item.rawValue)
            }
            return options
        case 2:
            var options = [String]()
            for item in CardMechanicName.allValues {
                options.append(item.rawValue)
            }
            return options
        case 3:
            var options = [String]()
            for item in CardRarity.allValues {
                options.append(item.rawValue)
            }
            return options
        default:
            return nil
        }
    }

    func setValueToFilter(stringValue: String, index: Int) {
        guard filter != nil else {return}
        switch index {
        case 0:
            guard let newType = CardType(rawValue: stringValue) else {return}
            filter!.type = newType
        case 1:
            guard let newClass = CardPlayerClass(rawValue: stringValue) else {return}
            filter!.playerClass = newClass
        case 2:
            guard let newMechanics = CardMechanicName(rawValue: stringValue) else {return}
            filter!.mechanicsName = newMechanics
        case 3:
            guard let newRarity = CardRarity(rawValue: stringValue) else {return}
            filter!.rarity = newRarity
        default:
            return
        }
    }
}


struct CollectionFilterCellModel {
    let title: String
    let checked: Bool
}

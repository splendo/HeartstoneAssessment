//
//  CardItem.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

import CoreData

extension CardItem {
    
    var imageURL: URL? {
        guard let img = img else {
            return nil
        }
        return URL(string: img)
    }
}

//
//  DataError.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

class DataError: NSObject, LocalizedError {
    
    var desc = ""
    
    init(_ str: String) {
        desc = str
    }    
    
    override var description: String {
        get {
            return desc
        }
    }
    
    var errorDescription: String? {
        get {
            return self.description
        }
    }
}

//
//  Localization.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

class AppLang {
    
    static var bundle : Bundle? = nil
    class func updateBundle(lang : String ) {
        var l = lang
        if l == "en" {
            l = "Base"
        }
        let path = Bundle.main.path(forResource: l, ofType: "lproj")
        AppLang.bundle = Bundle(path: path!)
    }
    
    class func load() {
        let l = UserDefaults.standard.string(forKey: "AppLang")
        if l == nil {
            AppLang.bundle = nil
        } else {
            AppLang.updateBundle(lang: l!)
        }
    }
    
    class func setID(k : String) {
        let d = UserDefaults.standard
        d.setValue(k, forKey: "AppLang")
        d.synchronize()
        
        AppLang.updateBundle(lang: k)
    }
}

func appLang() -> String {
    if AppLang.bundle == nil {
        return ""
    } else {
        let l = UserDefaults.standard.string(forKey: "AppLang")
        if l == "Base" {
            return "ENGLISH".localized
        }        
        return ""
    }
}

extension String {
    var isLocalizable : Bool {
        // by default can be changed as a rule for localization
        return true
    }
    
    var localized : String {
        return NSLocalizedString(self, tableName: nil, bundle: AppLang.bundle!, value: "", comment: "")
    }
    
    func localized(_ defaultString: String) -> String {
        return NSLocalizedString(self, tableName: nil, bundle: AppLang.bundle!, value: defaultString, comment: "")
    }
}

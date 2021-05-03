//
//  UIViewController+Helpers.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation
import UIKit

extension UIViewController {
    
    func presentAlert(title: String?, message: String?, ok:(() -> Void)?, retry:(() -> Void)?) {
        let vc = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let oka = UIAlertAction(title: "OK".localized, style: .cancel) { (_) in
            if ok != nil {
                ok!()
            }
        }
        vc.addAction(oka)
        
        if retry != nil {
            let retrya = UIAlertAction(title: "RETRY".localized, style: .default, handler: { (_) in
                retry!()
            })
            vc.addAction(retrya)
        }
        
        present(vc, animated: true, completion: nil)
    }
    
    func presentAlert(title: String?, message: String?, ok:(() -> Void)?, cancel:(() -> Void)?) {
        let vc = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let oka = UIAlertAction(title: "OK".localized, style: .cancel) { (_) in
            if ok != nil {
                ok!()
            }
        }
        vc.addAction(oka)
        
        if cancel != nil {
            let cancl = UIAlertAction(title: "CANCEL".localized, style: .default, handler: { (_) in
                cancel!()
            })
            vc.addAction(cancl)
        }
        
        present(vc, animated: true, completion: nil)
    }
    
    func present(error : Error, ok:(() -> Void)?, retry:(() -> Void)?) {
        presentAlert(title: "Error".localized, message: error.localizedDescription, ok: ok, retry: retry)
    }
    
}

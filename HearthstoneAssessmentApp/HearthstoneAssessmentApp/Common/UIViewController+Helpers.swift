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
        presentAlert(title: "NO_CONTENT_ERROR".localized, message: error.localizedDescription, ok: ok, retry: retry)
    }
    
    
    func presentAlertAboutForgottenPIN(forgot:(() -> Void)?, cancel:(() -> Void)?) {
        let vc = UIAlertController(
            title: "",
            message: " \("FORGOT_THE_CODE".localized). \n\n \("SESSION_WILL_BE_DISCARDED".localized) ",
            preferredStyle: .alert
        )
        
        vc.addAction(
            UIAlertAction(title: "YES".localized, style: .cancel, handler: { (_) in
                if forgot != nil {
                    forgot!()
                }
            })
        )
        
        vc.addAction(
            UIAlertAction(title: "NO".localized, style: .default, handler: { (_) in
                if cancel != nil {
                    cancel!()
                }
            })
        )
        
        present(vc, animated: true, completion: nil)
    }
    
    func presentAlertForgotPassword(restore:(() -> Void)?, cancel:(() -> Void)?) {
        let vc = UIAlertController(
            title: "LOGIN_FORGOT_PASSWORD".localized,
            message: "FORGOT_PASSWORD_MESSAGE".localized,
            preferredStyle: .alert
        )
        
        vc.addAction(
            UIAlertAction(title: "RESTORE_PASSWORD".localized, style: .default, handler: { (_) in
                if restore != nil {
                    restore!()
                }
            })
        )
        
        vc.addAction(
            UIAlertAction(title: "CANCEL".localized, style: .default, handler: { (_) in
                if cancel != nil {
                    cancel!()
                }
            })
        )
        
        present(vc, animated: true, completion: nil)
    }
    
    func presentAcionSheet(title: String, message: String? = nil, deleteAction:(() -> Void)?, cancel:(() -> Void)?) {
        let vc = UIAlertController(
            title: title,
            message: message,
            preferredStyle: .actionSheet
        )
        
        vc.addAction(
            UIAlertAction(title: "DELETE".localized, style: .default, handler: { (_) in
                if deleteAction != nil {
                    deleteAction!()
                }
            })
        )
        
        vc.addAction(
            UIAlertAction(title: "CANCEL".localized, style: .default, handler: { (_) in
                if cancel != nil {
                    cancel!()
                }
            })
        )
        
        present(vc, animated: true, completion: nil)
    }
}

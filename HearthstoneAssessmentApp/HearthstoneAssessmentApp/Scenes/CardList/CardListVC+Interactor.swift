//
//  CardListVC+Interactor.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation
import MBProgressHUD

extension CardListViewController: CardListView {
    
    func setCards(cards: Array<Card>) {
        self.cardsDataSource = cards
    }
    
    func startLoading() {        
        activityIndicator = MBProgressHUD.showAdded(to: view!, animated: true)
    }
    
    func finishLoading() {
        activityIndicator?.hide(animated: true)
    }
 
    func setError(error: Error) {
        present(error: error, ok: nil, retry: nil)
    }
}

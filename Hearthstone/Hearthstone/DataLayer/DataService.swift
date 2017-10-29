//
//  DataService.swift
//  Hearthstone
//
//  Created by Dmitrii on 25/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import Foundation

let dataServiceCardsUpdatedNotification = NSNotification.Name(rawValue: "dataServiceCardsUpdatedNotification")
let dataServiceCardsUpdatedNotificationErrorKey = "dataServiceCardsUpdatedNotificationErrorKey"

class DataService {

    // MARK: - Properties
    private var apiClient: APIClient
    private var notificationCenter: NotificationCenterProtocol
    private var cardsInternal = [Card]()

    // MARK: - Lyfecycle
    init(apiClient: APIClient = APIClient(), notificationCenter: NotificationCenterProtocol = NotificationCenter.default) {
        self.apiClient = apiClient
        self.notificationCenter = notificationCenter
    }

    // MARK: - Public
    func loadCards(completion: (() -> ())? = nil) {
        apiClient.loadCards(completion: { [weak self] (response, error) in
            if response != nil {
                self?.cardsInternal = response as! [Card]
                self?.cardsInternal.sort(by: { (c1, c2) -> Bool in
                    return c1.name < c2.name
                })
            }
            let userInfo = (error != nil) ? [dataServiceCardsUpdatedNotificationErrorKey : error!] : [:]
            self?.notificationCenter.post(
                name: dataServiceCardsUpdatedNotification,
                object: self,
                userInfo: userInfo
            )
            completion?()
        })
    }

    func cards() -> [Card] {
        return cardsInternal
    }

    // MARK: - Private
    
}


protocol NotificationCenterProtocol {
    func post(name aName: NSNotification.Name, object anObject: Any?, userInfo aUserInfo: [AnyHashable : Any]?)
}


extension NotificationCenter: NotificationCenterProtocol {}

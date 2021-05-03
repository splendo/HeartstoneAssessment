//
//  BaseViewProtocol.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

protocol BaseViewProtocol: NSObjectProtocol {
    func startLoading()
    func finishLoading()
    func setError(error: Error)
}

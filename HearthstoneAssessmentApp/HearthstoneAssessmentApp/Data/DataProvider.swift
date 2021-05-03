//
//  DataProvider.swift
//  HeartstoneAssessmentApp
//
//  Created by Igor Kruglik on 4/11/18.
//  Copyright © 2018 ikruglik. All rights reserved.
//

import Foundation

protocol DataObjectProviderProtocol {
    func loadCardData(onDataLoad: @escaping (RootCardsObject) -> Void, onFailed: @escaping (Error) -> Void)
}

class JsonDataProvider: DataObjectProviderProtocol {
    
    static let FILE_TYPE = "json"
    
    var fileName: String
    var urlString: String?
    
    init(fileName: String, urlString: String? = nil) {
        self.fileName = fileName
//        self.urlString = urlString
    }
    
    func loadCardData(onDataLoad: @escaping (RootCardsObject) -> Void, onFailed: @escaping (Error) -> Void) {
        if let url = urlString {
            self.loadData(urlString: url) { [weak self] (objectData, error) in
                if let err = error {
                    onFailed(err)
                } else {
                    self?.processData(objectData, onComplete: onDataLoad, onFailed: onFailed)
                }
            }
        } else {
            self.loadData(bundleFileName: fileName, onLoad: { [weak self] (data) in
                self?.processData(data, onComplete: onDataLoad, onFailed: onFailed)
            }) { (error) in
                onFailed(error)
            }
        }
    }
    
     private func loadData(urlString: String, completionHandler: @escaping (Data?, Error?) -> Void ) {
        let url = URL(string: urlString)
        let task = URLSession.shared.dataTask(with: url!, completionHandler: { (data, response, error) -> Void in
            if error != nil {
                completionHandler(nil, error)
                return
            }
            else {
                completionHandler(data, nil)
            }
        })
        task.resume()
    }
    
     private func loadData(bundleFileName: String, onLoad: (Data?) -> Void, onFailed: (Error) -> Void) {
        if let filepath = Bundle.main.path(forResource: bundleFileName, ofType: JsonDataProvider.FILE_TYPE) {
            do {
                let jsonData = try Data(contentsOf: URL(fileURLWithPath: filepath), options: .mappedIfSafe)
                onLoad(jsonData)
            } catch {
                onFailed(error)
            }
        } else {
            onFailed(DataError("File doesn't exist"))
        }
    }
    
    private func processData(_ data: Data?, onComplete: (RootCardsObject) -> Void , onFailed: (Error) -> Void ) {
        if let jsonData = data {
            do {
                let object = try JSONDecoder().decode(RootCardsObject.self, from: jsonData)
                onComplete(object)
            } catch {
                onFailed(error)
            }
        } else {
            onFailed(DataError("JSON malformed"))
        }
    }
    
}

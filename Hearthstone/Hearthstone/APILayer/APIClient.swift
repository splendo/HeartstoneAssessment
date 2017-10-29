//
//  APIClient.swift
//  Hearthstone
//
//  Created by Dmitrii on 25/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import Foundation

typealias RequestCompletion = (Any?, Error?) -> ()

class APIClient: NSObject {

    // MARK: - Properties
    static let requestHeaderKey = "X-Mashape-Key"
    static let mashapePrivateKey = "6dyrGJCOPvmshIyUKt06xfw1aEWwp16ipoKjsnUaI6ebTDV1H4"
    static let loadCardsURL = "https://omgvamp-hearthstone-v1.p.mashape.com/cards"

    private var session: URLSessionProtocol!

    // MARK: - Lyfecycle
    init(session sessionToSet: URLSessionProtocol? = nil) {
        super.init()
        if (sessionToSet == nil) {
            self.session = URLSession(configuration: .default, delegate: self, delegateQueue: nil)
        } else {
            self.session = sessionToSet
        }
    }

    // MARK: - Public
    func loadCards(completion: @escaping RequestCompletion) {
        let url = URL(string: APIClient.loadCardsURL)!
        var request = URLRequest(url: url)
        request.addValue(APIClient.mashapePrivateKey, forHTTPHeaderField: APIClient.requestHeaderKey)
        let task = session.dataTask(
            with: request,
            completionHandler: { (data, response, error) in
                APIResponseParser.processCardsResponse(responseData: data, error: error, completion: completion)
        })
        task.resume()
    }

    // MARK: - Private
}


protocol URLSessionProtocol {
    func dataTask(with request: URLRequest, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Swift.Void) -> URLSessionDataTask
}


extension URLSession: URLSessionProtocol {}


extension APIClient: URLSessionDelegate {}

//
//  APIClientTest.swift
//  HearthstoneTests
//
//  Created by Dmitrii on 29/10/2017.
//  Copyright © 2017 DI. All rights reserved.
//

import XCTest

class APIClientTest: XCTestCase {

    var apiClientToTest: APIClient!

    var sessionMock: URLSessionMock!
    var task: URLSessionDataTaskMock!
    var completion: RequestCompletion!

    override func setUp() {
        super.setUp()
        task = URLSessionDataTaskMock()
        completion = {
            (response, error) -> () in
        }
        sessionMock = URLSessionMock(task: task)
        apiClientToTest = APIClient(session: sessionMock)
    }

    func testLoadCardsTaskFillingIn() {
        apiClientToTest.loadCards(completion: completion)

        XCTAssertNotNil(sessionMock.request)
        XCTAssertNotNil(sessionMock.request?.url)
        XCTAssertEqual(sessionMock.request?.url?.absoluteString, APIClient.loadCardsURL)

        let testHeaders = sessionMock.request?.allHTTPHeaderFields
        XCTAssertNotNil(testHeaders)
        XCTAssertEqual(testHeaders?.count, 1)
        XCTAssertEqual(testHeaders?.first?.key, APIClient.requestHeaderKey)
        XCTAssertEqual(testHeaders?.first?.value, APIClient.mashapePrivateKey)
    }

    func testLoadCardsTaskRun() {
        apiClientToTest.loadCards(completion: completion)

        XCTAssertTrue(task.resumeCalled)
    }
}


class URLSessionMock: URLSessionProtocol {

    var request: URLRequest?
    let task: URLSessionDataTaskMock

    init(task: URLSessionDataTaskMock) {
        self.task = task
    }

    func dataTask(with request: URLRequest, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Swift.Void) -> URLSessionDataTask {
        self.request = request
        task.completion = completionHandler
        return task
    }
}

class URLSessionDataTaskMock: URLSessionDataTask {

    var completion: ((Data?, URLResponse?, Error?) -> Void)?
    var _data: Data?
    var _response: URLResponse?
    var _error: NSError?

    var resumeCalled = false

    override func resume() {
        resumeCalled = true
        completion?(_data, _response, _error)
    }
}

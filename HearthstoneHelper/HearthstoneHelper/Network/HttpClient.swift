//
// Created by Maxim Berezhnoy on 15/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

protocol Cancelable {
    func cancel()
}

protocol HttpCommunicator {
    @discardableResult
    func get(url: URL, completion: @escaping Http.TaskCompletion) -> Cancelable
}

enum Http {
    typealias TaskCompletion = (Result<Data, RequestError>) -> Void

    private typealias URLSessionTaskCompletion = (Data?, URLResponse?, Error?) -> Void

    enum RequestError: Error {
        case noData
        case httpError(Error)
    }

    final class Client: HttpCommunicator {
        private let urlSession: URLSession

        init() {
            urlSession = URLSession.shared
        }

        @discardableResult
        func get(url: URL, completion: @escaping TaskCompletion) -> Cancelable {
            let task = Task(urlTaskFactory: { sessionTaskCompletion in
                urlSession.dataTask(with: url, completionHandler: sessionTaskCompletion)
            }, completion: completion)

            task.start()

            return task
        }
    }

    private class Task: Cancelable {
        private let urlSessionTask: URLSessionDataTask

        init(urlTaskFactory: (@escaping URLSessionTaskCompletion) -> URLSessionDataTask,
             completion: @escaping TaskCompletion) {
            self.urlSessionTask = urlTaskFactory { data, _, error in
                guard let data = data else {
                    if let error = error {
                        completion(.failure(.httpError(error)))
                    } else {
                        completion(.failure(.noData))
                    }

                    return
                }

                completion(.success(data))
            }
        }

        func start() {
            assert(urlSessionTask.state == .suspended)
            urlSessionTask.resume()
        }

        func cancel() {
            urlSessionTask.cancel()
        }
    }
}
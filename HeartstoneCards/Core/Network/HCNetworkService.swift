//
// Created by Roman Petryshen on 22/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation
import Alamofire

public enum HCResult<T> {
    case completed(T)
    case failed(HCError)
}


class HCNetworkService {

    lazy var decoder: JSONDecoder = {
        return JSONDecoder()
    }()

    let baseUrl: String

    init(baseUrl: String) {
        self.baseUrl = baseUrl
    }

    public func fetch<T: Decodable>(method: HTTPMethod, path: String, parameters: Alamofire.Parameters?, completion: @escaping (HCResult<T>) -> Void) {

        Alamofire.request(
                baseUrl + path,
                method: method,
                parameters: parameters,
                encoding: URLEncoding.default,
                headers: nil).responseJSON { [weak self] response in

            var data = response.data

            // Dirty hack used to support paginated response.
            // Should be fixed by implementing suitable endpoint response.
            if path == HCApiDataStructure.GetCardsRequest().path {
                if let value = response.value {
                    var newValue = ["data": value]

                    typealias C = HCPaginationCursor

                    if let limit = parameters?[C.limitUrlKey] as? String, let start = parameters?[C.startUrlKey] as? String, let rows = value as? Array<Any> {
                        if (rows.count == Int(limit)) {
                            newValue["cursor"] = ["start": Int(start)! + Int(limit)!, "limit": Int(limit)]
                        }
                    }
                    guard let newData = try? JSONSerialization.data(withJSONObject: newValue, options: []) else {
                        completion(HCResult.failed(.malformedData))
                        return
                    }
                    data = newData
                }
            }
            // End of hack.

            if let data = data, let jsonDecoder = self?.decoder {
                do {
                    let model = try jsonDecoder.decode(T.self, from: data)
                    completion(HCResult.completed(model))
                } catch {
                    completion(HCResult.failed(.malformedData))
                }
            }

        }
    }

}


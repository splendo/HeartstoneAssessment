//
//  CoolCardAPIRequest.swift
//  CoolCards
//
//  Created by Chatterjee, Sumeru(AWF) on 12/18/17.
//  Copyright © 2017 Chatterjee, Sumeru. All rights reserved.
//

import Foundation
import Moya

enum CoolCardsAPIRequest {
    case cards
    case card(cardId: String)
}

extension CoolCardsAPIRequest: TargetType {
    
    var baseURL: URL {
        // The server is running on google app engine in a docker container and the code is also in the repository

        return URL(string: "http://35.205.213.172:5000")!
    }
    
    var headers: [String : String]? {
        return nil
    }
    
    var path: String {
        switch self {
        case .cards:
            return "/cards"
        case .card(let cardId):
            return "/card/\(cardId)"
        }
    }
    
    var method: Moya.Method {
        return .get
    }
    
    var parameterEncoding: ParameterEncoding {
        return JSONEncoding.default
    }
    
    var parameters: [String: Any]? {
        return nil
    }
    
    var sampleData: Data {
        return Data()
    }
    
    var task: Moya.Task {
        return .requestPlain
    }
}

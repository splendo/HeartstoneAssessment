//
// Created by Roman Petryshen on 26/04/2018.
// Copyright (c) 2018 Roman Petryshen. All rights reserved.
//

import Foundation

public enum HCError: Error {

    case internalError(description: String?)
    case malformedData
    case notImplemented
    case unknown

    public var userDescription: String {
        switch self {
        case .internalError(_): return "Internal error"
        case .notImplemented: return "Not implemented"
        case .malformedData: return "Not possible to encode/decode data"
        case .unknown: return "Unknown error"
        }
    }
}

extension Error {
    public var hcError: HCError {
        return self as? HCError ?? HCError.unknown
    }
}
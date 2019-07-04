//
//  CardCollection.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

struct CardCollection: Decodable {

    let groups: [String: [Card]]

    struct GroupKeys: CodingKey {
        let intValue: Int? = nil
        var stringValue: String
        init?(stringValue: String) {
            self.stringValue = stringValue
        }
        init?(intValue: Int) { return nil }
    }

    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: GroupKeys.self)
        var results = [String: [Card]]()
        try container.allKeys.forEach { key in
            results[key.stringValue] = try container.decode([Card].self, forKey: key)
        }
        groups = results
    }
}

//
// Created by Maxim Berezhnoy on 13/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

struct CardSets: Decodable {
    let sets: [String: [Card]]
    var cards: [Card] {
        sets.values.flatMap({ $0 })
    }

    struct SetsCodingKeys: CodingKey {
        var intValue: Int?
        let stringValue: String

        init?(intValue: Int) {
            fatalError("Unexpected intValue while decoding \(SetsCodingKeys.self)")
        }

        init?(stringValue: String) {
            self.stringValue = stringValue
        }
    }

    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: SetsCodingKeys.self)
        self.sets = try container.allKeys.reduce(into: [String: [Card]](), { sets, key in
            sets[key.stringValue] = try container.decode([Card].self, forKey: key)
        })
    }
}
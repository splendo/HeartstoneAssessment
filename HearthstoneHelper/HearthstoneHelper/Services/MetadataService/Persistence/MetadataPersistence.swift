//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import Foundation

protocol MetadataPersisting {
    func persist(metadata: CardMetadata, forCardWithId id: Card.ID)
    func retrieveMetadata(forCardWithId id: Card.ID) -> CardMetadata?
}

class UserDefaultMetadataPersistence: MetadataPersisting {
    let userDefaults = UserDefaults.standard

    func persist(metadata: CardMetadata, forCardWithId id: Card.ID) {
        let encoder = PropertyListEncoder()
        guard let encodedMetadata = try? encoder.encode(metadata) else { return }

        userDefaults.set(encodedMetadata, forKey: id)
    }

    func retrieveMetadata(forCardWithId id: Card.ID) -> CardMetadata? {
        let decoder = PropertyListDecoder()
        guard let storedData = userDefaults.value(forKey: id) as? Data,
              let decodedMetadata = try? decoder.decode(CardMetadata.self, from: storedData)
                else { return nil }
        
        return decodedMetadata
    }
}
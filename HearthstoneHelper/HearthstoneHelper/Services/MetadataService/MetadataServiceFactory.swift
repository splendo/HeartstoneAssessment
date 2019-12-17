//
// Created by Maxim Berezhnoy on 16/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

protocol MetadataServiceCreating {
    func create() -> MetadataProviding
}

class MetadataServiceFactory: MetadataServiceCreating {
    func create() -> MetadataProviding { 
        let persistence = UserDefaultMetadataPersistence()
        
        return MetadataService(persistence: persistence)
    }
}
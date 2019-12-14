//
// Created by Maxim Berezhnoy on 12/12/2019.
// Licensed under the MIT license
//
// Copyright (c) 2019 rencevio. All rights reserved.

import struct Foundation.Data

final class RootDependencies {
    let collectionCreating: CollectionCreating
    
    init() {
        collectionCreating = CollectionFactory()
    }
    
    func createCollectionViewController() -> CollectionDisplaying {
        collectionCreating.createCollectionViewController()
    }
}
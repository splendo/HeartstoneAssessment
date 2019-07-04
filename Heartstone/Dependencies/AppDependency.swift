//
//  AppDependency.swift
//  Heartstone
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

protocol Dependency {
    var dataProvider: DataProvider { get }
    var storage: Storage { get }
}

class AppDependency: Dependency {
    let dataProvider: DataProvider = LocalCardDataProvider()
    let storage = Storage(name: "Heartstone")
}

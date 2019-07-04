//
//  MockDependency.swift
//  HeartstoneTests
//
//  Created by Grigory Avdyushin on 04/07/2019.
//  Copyright © 2019 Grigory Avdyushin. All rights reserved.
//

@testable import Heartstone

struct MockDataProvider: DataProvider {
    func fetchCardsList(_ completion: (Result<Card, Error>) -> Void) {
    }
}

class MockDependency: Dependency {
    let dataProvider: DataProvider = MockDataProvider()
}

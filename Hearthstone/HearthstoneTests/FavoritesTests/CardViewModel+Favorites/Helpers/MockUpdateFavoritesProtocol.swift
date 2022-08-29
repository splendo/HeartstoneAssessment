//
//  MockUpdateFavoritesProtocol.swift
//  HearthstoneTests
//
//  Created by Stavros Tsikinas on 26/8/22.
//

import XCTest
@testable import Hearthstone

class MockUpdateFavoritesProtocol: UpdateFavoritesProtocol {
    
    var isFavorite: Bool?
    
    private let dbService: TestCoreDataService!
    private let testCase: XCTestCase
    // For the updating tests
    private var expectation: XCTestExpectation?
    
    init(for testCase: XCTestCase, with dbService: TestCoreDataService) {
        self.testCase = testCase
        self.dbService = dbService
    }
    
    // MAR: - Helpers
    func expectDidBecomeFavorite() {
        expectation = testCase.expectation(description: "expectDidBecomeFavorite")
    }
    
    // MARK: - UpdateFavoritesProtocol functions
    func initFavorite(for card: CardViewModel, completion: @escaping (Bool) -> Void) {
        dbService.exists(with: card.cardID) { isFavorite in
            completion(isFavorite)
        }
    }
    
    func updateFavorite(for card: CardViewModel) {
        if card.isFavorite {
            dbService.save(card.cardID) { [weak self] saved in
                if self?.expectation != nil {
                    self?.isFavorite = saved
                    self?.expectation?.fulfill()
                    self?.expectation = nil
                }
            }
        } else {
            dbService.delete(cardID: card.cardID) { [weak self] deleted in
                if self?.expectation != nil {
                    self?.isFavorite = !deleted
                    self?.expectation?.fulfill()
                    self?.expectation = nil
                }
            }
        }
    }
    

}

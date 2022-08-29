//
//  CardViewModelHelper.swift
//  HearthstoneTests
//
//  Created by Stavros Tsikinas on 26/8/22.
//

@testable import Hearthstone

func testCardViewModel() -> CardViewModel {
    CardViewModel(card: testCard(cardID: "TestCardID", name: "A test card"))
}

func testCardViewModel(favorite: Bool) -> CardViewModel {
    let cardVM = testCardViewModel()
    cardVM.isFavorite = favorite
    return cardVM
}

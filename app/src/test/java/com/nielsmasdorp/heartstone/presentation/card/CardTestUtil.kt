package com.nielsmasdorp.heartstone.presentation.card

import com.nielsmasdorp.domain.card.entity.Card

object CardTestUtil {

    /**
     * Create test [Card] with optional set values
     */
    fun createTestCard(id: String = "1",
                       name: String = "name",
                       imgUrl: String = "img",
                       cardSet: String = "set",
                       type: String = "type",
                       rarity: String = "rarity",
                       cost: Int = 10,
                       attack: Int = 4,
                       health: Int = 6,
                       text: String = "text",
                       flavor: String = "flavor",
                       classes: List<String> = emptyList(),
                       mechanics: List<String> = emptyList()): Card {
        return Card(id, name, imgUrl, cardSet, type, rarity, cost, attack, health, text, flavor, classes, mechanics)
    }

    /**
     * Create test [CardViewModel] with optional set values
     */
    fun createTestCardViewModel(id: String = "1",
                                name: String = "name",
                                imgUrl: String = "img",
                                cardSet: String = "set",
                                type: String = "type",
                                rarity: String = "rarity",
                                text: String = "text"): CardViewModel {
        return CardViewModel(id, name, imgUrl, cardSet, type, rarity, text)
    }
}

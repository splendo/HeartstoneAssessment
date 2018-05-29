package com.nielsmasdorp.domain.card

import com.nielsmasdorp.domain.card.entity.Card

object CardTestUtil {

    /**
     * Create test [Card] with optional set values
     */
    fun createTestCard(id: String = "1",
                       name: String = "test",
                       imgUrl: String = "testImg",
                       cardSet: String = "testCardSet",
                       type: String = "testType",
                       rarity: String = "testRarity",
                       cost: Int = 10,
                       attack: Int = 4,
                       health: Int = 6,
                       text: String = "testText",
                       flavor: String = "testFlavor",
                       classes: List<String> = emptyList(),
                       mechanics: List<String> = emptyList()): Card {
        return Card(id, name, imgUrl, cardSet, type, rarity, cost, attack, health, text, flavor, classes, mechanics)
    }
}
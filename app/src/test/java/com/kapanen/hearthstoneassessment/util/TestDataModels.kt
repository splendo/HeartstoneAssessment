package com.kapanen.hearthstoneassessment.util

import com.kapanen.hearthstoneassessment.data.CardType
import com.kapanen.hearthstoneassessment.model.*

object TestDataModels {

    val mockedCard1 = Card(
        cardId = "EX1_371",
        cardType = CardType.BASIC,
        name = "Hand of Protection",
        cardSet = "Basic",
        type = "Spell",
        faction = "Neutral",
        rarity = "Free",
        cost = 1,
        htmlText = "Give a minion <b>Divine Shield</b>.",
        flavor = "This spell has been renamed so many times, even paladins don\u2019t know what it should be called anymore.",
        artist = "Clint Langley",
        collectible = true,
        playerClass = "Paladin",
        img = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/EX1_371.png",
        imgGold = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/EX1_371_premium.gif",
        mechanics = listOf(Mechanic("Divine Shield")),
        isFavorite = false
    )

    val mockedCard2 = Card(
        cardId = "CFM_321",
        cardType = CardType.MEAN_STREETS_OF_GADGETZAN,
        name = "Grimestreet Informant",
        cardSet = "Mean Streets of Gadgetzan",
        type = "Minion",
        rarity = "Rare",
        cost = 2,
        attack = 1,
        health = 1,
        htmlText = "Give a minion <b>Divine Shield</b>.",
        flavor = "This spell has been renamed so many times, even paladins don\u2019t know what it should be called anymore.",
        artist = "Clint Langley",
        collectible = true,
        playerClass = "Neutral",
        multiClassGroup = "Grimy Goons",
        img = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/CFM_321.png",
        imgGold = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/CFM_321_premium.gif",
        mechanics = listOf(Mechanic("Discover"), Mechanic("Battlecry")),
        isFavorite = false
    )

    val mockedCard3 = Card(
        cardId = "EX1_557",
        cardType = CardType.CLASSIC,
        name = "Nat Pagle",
        cardSet = "Classic",
        type = "Minion",
        rarity = "Legendary",
        cost = 2,
        attack = 0,
        health = 4,
        htmlText = "At the start of your turn, you have a 50% chance to draw an extra card.",
        flavor = "Nat Pagle, Azeroth's premier fisherman!  He invented the Auto-Angler 3000, the Extendo-Pole 3000, and the Lure-o-matic 2099 (still in testing).",
        artist = "Steve Prescott",
        collectible = true,
        elite = true,
        playerClass = "Neutral",
        img = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/EX1_557.png",
        imgGold = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/EX1_557_premium.gif",
        isFavorite = false
    )

    val mockedBeCard1 = BeCard(
        cardId = "EX1_371",
        name = "Hand of Protection",
        cardSet = "Basic",
        type = "Spell",
        faction = "Neutral",
        rarity = "Free",
        cost = 1,
        text = "Give a minion <b>Divine Shield</b>.",
        flavor = "This spell has been renamed so many times, even paladins don\u2019t know what it should be called anymore.",
        artist = "Clint Langley",
        collectible = true,
        playerClass = "Paladin",
        img = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/EX1_371.png",
        imgGold = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/EX1_371_premium.gif",
        mechanics = listOf(Mechanic("Divine Shield"))
    )

    val mockedBeCard2 = BeCard(
        cardId = "CFM_321",
        name = "Grimestreet Informant",
        cardSet = "Mean Streets of Gadgetzan",
        type = "Minion",
        rarity = "Rare",
        cost = 2,
        attack = 1,
        health = 1,
        text = "Give a minion <b>Divine Shield</b>.",
        flavor = "This spell has been renamed so many times, even paladins don\u2019t know what it should be called anymore.",
        artist = "Clint Langley",
        collectible = true,
        playerClass = "Neutral",
        multiClassGroup = "Grimy Goons",
        img = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/CFM_321.png",
        imgGold = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/CFM_321_premium.gif",
        mechanics = listOf(Mechanic("Discover"), Mechanic("Battlecry"))
    )

    val mockedBeCard3 = BeCard(
        cardId = "EX1_557",
        name = "Nat Pagle",
        cardSet = "Classic",
        type = "Minion",
        rarity = "Legendary",
        cost = 2,
        attack = 0,
        health = 4,
        text = "At the start of your turn, you have a 50% chance to draw an extra card.",
        flavor = "Nat Pagle, Azeroth's premier fisherman!  He invented the Auto-Angler 3000, the Extendo-Pole 3000, and the Lure-o-matic 2099 (still in testing).",
        artist = "Steve Prescott",
        collectible = true,
        elite = true,
        playerClass = "Neutral",
        img = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/EX1_557.png",
        imgGold = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/EX1_557_premium.gif"
    )

    val mockedDbCard1 = DbCard(
        cardId = "EX1_371",
        cardType = "Basic",
        name = "Hand of Protection",
        cardSet = "Basic",
        type = "Spell",
        faction = "Neutral",
        rarity = "Free",
        cost = 1,
        htmlText = "Give a minion <b>Divine Shield</b>.",
        flavor = "This spell has been renamed so many times, even paladins don\u2019t know what it should be called anymore.",
        artist = "Clint Langley",
        collectible = true,
        playerClass = "Paladin",
        img = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/EX1_371.png",
        imgGold = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/EX1_371_premium.gif",
        mechanics = "Divine Shield",
        isFavorite = false
    )

    val mockedDbCard2 = DbCard(
        cardId = "CFM_321",
        cardType = "Mean Streets of Gadgetzan",
        name = "Grimestreet Informant",
        cardSet = "Mean Streets of Gadgetzan",
        type = "Minion",
        rarity = "Rare",
        cost = 2,
        attack = 1,
        health = 1,
        htmlText = "Give a minion <b>Divine Shield</b>.",
        flavor = "This spell has been renamed so many times, even paladins don\u2019t know what it should be called anymore.",
        artist = "Clint Langley",
        collectible = true,
        playerClass = "Neutral",
        multiClassGroup = "Grimy Goons",
        img = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/CFM_321.png",
        imgGold = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/CFM_321_premium.gif",
        mechanics = "Discover|Battlecry",
        isFavorite = false
    )

    val mockedDbCard3 = DbCard(
        cardId = "EX1_557",
        cardType = "Classic",
        name = "Nat Pagle",
        cardSet = "Classic",
        type = "Minion",
        rarity = "Legendary",
        cost = 2,
        attack = 0,
        health = 4,
        htmlText = "At the start of your turn, you have a 50% chance to draw an extra card.",
        flavor = "Nat Pagle, Azeroth's premier fisherman!  He invented the Auto-Angler 3000, the Extendo-Pole 3000, and the Lure-o-matic 2099 (still in testing).",
        artist = "Steve Prescott",
        collectible = true,
        elite = true,
        playerClass = "Neutral",
        img = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/EX1_557.png",
        imgGold = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/EX1_557_premium.gif",
        isFavorite = false
    )

    val cardList = listOf(mockedCard1, mockedCard2, mockedCard3)
    val dbCardList = listOf(mockedDbCard1, mockedDbCard2, mockedDbCard3)

    val cards = Cards(
        basic = listOf(mockedBeCard1),
        classic = listOf(mockedBeCard3),
        meanStreetsOfGadgetzan = listOf(mockedBeCard2),
    )

}

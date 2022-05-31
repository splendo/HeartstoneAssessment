package com.kapanen.hearthstoneassessment.util

import android.content.res.Resources
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.data.CardType
import com.kapanen.hearthstoneassessment.model.BeCard
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.Cards
import com.kapanen.hearthstoneassessment.model.FilterItem
import com.kapanen.hearthstoneassessment.setting.AppSettings

fun Cards.toList(): List<Card> {
    val cardsList = mutableListOf<Card>()
    this.basic?.let { beCards -> cardsList.convertAndAdd(CardType.BASIC, beCards) }
    this.classic?.let { beCards -> cardsList.convertAndAdd(CardType.CLASSIC, beCards) }
    this.hallOfFame?.let { beCards -> cardsList.convertAndAdd(CardType.HALL_OF_FAME, beCards) }
    this.promo?.let { beCards -> cardsList.convertAndAdd(CardType.PROMO, beCards) }
    this.naxxramas?.let { beCards -> cardsList.convertAndAdd(CardType.NAXXRAMAS, beCards) }
    this.goblinsVsGnomes?.let { beCards ->
        cardsList.convertAndAdd(
            CardType.GOBLINS_VS_GNOMES,
            beCards
        )
    }
    this.blackrockMountain?.let { beCards ->
        cardsList.convertAndAdd(
            CardType.BLACKROCK_MOUNTAIN,
            beCards
        )
    }
    this.theGrandTournament?.let { beCards ->
        cardsList.convertAndAdd(
            CardType.THE_GRAND_TOURNAMENT,
            beCards
        )
    }
    this.theLeagueOfExplorers?.let { beCards ->
        cardsList.convertAndAdd(
            CardType.THE_LEAGUE_OF_EXPLORERS,
            beCards
        )
    }
    this.whispersOfTheOldGods?.let { beCards ->
        cardsList.convertAndAdd(
            CardType.WHISPERS_OF_THE_OLD_GODS,
            beCards
        )
    }
    this.oneNightInKarazhan?.let { beCards ->
        cardsList.convertAndAdd(
            CardType.ONE_NIGHT_IN_KARAZHAN,
            beCards
        )
    }
    this.meanStreetsOfGadgetzan?.let { beCards ->
        cardsList.convertAndAdd(
            CardType.MEAN_STREETS_OF_GADGETZAN,
            beCards
        )
    }
    this.journeyToUnQuoteGoro?.let { beCards ->
        cardsList.convertAndAdd(
            CardType.JOURNEY_TO_UN_QUOTE_GORO,
            beCards
        )
    }
    this.tavernBrawl?.let { beCards -> cardsList.convertAndAdd(CardType.TAVERN_BRAWL, beCards) }
    this.heroSkins?.let { beCards -> cardsList.convertAndAdd(CardType.HERO_SKINS, beCards) }
    this.missions?.let { beCards -> cardsList.convertAndAdd(CardType.MISSIONS, beCards) }
    this.credits?.let { beCards -> cardsList.convertAndAdd(CardType.CREDITS, beCards) }
    return cardsList.toList()
}

private fun MutableList<Card>.convertAndAdd(cardType: CardType, beCards: List<BeCard>?) {
    beCards?.map { it.toCard(cardType) }?.let { this.addAll(it) }
}

private fun BeCard.toCard(cardType: CardType) = Card(
    cardId = this.cardId,
    cardType = cardType,
    name = this.name,
    cardSet = this.cardSet,
    type = this.type,
    faction = this.faction,
    rarity = this.rarity,
    cost = this.cost,
    attack = this.attack,
    health = this.health,
    htmlText = this.text,
    flavor = this.flavor,
    artist = this.artist,
    elite = this.elite,
    collectible = this.collectible,
    playerClass = this.playerClass,
    multiClassGroup = this.multiClassGroup,
    img = this.img,
    imgGold = this.imgGold,
    mechanics = this.mechanics
)

fun List<Card>.sort(appSettings: AppSettings): List<Card> {
    return if (appSettings.isAscendingSorting) {
        this.sortedBy { it.name }
    } else {
        this.sortedByDescending { it.name }
    }
}

fun List<Card>.filter(appSettings: AppSettings, resources: Resources): List<Card> {
    val undefinedStr = resources.getString(R.string.filter_undefined_item)
    val typeFilterSet = appSettings.typeFilter.toStringSet()
    val rarityFilterSet = appSettings.rarityFilter.toStringSet()
    val classFilterSet = appSettings.classFilter.toStringSet()
    val mechanicFilterSet = appSettings.mechanicFilter.toStringSet()
    return this.filter { card ->
        mechanicFilterSet.checkFilter(card.mechanics?.map { it.name } ?: emptyList(), undefinedStr)
                && typeFilterSet.checkFilter(card.type, undefinedStr)
                && rarityFilterSet.checkFilter(card.rarity, undefinedStr)
                && classFilterSet.checkFilter(card.playerClass, undefinedStr)
    }
}

private fun Set<String>.checkFilter(value: String?, undefinedStr: String): Boolean {
    return this.contains(value) || (value.isNullOrBlank() && this.contains(undefinedStr))
}

private fun Set<String>.checkFilter(items: List<String>, undefinedStr: String): Boolean {
    for (item in items) {
        if (this.contains(item)) {
            return true
        }
    }
    if (this.contains(undefinedStr) && items.isEmpty()) {
        return true
    }
    return false
}

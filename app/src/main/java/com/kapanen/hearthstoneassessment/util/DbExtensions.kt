package com.kapanen.hearthstoneassessment.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kapanen.hearthstoneassessment.data.CardType
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.DbCard
import com.kapanen.hearthstoneassessment.model.Mechanic

private const val ITEM_DELIMITER = "|"

fun DbCard.toCard() = Card(
    cardId = this.cardId,
    cardType = CardType.values().first { it.typeName.equals(this.cardType, ignoreCase = true) },
    name = this.name,
    cardSet = this.cardSet,
    type = this.type,
    faction = this.faction,
    rarity = this.rarity,
    cost = this.cost,
    attack = this.attack,
    health = this.health,
    htmlText = this.htmlText,
    flavor = this.flavor,
    artist = this.artist,
    elite = this.elite,
    collectible = this.collectible,
    playerClass = this.playerClass,
    multiClassGroup = this.multiClassGroup,
    img = this.img,
    imgGold = this.imgGold,
    mechanics = this.mechanics?.split(ITEM_DELIMITER)?.map { name -> Mechanic(name) },
    isFavorite = this.isFavorite
)

fun Card.toDbCard() = DbCard(
    cardId = this.cardId,
    cardType = this.cardType?.typeName,
    name = this.name,
    cardSet = this.cardSet,
    type = this.type,
    faction = this.faction,
    rarity = this.rarity,
    cost = this.cost,
    attack = this.attack,
    health = this.health,
    htmlText = this.htmlText,
    flavor = this.flavor,
    artist = this.artist,
    elite = this.elite,
    collectible = this.collectible,
    playerClass = this.playerClass,
    multiClassGroup = this.multiClassGroup,
    img = this.img,
    imgGold = this.imgGold,
    mechanics = this.mechanics?.map { it.name }?.joinToString(separator = ITEM_DELIMITER),
    isFavorite = this.isFavorite
)

fun LiveData<List<DbCard>>.toCards(): LiveData<Result<List<Card>>> =
    this.map { dbCards -> Result.success(dbCards.map { it.toCard() }) }

fun List<DbCard>.toCards(): Result<List<Card>> = Result.success(this.map { it.toCard() })

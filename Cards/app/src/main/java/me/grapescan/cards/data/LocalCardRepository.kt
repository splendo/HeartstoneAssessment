package me.grapescan.cards.data

class LocalCardRepository : CardRepository {
    override suspend fun getCards() = listOf(
        Card("1", "First Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png"),
        Card("2", "Second Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png"),
        Card("3", "Third Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png"),
        Card("4", "Fourth Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png"),
        Card("5", "Fifth Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png"),
        Card("6", "Sixth Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png")
    )
}

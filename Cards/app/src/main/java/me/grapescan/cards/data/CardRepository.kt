package me.grapescan.cards.data

interface CardRepository {
    suspend fun getCards(query: Query = Query()): List<Card>
    suspend fun setFavorite(cardId: String, isFavorite: Boolean)
    suspend fun getCurrentSelection(): List<Card>

    data class Query(val filter: Filter = Filter(), val sorting: Sorting = Sorting())

    data class Sorting(val parameter: Parameter = Parameter.NAME, val isAscending: Boolean = true)

    enum class Parameter {
        NAME, CARD_SET, TYPE, RARITY, COST, ATTACK, HEALTH, TEXT, FLAVOR, ARTIST, COLLECTIBLE, PLAYER_CLASS
    }

    data class Filter(
        val type: Type = Type.ANY,
        val rarity: Rarity = Rarity.ANY,
        val playerClass: PlayerClass = PlayerClass.ANY,
        val mechanics: Mechanics = Mechanics.ANY
    )

    enum class Type(val id: String) {
        ANY("any"),
        ENCHANTMENT("Enchantment"),
        HERO("Hero"),
        HERO_POWER("Hero Power"),
        MINION("Minion"),
        SPELL("Spell"),
        WEAPON("Weapon")
    }

    enum class Rarity(val id: String) {
        ANY("any"),
        FREE("Free"),
        COMMON("Common"),
        LEGENDARY("Legendary"),
        EPIC("Epic"),
        RARE("Rare")
    }

    enum class PlayerClass(val id: String) {
        ANY("any"),
        NEUTRAL("Neutral"),
        SHAMAN("Shaman"),
        PRIEST("Priest"),
        PALADIN("Paladin"),
        WARRIOR("Warrior"),
        HUNTER("Hunter"),
        DRUID("Druid"),
        WARLOCK("Warlock"),
        MAGE("Mage"),
        ROGUE("Rogue"),
        DREAM("Dream"),
        DEATH_KNIGHT("Death Knight")
    }

    enum class Mechanics(val id: String) {
        ANY("any"),
        TAUNT("Taunt"),
        ONE_TURN_EFFECT("OneTurnEffect"),
        MORPH("Morph"),
        IMMUNE_TO_SPELL_POWER("ImmuneToSpellpower"),
        CHARGE("Charge"),
        BATTLECRY("Battlecry"),
        FREEZE("Freeze"),
        DIVINE_SHIELD("Divine Shield"),
        AURA("Aura"),
        SPELL_DAMAGE("Spell Damage"),
        ADJANCENT_BUFF("AdjacentBuff"),
        WINDFURY("Windfury"),
        ENRAGE("Enrage"),
        SUMMONED("Summoned"),
        SILENCE("Silence"),
        STEALTH("Stealth"),
        COMBO("Combo"),
        OVERLOAD("Overload"),
        SECRET("Secret"),
        DEATHRATTLE("Deathrattle"),
        AFFECTED_BY_SPELL_POWER("AffectedBySpellPower"),
        POISONOUS("Poisonous"),
        AI_NUST_PLAY("AIMustPlay"),
        INVISIBLE_DEATHRATTLE("InvisibleDeathrattle"),
        INSPIRE("Inspire"),
        DISCOVER("Discover"),
        JADE_GOLEM("Jade Golem"),
        ADAPT("Adapt"),
        QUEST("Quest")
    }
}

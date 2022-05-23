package com.kapanen.hearthstoneassessment.data

import androidx.annotation.StringRes
import com.kapanen.hearthstoneassessment.R

enum class CardType(val typeName: String, @StringRes label: Int) {
    BASIC("Basic", R.string.card_type_basic),
    CLASSIC("Classic", R.string.card_type_classic),
    HALL_OF_FAME("Hall of Fame", R.string.card_type_hall_of_fame),
    PROMO("Promo", R.string.card_type_promo),
    NAXXRAMAS("Naxxramas", R.string.card_type_naxxramas),
    GOBLINS_VS_GNOMES("Goblins vs Gnomes", R.string.card_type_goblin_vs_gnomes),
    BLACKROCK_MOUNTAIN("Blackrock Mountain", R.string.card_type_blackrock_mountain),
    THE_GRAND_TOURNAMENT("The Grand Tournament", R.string.card_type_the_grand_tournament),
    THE_LEAGUE_OF_EXPLORERS("The League of Explorers", R.string.card_type_the_league_of_explorers),
    WHISPERS_OF_THE_OLD_GODS(
        "Whispers of the Old Gods",
        R.string.card_type_whispers_of_the_old_gods
    ),
    ONE_NIGHT_IN_KARAZHAN("One Night in Karazhan", R.string.card_type_one_night_in_karazhan),
    MEAN_STREETS_OF_GADGETZAN(
        "Mean Streets of Gadgetzan",
        R.string.card_type_main_streets_of_gadgetzan
    ),
    JOURNEY_TO_UN_QUOTE_GORO("Journey to Un'Goro", R.string.card_type_jorney_to_un_apostrophe_goro),
    TAVERN_BRAWL("Tavern Brawl", R.string.card_type_tavern_brawl),
    HERO_SKINS("Hero Skins", R.string.card_type_hero_skins),
    MISSIONS("Missions", R.string.card_type_missions),
    CREDITS("Credits", R.string.card_type_credits)
}

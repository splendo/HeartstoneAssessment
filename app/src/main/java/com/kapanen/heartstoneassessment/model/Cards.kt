package com.kapanen.heartstoneassessment.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @param basic
 * @param classic
 * @param hallOfFame
 * @param promo
 * @param naxxramas
 * @param goblinsVsGnomes
 * @param blackrockMountain
 * @param theGrandTournament
 * @param theLeagueOfExplorers
 * @param whispersOfTheOldGods
 * @param oneNightInKarazhan
 * @param meanStreetsOfGadgetzan
 * @param journeyToUnQuoteGoro
 * @param tavernBrawl
 * @param heroSkins
 * @param missions
 * @param credits
 */
data class Cards(
    @SerializedName("Basic") val basic: List<Card>? = null,
    @SerializedName("Classic") val classic: List<Card>? = null,
    @SerializedName("Hall of Fame") val hallOfFame: List<Card>? = null,
    @SerializedName("Promo") val promo: List<Card>? = null,
    @SerializedName("Naxxramas") val naxxramas: List<Card>? = null,
    @SerializedName("Goblins vs Gnomes") val goblinsVsGnomes: List<Card>? = null,
    @SerializedName("Blackrock Mountain") val blackrockMountain: List<Card>? = null,
    @SerializedName("The Grand Tournament") val theGrandTournament: List<Card>? = null,
    @SerializedName("The League of Explorers") val theLeagueOfExplorers: List<Card>? = null,
    @SerializedName("Whispers of the Old Gods") val whispersOfTheOldGods: List<Card>? = null,
    @SerializedName("One Night in Karazhan") val oneNightInKarazhan: List<Card>? = null,
    @SerializedName("Mean Streets of Gadgetzan") val meanStreetsOfGadgetzan: List<Card>? = null,
    @SerializedName("Journey to Un'Goro") val journeyToUnQuoteGoro: List<Card>? = null,
    @SerializedName("Tavern Brawl") val tavernBrawl: List<Card>? = null,
    @SerializedName("Hero Skins") val heroSkins: List<Card>? = null,
    @SerializedName("Missions") val missions: List<Card>? = null,
    @SerializedName("Credits") val credits: List<Card>? = null
)

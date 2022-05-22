package com.kapanen.hearthstoneassessment.model

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
    @SerializedName("Basic") val basic: List<BeCard>? = null,
    @SerializedName("Classic") val classic: List<BeCard>? = null,
    @SerializedName("Hall of Fame") val hallOfFame: List<BeCard>? = null,
    @SerializedName("Promo") val promo: List<BeCard>? = null,
    @SerializedName("Naxxramas") val naxxramas: List<BeCard>? = null,
    @SerializedName("Goblins vs Gnomes") val goblinsVsGnomes: List<BeCard>? = null,
    @SerializedName("Blackrock Mountain") val blackrockMountain: List<BeCard>? = null,
    @SerializedName("The Grand Tournament") val theGrandTournament: List<BeCard>? = null,
    @SerializedName("The League of Explorers") val theLeagueOfExplorers: List<BeCard>? = null,
    @SerializedName("Whispers of the Old Gods") val whispersOfTheOldGods: List<BeCard>? = null,
    @SerializedName("One Night in Karazhan") val oneNightInKarazhan: List<BeCard>? = null,
    @SerializedName("Mean Streets of Gadgetzan") val meanStreetsOfGadgetzan: List<BeCard>? = null,
    @SerializedName("Journey to Un'Goro") val journeyToUnQuoteGoro: List<BeCard>? = null,
    @SerializedName("Tavern Brawl") val tavernBrawl: List<BeCard>? = null,
    @SerializedName("Hero Skins") val heroSkins: List<BeCard>? = null,
    @SerializedName("Missions") val missions: List<BeCard>? = null,
    @SerializedName("Credits") val credits: List<BeCard>? = null
)

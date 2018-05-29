package com.nielsmasdorp.heartstone.data.card.model

import com.google.gson.annotations.SerializedName

/**
 * Model for all sets of [JSONCard]s in the JSON file in /assets
 */
data class JSONCardSets(
        @SerializedName("Basic")
        val basicCards: List<JSONCard>,

        @SerializedName("Classic")
        val classicCards: List<JSONCard>,

        @SerializedName("Hall of Fame")
        val hallOfFameCards: List<JSONCard>,

        @SerializedName("Naxxramas")
        val naxxramasCards: List<JSONCard>,

        @SerializedName("Goblins vs Gnomes")
        val goblinsVsGnomesCards: List<JSONCard>,

        @SerializedName("Blackrock Mountain")
        val blackRockMountainCards: List<JSONCard>,

        @SerializedName("The Grand Tournament")
        val grandTournamentCards: List<JSONCard>,

        @SerializedName("The League of Explorers")
        val leagueOfExplorersCards: List<JSONCard>,

        @SerializedName("Whispers of the Old Gods")
        val whispersOfTheOldGodsCards: List<JSONCard>,

        @SerializedName("One Night in Karazhan")
        val karazhanCards: List<JSONCard>,

        @SerializedName("Mean Streets of Gadgetzan")
        val gadgetzanCards: List<JSONCard>,

        @SerializedName("Journey to Un'Goro")
        val journeyUnGoroCards: List<JSONCard>,

        @SerializedName("Tavern Brawl")
        val tavernBrawlCards: List<JSONCard>,

        @SerializedName("Hero Skins")
        val heroSkinsCards: List<JSONCard>,

        @SerializedName("Missions")
        val missionsCards: List<JSONCard>,

        @SerializedName("Credits")
        val creditsCards: List<JSONCard>
)
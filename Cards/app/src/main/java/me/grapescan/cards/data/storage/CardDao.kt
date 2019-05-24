package me.grapescan.cards.data.storage

import com.google.gson.annotations.SerializedName

data class CardDao(
    @SerializedName("cardId") val cardId: String,
    @SerializedName("name") val name: String,
    @SerializedName("cardSet") val cardSet: String,
    @SerializedName("type") val type: String,
    @SerializedName("rarity") val rarity: String,
    @SerializedName("cost") val cost: Int,
    @SerializedName("attack") val attack: Int,
    @SerializedName("health") val health: Int,
    @SerializedName("text") val text: String,
    @SerializedName("flavor") val flavor: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("collectible") val collectible: Boolean,
    @SerializedName("playerClass") val playerClass: String,
    @SerializedName("howToGetGold") val howToGetGold: String,
    @SerializedName("img") val img: String? = null,
    @SerializedName("imgGold") val imgGold: String,
    @SerializedName("locale") val locale: String,
    @SerializedName("mechanics") val mechanics: List<MechanicsDao> = emptyList()
) {
    data class MechanicsDao(
        @SerializedName("name") val name: String
    )
}

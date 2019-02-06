package laurens.hearthstoneassessment.model

import android.arch.lifecycle.LiveData

data class CardModel(
    val status: LiveData<CardStatus>,
    val card: Card
)
package laurens.hearthstoneassessment.dataaccess.dao

import android.arch.paging.DataSource
import laurens.hearthstoneassessment.dataaccess.SortDirection
import laurens.hearthstoneassessment.model.Card


interface CardDao {

    fun loadCards(
        type: String? = null,
        cardClass: String? = null,
        mechanic: String? = null,
        rarity: String? = null,
        sortDirection: SortDirection = SortDirection.NOT_SORTED
    ): DataSource.Factory<Int, Card>

    fun insert(card: Card)
}


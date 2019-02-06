package laurens.hearthstoneassessment.dataaccess.room

import android.arch.persistence.db.SimpleSQLiteQuery
import laurens.hearthstoneassessment.dataaccess.SortDirection

object QueryCreator {

    fun createQuery(
        type: String? = null,
        cardClass: String? = null,
        mechanic: String? = null,
        rarity: String? = null,
        sortDirection: SortDirection
    ) = SimpleSQLiteQuery(
        """
        SELECT * FROM card
        ${joinClause("card_class", "value", cardClass != null)}
        ${joinClause("card_mechanic", "name", mechanic != null)}
        ${whereClause(type, rarity)}
        ${sortClause(sortDirection)}
    """.trimIndent(),
        listOfNotNull(cardClass, mechanic, type, rarity).toTypedArray()
    )


    private fun joinClause(tableName: String, valueName: String, includeClause: Boolean) = if (includeClause) """
        INNER JOIN $tableName
        ON $tableName.cardId = card.cardId
        AND $tableName.$valueName = ?
    """.trimIndent() else ""

    private fun whereClause(type: String?, rarity: String?): String {
        return listOfNotNull(
            type?.let { "type = ?" },
            rarity?.let { "rarity = ?" }
        )
            .joinToString(separator = " AND ")
            .let { if (it.isNotBlank()) "WHERE $it" else "" }
    }

    private fun sortClause(sortDirection: SortDirection): String {
        val baseSort = "ORDER BY name"
        return when (sortDirection) {
            SortDirection.NOT_SORTED -> ""
            SortDirection.ASCENDING -> "$baseSort ASC"
            SortDirection.DESCENDING -> "$baseSort DESC"
        }
    }
}

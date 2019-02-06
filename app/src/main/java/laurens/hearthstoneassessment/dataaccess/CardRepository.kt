package laurens.hearthstoneassessment.dataaccess

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import laurens.hearthstoneassessment.dataaccess.dao.CardDao
import laurens.hearthstoneassessment.dataaccess.dao.CardStatusDao
import laurens.hearthstoneassessment.model.CardModel
import laurens.hearthstoneassessment.model.CardStatus
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepository @Inject constructor(private val cardDao: CardDao, private val cardStatusDao: CardStatusDao) :
    AnkoLogger {

    private val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(true)
        .build()

    private var cardCache: LiveData<PagedList<CardModel>>? = null

    var traverser: PagedListTraverser<CardModel>? = null

    fun selectItem(index: Int) {
        traverser = cardCache?.let { PagedListTraverser(it, index) }
    }

    fun setCurrentFavoriteStatus(favorite: Boolean) {
        traverser?.current()?.also {
            cardStatusDao.updateOrInsert(CardStatus(it.card.cardId, favorite))
        }
    }

    fun cards(
        type: String? = null,
        cardClass: String? = null,
        mechanic: String? = null,
        rarity: String? = null,
        sortDirection: SortDirection = SortDirection.NOT_SORTED
    ): LiveData<PagedList<CardModel>> {
        val cardDataSource = cardDao.loadCards(type, cardClass, mechanic, rarity, sortDirection)
            .map { CardModel(cardStatusDao.getCardStatusLive(it.cardId), it) }
        val pagedList = LivePagedListBuilder(cardDataSource, config).build()
        cardCache = pagedList
        return pagedList
    }
}


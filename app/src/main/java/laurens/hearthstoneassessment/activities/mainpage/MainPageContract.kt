package laurens.hearthstoneassessment.activities.mainpage

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import laurens.hearthstoneassessment.model.CardModel
import laurens.hearthstoneassessment.mvp.BasePresenter

interface MainPageContract {
    interface View {
        fun setCards(cards: LiveData<PagedList<CardModel>>)
        fun goToDetailActivity()
    }

    interface Presenter : BasePresenter<MainPageContract.View> {
        fun onCardTap(index: Int)
    }
}
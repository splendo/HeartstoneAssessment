package laurens.hearthstoneassessment.activities.detailpage

import laurens.hearthstoneassessment.model.CardModel
import laurens.hearthstoneassessment.mvp.BasePresenter

interface DetailPageContract {
    interface View {
        fun setContent(card: CardModel)
    }

    interface Presenter : BasePresenter<DetailPageContract.View> {
        fun setFavoriteStatus(favorite: Boolean)
        fun onSwipeLeft()
        fun onSwipeRight()
    }
}
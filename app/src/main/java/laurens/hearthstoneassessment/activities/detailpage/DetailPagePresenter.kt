package laurens.hearthstoneassessment.activities.detailpage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import laurens.hearthstoneassessment.dataaccess.CardRepository
import laurens.hearthstoneassessment.model.CardModel

class DetailPagePresenter(private val cardRepository: CardRepository) : DetailPageContract.Presenter {

    override lateinit var view: DetailPageContract.View

    override fun setFavoriteStatus(favorite: Boolean) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                cardRepository.setCurrentFavoriteStatus(favorite)
            }
        }
    }

    override fun onSwipeLeft() {
        updateView { cardRepository.traverser?.previous() }
    }

    override fun onSwipeRight() {
        updateView { cardRepository.traverser?.next() }
    }

    override fun onAttach() {
        updateView { cardRepository.traverser?.current() }
    }

    private fun updateView(update: () -> CardModel?) {
        update()?.also { view.setContent(it) }
    }

    override fun onDetach() {

    }
}
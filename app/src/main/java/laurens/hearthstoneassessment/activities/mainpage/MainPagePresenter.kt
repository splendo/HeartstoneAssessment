package laurens.hearthstoneassessment.activities.mainpage

import laurens.hearthstoneassessment.dataaccess.CardRepository

class MainPagePresenter(
    private val cardRepository: CardRepository
) : MainPageContract.Presenter {

    override lateinit var view: MainPageContract.View

    override fun onCardTap(index: Int) {
        cardRepository.selectItem(index)
        view.goToDetailActivity()
    }

    override fun onAttach() {
        val cards = cardRepository.cards(
            mechanic = "Deathrattle",
            rarity = "Legendary"
        )
        view.setCards(cards)
    }

    override fun onDetach() {
    }
}
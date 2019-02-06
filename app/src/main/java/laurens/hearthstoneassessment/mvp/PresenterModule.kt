package laurens.hearthstoneassessment.mvp

import dagger.Module
import dagger.Provides
import laurens.hearthstoneassessment.dataaccess.CardRepository
import laurens.hearthstoneassessment.dataaccess.DataAccessModule
import laurens.hearthstoneassessment.activities.detailpage.DetailPageContract
import laurens.hearthstoneassessment.activities.detailpage.DetailPagePresenter
import laurens.hearthstoneassessment.activities.mainpage.MainPageContract
import laurens.hearthstoneassessment.activities.mainpage.MainPagePresenter

@Module(includes = [DataAccessModule::class])
class PresenterModule {

    @Provides
    fun provideDetailPagePresenter(cardRepository: CardRepository): DetailPageContract.Presenter =
        DetailPagePresenter(cardRepository)

    @Provides
    fun provideMainPagePresenter(cardRepository: CardRepository): MainPageContract.Presenter =
        MainPagePresenter(cardRepository)
}
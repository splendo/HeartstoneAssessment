package laurens.hearthstoneassessment.application

import dagger.Component
import laurens.hearthstoneassessment.activities.detailpage.DetailPageActivity
import laurens.hearthstoneassessment.activities.mainpage.MainActivity
import laurens.hearthstoneassessment.mvp.PresenterModule
import javax.inject.Singleton

@Singleton
@Component(modules = [PresenterModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: DetailPageActivity)
}
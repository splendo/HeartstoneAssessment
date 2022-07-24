package laurens.hearthstoneassessment.application

import android.app.Application
import laurens.hearthstoneassessment.dataaccess.DataAccessModule

class HearthstoneAssessment : Application() {
    lateinit var mainComponent: MainComponent
        private set

    override fun onCreate() {
        super.onCreate()
        mainComponent = DaggerMainComponent.builder()
            .dataAccessModule(DataAccessModule(applicationContext))
            .build()
    }
}

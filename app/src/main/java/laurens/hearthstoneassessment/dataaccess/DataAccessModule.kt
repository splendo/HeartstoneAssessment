package laurens.hearthstoneassessment.dataaccess

import android.content.Context
import dagger.Module
import dagger.Provides
import laurens.hearthstoneassessment.dataaccess.dao.CardDao
import laurens.hearthstoneassessment.dataaccess.dao.CardStatusDao
import laurens.hearthstoneassessment.dataaccess.room.LocalCardDatabase
import laurens.hearthstoneassessment.dataaccess.room.RoomCardDao

@Module
class DataAccessModule(private val context: Context) {

    private val localCardDatabase by lazy { LocalCardDatabase.create(context) }

    @Provides
    fun provideCardDao(): CardDao =
        RoomCardDao(localCardDatabase.cardDao())

    @Provides
    fun provideCardStatusDao(): CardStatusDao = localCardDatabase.cardStatusDao()
}
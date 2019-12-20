package com.krayem.hearthstone

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.krayem.hearthstone.db.DatabaseManager
import com.krayem.hearthstone.db.DefaultDatabaseManager
import com.krayem.hearthstone.model.MyObjectBox
import com.krayem.hearthstone.objectbox.ObjectBox
import io.objectbox.BoxStore
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTester {

    lateinit var databaseManager: DatabaseManager

    @BeforeClass
    fun setupDatabaseManager(){
        ObjectBox.init(InstrumentationRegistry.getInstrumentation().targetContext)

        databaseManager = DefaultDatabaseManager()

    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        databaseManager.populateDb()
        Assert.assertEquals(databaseManager.isEmpty(),false)
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        Assert.assertEquals("com.krayem.hearthstone", appContext.packageName)
    }
}
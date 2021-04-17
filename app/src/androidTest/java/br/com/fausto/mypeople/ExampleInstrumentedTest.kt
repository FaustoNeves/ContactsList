package br.com.fausto.mypeople

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.fausto.mypeople.database.subscriber.SubscriberDatabase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("br.com.fausto.mypeople", appContext.packageName)
    }

    private lateinit var subscriberDB: SubscriberDatabase

    @Before
    fun openDB() {
        subscriberDB = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            SubscriberDatabase::class.java
        ).build()
    }
}
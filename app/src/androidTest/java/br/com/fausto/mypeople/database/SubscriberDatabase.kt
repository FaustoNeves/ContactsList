package br.com.fausto.mypeople.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.fausto.mypeople.database.subscriber.Subscriber
import br.com.fausto.mypeople.database.subscriber.SubscriberDAO
import br.com.fausto.mypeople.database.subscriber.SubscriberDatabase
import br.com.fausto.mypeople.repository.subscriber.RSubscriber
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SubscriberDatabase {

    private lateinit var subscriberDB: SubscriberDAO
    lateinit var repository: RSubscriber

    @Before
    fun openDB() {
        subscriberDB =
            SubscriberDatabase.getInstance(InstrumentationRegistry.getInstrumentation().targetContext).subscriberDAO
        repository = RSubscriber(subscriberDB)
    }

//    @After
//    fun endDB() {
//        subscriberDB.close()
//    }

    @Test
    fun insertData() = runBlocking {
        repository.deleteAll()
        val subscriber = Subscriber(5, "name", "email", "123")
        repository.insert(subscriber)
        val newSubscriber = repository.searchById(5)
        assertThat(subscriber.name, equalTo(newSubscriber.name))

    }

//    @Test
//    fun getBufferoosRetrievesData() {
//        val cachedBufferoos = BufferooFactory.makeCachedBufferooList(5)
//
//        cachedBufferoos.forEach {
//            bufferoosDatabase.cachedBufferooDao().insertBufferoo(it)
//        }
//
//        val retrievedBufferoos = bufferoosDatabase.cachedBufferooDao().getBufferoos()
//        assert(retrievedBufferoos == cachedBufferoos.sortedWith(compareBy({ it.id }, { it.id })))
//    }
//
//    @Test
//    fun clearBufferoosClearsData() {
//        val cachedBufferoo = BufferooFactory.makeCachedBufferoo()
//        bufferoosDatabase.cachedBufferooDao().insertBufferoo(cachedBufferoo)
//
//        bufferoosDatabase.cachedBufferooDao().clearBufferoos()
//        assert(bufferoosDatabase.cachedBufferooDao().getBufferoos().isEmpty())
//    }
}
package br.com.fausto.mypeople.database

import br.com.fausto.mypeople.repository.SubscriberRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
class SubscriberDatabase {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var contactsListDatabase: ContactsListDatabase

    private lateinit var subscriberDB: SubscriberDAO
    lateinit var repository: SubscriberRepository

    @Before
    fun openDB() {
        hiltRule.inject()
        subscriberDB = contactsListDatabase.subscriberDAO
        repository = SubscriberRepository(subscriberDB)
        runBlocking {
            repository.deleteAll()
        }
    }

    @After
    fun closeDB() {
        contactsListDatabase.close()
    }

    @Test
    fun insertAndSearchById() = runBlocking {
        val subscriber = Subscriber(5, "Ann", "ann@hotmail.com", "123")
        repository.insert(subscriber)
        val newSubscriber = repository.searchById(5)
        assertThat(subscriber.name, equalTo(newSubscriber.name))
    }

    @Test
    fun insertAndUpdate() = runBlocking {
        val subscriber = Subscriber(3, "Abel", "abel@gmail.com", "456")
        repository.insert(subscriber)
        subscriber.phoneNumber = "789"
        repository.update(subscriber)
        val newSubscriber = repository.searchById(3)
        assertThat(subscriber.phoneNumber, equalTo(newSubscriber.phoneNumber))
    }

    @Test
    fun inserAndDelete() = runBlocking {
        val subscriber = Subscriber(6, "Halo", "halo@outlook.com", "246")
        repository.insert(subscriber)
        val allSubscribers = repository.subscribers
        assertNull(allSubscribers.value)
    }

    @Test
    fun getAllSubscribers() = runBlocking {
        val subscriber = Subscriber(10, "Juan", "juan@hotmail.com", "123")
        val subscriber2 = Subscriber(15, "Camila", "camila@hotmail.com", "456")
        val subscriber3 = Subscriber(20, "Mona", "mona@hotmail.com", "172")
        val subscriber4 = Subscriber(25, "Rafael", "rafael@hotmail.com", "987")
        repository.insert(subscriber)
        repository.insert(subscriber2)
        repository.insert(subscriber3)
        repository.insert(subscriber4)
        val allSubscribers = repository.subscribers
        assertNotNull(allSubscribers)
    }
//    @Test
//    fun insertAndDelete() = runBlocking {
//
//    }

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
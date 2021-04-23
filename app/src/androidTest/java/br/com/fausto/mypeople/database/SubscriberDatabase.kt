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
    @Named("test_subscriber_data_base")
    lateinit var contactsListDatabase: ContactsListDatabase

    private lateinit var subscriberDB: SubscriberDAO
    lateinit var repository: SubscriberRepository

    @Before
    fun openDB() {
        hiltRule.inject()
        subscriberDB = contactsListDatabase.subscriberDAO
        repository = SubscriberRepository(subscriberDB)
        runBlocking {
//            repository.deleteAll()
            subscriberDB.deleteAllSubscribers()
        }
    }

    @After
    fun closeDB() {
        contactsListDatabase.close()
    }

    @Test
    fun insertAndSearchById() = runBlocking {
        val subscriber = Subscriber(5, "Ann", "ann@hotmail.com", "123")
//        repository.insert(subscriber)
        subscriberDB.insertSubscriber(subscriber)
        val newSubscriber = repository.searchById(5)
        assertThat(subscriber.name, equalTo(newSubscriber.name))
    }

    @Test
    fun insertAndUpdate() = runBlocking {
        val subscriber = Subscriber(3, "Abel", "abel@gmail.com", "456")
//        repository.insert(subscriber)
        subscriberDB.insertSubscriber(subscriber)
        subscriber.phoneNumber = "789"
//        repository.update(subscriber)
        subscriberDB.updateSubscriber(subscriber)
        val newSubscriber = repository.searchById(3)
        assertThat(subscriber.phoneNumber, equalTo(newSubscriber.phoneNumber))
    }

    @Test
    fun inserAndDelete() = runBlocking {
        val subscriber = Subscriber(6, "Halo", "halo@outlook.com", "246")
//        repository.insert(subscriber)
        subscriberDB.insertSubscriber(subscriber)
//        val allSubscribers = repository.subscribers
        val allSubscribers = subscriberDB.getAllSubscribers()
        assertNull(allSubscribers.value)
    }

    @Test
    fun getAllSubscribers() = runBlocking {
        val subscriber = Subscriber(10, "Juan", "juan@hotmail.com", "123")
        val subscriber2 = Subscriber(15, "Camila", "camila@hotmail.com", "456")
        val subscriber3 = Subscriber(20, "Mona", "mona@hotmail.com", "172")
        val subscriber4 = Subscriber(25, "Rafael", "rafael@hotmail.com", "987")
//        repository.insert(subscriber)
//        repository.insert(subscriber2)
//        repository.insert(subscriber3)
//        repository.insert(subscriber4)
        subscriberDB.insertSubscriber(subscriber)
        subscriberDB.insertSubscriber(subscriber2)
        subscriberDB.insertSubscriber(subscriber3)
        subscriberDB.insertSubscriber(subscriber4)
//        val allSubscribers = repository.subscribers
        val allSubscribers = subscriberDB.getAllSubscribers()
        assertNotNull(allSubscribers)
    }
}
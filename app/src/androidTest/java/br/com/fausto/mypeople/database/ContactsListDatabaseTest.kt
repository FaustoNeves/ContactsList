package br.com.fausto.mypeople.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fausto.mypeople.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
class ContactsListDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_subscriber_data_base")
    lateinit var contactsListDatabase: ContactsListDatabase

    private lateinit var subscriberDAO: SubscriberDAO

    @Before
    fun openDB() {
        hiltRule.inject()
        subscriberDAO = contactsListDatabase.subscriberDAO()
        runBlocking {
            subscriberDAO.deleteAllSubscribers()
        }
    }

    @After
    fun closeDB() {
        contactsListDatabase.close()
    }

    @Test
    fun insertAndSearchById() = runBlocking {
        val subscriber = Subscriber(5, "Ann", "ann@hotmail.com", "123")
        subscriberDAO.insertSubscriber(subscriber)
        val newSubscriber = subscriberDAO.searchById(subscriber.id)
        assertThat(subscriber.name).isEqualTo(newSubscriber.name)
    }

    @Test
    fun insertAndUpdate() = runBlocking {
        val subscriber = Subscriber(3, "Abel", "abel@gmail.com", "456")
        subscriberDAO.insertSubscriber(subscriber)
        subscriber.phoneNumber = "789"
        subscriberDAO.updateSubscriber(subscriber)
        val newSubscriber = subscriberDAO.searchById(subscriber.id)
        assertThat(subscriber.phoneNumber).isEqualTo(newSubscriber.phoneNumber)
    }

    @Test
    fun insertAndDelete() = runBlocking {
        val subscriber = Subscriber(6, "Halo", "halo@outlook.com", "246")
        subscriberDAO.insertSubscriber(subscriber)
        subscriberDAO.deleteSubscriber(subscriber)
        val allSubscribers = subscriberDAO.getAllSubscribers().getOrAwaitValue()
        assertFalse(allSubscribers.contains(subscriber))
        assertThat(allSubscribers).doesNotContain(subscriber)
    }

    @Test
    fun getAllSubscribers() = runBlocking {
        val subscriber = Subscriber(10, "Juan", "juan@hotmail.com", "123")
        val subscriber2 = Subscriber(15, "Camila", "camila@hotmail.com", "456")
        val subscriber3 = Subscriber(20, "Mona", "mona@hotmail.com", "172")
        val subscriber4 = Subscriber(25, "Rafael", "rafael@hotmail.com", "987")
        subscriberDAO.insertSubscriber(subscriber)
        subscriberDAO.insertSubscriber(subscriber2)
        subscriberDAO.insertSubscriber(subscriber3)
        subscriberDAO.insertSubscriber(subscriber4)
        val allSubscribers = subscriberDAO.getAllSubscribers().getOrAwaitValue()
        val subscribersConfirmList = listOf(subscriber, subscriber2, subscriber3, subscriber4)
        assert(allSubscribers.containsAll(subscribersConfirmList))
    }
}
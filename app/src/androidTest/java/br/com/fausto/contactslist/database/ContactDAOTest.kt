package br.com.fausto.contactslist.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fausto.contactslist.getOrAwaitValue
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
class ContactDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_contact_database")
    lateinit var contactsListDatabase: ContactsListDatabase
    private lateinit var contactDAO: ContactDAO

    @Before
    fun openDB() {
        hiltRule.inject()
        contactDAO = contactsListDatabase.ContactDAO()
    }

    @After
    fun closeDB() {
        contactsListDatabase.close()
    }

    @Test
    fun insertAndSearchById() = runBlocking {
        val contact = Contact(5, "Ann", "ann@hotmail.com", "123")
        contactDAO.insertContact(contact)
        val newContact = contactDAO.searchById(contact.id)
        assertThat(contact.name).isEqualTo(newContact.name)
    }

    @Test
    fun insertAndUpdate() = runBlocking {
        val contact = Contact(3, "Abel", "abel@gmail.com", "456")
        contactDAO.insertContact(contact)
        contact.phoneNumber = "789"
        contactDAO.updateContact(contact)
        val newContact = contactDAO.searchById(contact.id)
        assertThat(contact.phoneNumber).isEqualTo(newContact.phoneNumber)
    }

    @Test
    fun insertAndDelete() = runBlocking {
        val contact = Contact(6, "Halo", "halo@outlook.com", "246")
        contactDAO.insertContact(contact)
        contactDAO.deleteContact(contact)
        val allContacts = contactDAO.getAllContacts().getOrAwaitValue()
        assertFalse(allContacts.contains(contact))
        assertThat(allContacts).doesNotContain(contact)
    }

    @Test
    fun getAllContacts() = runBlocking {
        val contact = Contact(10, "Juan", "juan@hotmail.com", "123")
        val contact2 = Contact(15, "Camila", "camila@hotmail.com", "456")
        val contact3 = Contact(20, "Mona", "mona@hotmail.com", "172")
        val contact4 = Contact(25, "Rafael", "rafael@hotmail.com", "987")
        contactDAO.insertContact(contact)
        contactDAO.insertContact(contact2)
        contactDAO.insertContact(contact3)
        contactDAO.insertContact(contact4)
        val allContacts = contactDAO.getAllContacts().getOrAwaitValue()
        val contactsConfirmList = listOf(contact, contact2, contact3, contact4)
        assert(allContacts.containsAll(contactsConfirmList))
    }
}
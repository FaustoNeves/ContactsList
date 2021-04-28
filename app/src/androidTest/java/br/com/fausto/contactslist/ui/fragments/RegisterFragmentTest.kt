package br.com.fausto.contactslist.ui.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import br.com.fausto.contactslist.R
import br.com.fausto.contactslist.database.ContactDAO
import br.com.fausto.contactslist.database.ContactsListDatabase
import br.com.fausto.contactslist.launchFragmentInHiltContainer
import br.com.fausto.contactslist.ui.utils.ToastMatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
class RegisterFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var contactsListDatabase: ContactsListDatabase
    private lateinit var contactDAO: ContactDAO

    @Before
    fun setup() {
        hiltRule.inject()
        contactDAO = contactsListDatabase.ContactDAO()
    }

    @After
    fun closeDB() {
        runBlocking { contactDAO.deleteAllContacts() }
        contactsListDatabase.close()
    }

    @Test
    fun registerContactWithAllCredentials() {
        launchFragmentInHiltContainer<RegisterFragment> {}
        onView(withId(R.id.textInputName)).perform(typeText("Donatelo"))
        onView(withId(R.id.textInputEmail)).perform(typeText("donatelo@hotmail.com"))
        onView(withId(R.id.textInputCel)).perform(typeText("123456"))
        onView(withId(R.id.save_update_button)).perform(click())
        onView(withText("Contact registered")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun registerContactWithNameEmail() {
        launchFragmentInHiltContainer<RegisterFragment> {}
        onView(withId(R.id.textInputName)).perform(typeText("Rafael"))
        onView(withId(R.id.textInputEmail)).perform(typeText("rafael@hotmail.com"))
        onView(withId(R.id.textInputCel)).perform(typeText(""))
        onView(withId(R.id.save_update_button)).perform(click())
        onView(withText("Contact registered")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun registerContactWithNamePhoneNumber() {
        launchFragmentInHiltContainer<RegisterFragment> {}
        onView(withId(R.id.textInputName)).perform(typeText("Michelangelo"))
        onView(withId(R.id.textInputEmail)).perform(typeText(""))
        onView(withId(R.id.textInputCel)).perform(typeText("123456"))
        onView(withId(R.id.save_update_button)).perform(click())
        onView(withText("Contact registered")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun registerContactWithEmptyName() {
        launchFragmentInHiltContainer<RegisterFragment> {}
        onView(withId(R.id.textInputName)).perform(typeText(""))
        onView(withId(R.id.textInputEmail)).perform(typeText("splinter@hotmail.com"))
        onView(withId(R.id.textInputCel)).perform(typeText("123456"))
        onView(withId(R.id.save_update_button)).perform(click())
        onView(withText("Name is required")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun registerContactWithEmptyEmailPhoneNumber() {
        launchFragmentInHiltContainer<RegisterFragment> {}
        onView(withId(R.id.textInputName)).perform(typeText("Leonardo"))
        onView(withId(R.id.textInputEmail)).perform(typeText(""))
        onView(withId(R.id.textInputCel)).perform(typeText(""))
        onView(withId(R.id.save_update_button)).perform(click())
        onView(withText("Please add email or phone number")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    /**Couldn't test upgrade functionalities
    @Test
    fun updateContactWithAllCredentials() {
    val contact = Contact(15, "Shaokan", "shaokan@gmail.com", "55555")
    runBlocking {
    contactDAO.insertContact(contact)
    }

    launchFragmentInHiltContainer<RegisterFragment> {}

    onView(withId(R.id.textInputName)).perform(typeText(contact.name))
    onView(withId(R.id.textInputEmail)).perform(typeText(contact.email))
    onView(withId(R.id.textInputCel)).perform(typeText(contact.phoneNumber))
    onView(withId(R.id.save_update_button)).perform(click())
    onView(withText("Contact updated")).inRoot(ToastMatcher())
    .check(matches(isDisplayed()))
    runBlocking { delay(2000) }
    }
     */
}
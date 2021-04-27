package br.com.fausto.contactslist.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
class RegisterFragmentTest {

//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_contact_database")
    lateinit var contactsListDatabase: ContactsListDatabase
    private lateinit var contactDAO: ContactDAO

    @Before
    fun setup() = hiltRule.inject()

    @After
    fun closeDB() {
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
        onView(withId(R.id.textInputName)).perform(typeText("Donatelo"))
        onView(withId(R.id.textInputEmail)).perform(typeText("donatelo@hotmail.com"))
        onView(withId(R.id.textInputCel)).perform(typeText(""))
        onView(withId(R.id.save_update_button)).perform(click())
        onView(withText("Contact registered")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun registerContactWithAllNamePhoneNumber() {
        launchFragmentInHiltContainer<RegisterFragment> {}
        onView(withId(R.id.textInputName)).perform(typeText("Donatelo"))
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
        onView(withId(R.id.textInputEmail)).perform(typeText("donatelo@hotmail.com"))
        onView(withId(R.id.textInputCel)).perform(typeText("123456"))
        onView(withId(R.id.save_update_button)).perform(click())
        onView(withText("Name is required")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun registerContactWithEmptyEmailPhoneNumber() {
        launchFragmentInHiltContainer<RegisterFragment> {}
        onView(withId(R.id.textInputName)).perform(typeText("Donatelo"))
        onView(withId(R.id.textInputEmail)).perform(typeText(""))
        onView(withId(R.id.textInputCel)).perform(typeText(""))
        onView(withId(R.id.save_update_button)).perform(click())
        onView(withText("Please add email or phone number")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

//    @Test
//    fun updateContactWithAllCredentials() {
//
//    }
}
package br.com.fausto.contactslist.ui.fragments

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import br.com.fausto.contactslist.R
import br.com.fausto.contactslist.database.ContactDAO
import br.com.fausto.contactslist.database.ContactsListDatabase
import br.com.fausto.contactslist.launchFragmentInHiltContainer
import br.com.fausto.contactslist.ui.activities.MainActivity
import br.com.fausto.contactslist.ui.utils.ToastMatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var contactsListDatabase: ContactsListDatabase
    private lateinit var contactDAO: ContactDAO

    @After
    fun closeDB() {
        runBlocking { contactDAO.deleteAllContacts() }
        contactsListDatabase.close()
        Intents.release()
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setup() {
        hiltRule.inject()
        Intents.init()
        contactDAO = contactsListDatabase.ContactDAO()
        //This code above is here because we'll make use of this contact in every test
        launchFragmentInHiltContainer<RegisterFragment> {}
        onView(withId(R.id.textInputName)).perform(typeText("Finley"))
        onView(withId(R.id.textInputEmail)).perform(typeText("finley@gmail.com"))
        onView(withId(R.id.textInputCel)).perform(typeText("123456"))
        onView(withId(R.id.save_update_button)).perform(click())
    }

    @Test
    fun callLayoutTest() {
        launchFragmentInHiltContainer<HomeFragment> {}
        onView(withId(R.id.contact_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.call_layout)).perform(click())
        intended(allOf(hasAction(Intent.ACTION_DIAL)));
    }

    @Test
    fun emailLayoutTest() {
        launchFragmentInHiltContainer<HomeFragment> {}
        onView(withId(R.id.contact_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.email_layout)).perform(click())
        intended(allOf(hasAction(Intent.ACTION_CHOOSER)));
    }

    @Test
    fun editLayoutTest() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.contact_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.edit_layout)).inRoot(isDialog()).check(matches(isDisplayed())).perform(
            click()
        )
        verify(navController).navigate(HomeFragmentDirections.actionHomeFragmentToRegisterFragment())
    }

    @Test
    fun deleteLayoutTest() {
        launchFragmentInHiltContainer<RegisterFragment> {}
        launchFragmentInHiltContainer<HomeFragment> {}
        onView(withId(R.id.contact_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.exclude_layout)).perform(click())
        onView(withText("Successfully deleted")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun closeLayoutTest() {
        launchFragmentInHiltContainer<HomeFragment> {}
        onView(withId(R.id.contact_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.cancel_layout)).perform(click())
        onView(withId(R.id.cancel_layout)).check(doesNotExist())
    }
}
package br.com.fausto.contactslist.ui.fragments

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() = hiltRule.inject()
//
//    @Test
//    fun editContactNavigation() {
//        val navController = mock(NavController::class.java)
//        launchFragmentInHiltContainer<HomeFragment> {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//        onView(withId(R.id.contact_recycler_view)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
//        onView(withId(R.id.edit_layout)).inRoot(isDialog()).check(matches(isDisplayed()))
//
//    }
}
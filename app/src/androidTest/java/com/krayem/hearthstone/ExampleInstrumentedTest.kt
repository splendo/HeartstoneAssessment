package com.krayem.hearthstone

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.krayem.hearthstone.ui.main.grid.recyclerview.CardViewHolder
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)





    @Test
    fun testSorting() {

        onView(withId(R.id.fragment_main_viewpager)).perform(swipeLeft())


        // click the filter button
        onView(withId(R.id.filters_button)).perform(click())

        // check if the filters fragment is visible
        onView(withId(R.id.filters_root)).check(matches(isDisplayed()))

        // scroll
        onView(withId(R.id.filters_fragment_scrollview)).perform(swipeUp())

        // sort by alphabetical
        onView(withId(R.id.sort_alphabetic_rb)).perform(click())

        // apply filters
        onView(withId(R.id.apply_filters_button)).perform(click())

        // check if the main fragment is visible
        onView(withId(R.id.fragment_main_root)).check(matches(isDisplayed()))

        // click on the first item ("Acidic Swap Ooze" Card)
        // todo: I am sure testing on the data in this way is not the best approach since changing the data source will cause the test to fail
        onView(allOf(withId(R.id.list_fragment_cards_rv), isDisplayed())).perform(actionOnItemAtPosition<CardViewHolder>(0, click()))

        // check name text view, other card details view can be tested here
        onView(allOf(withId(R.id.card_details_section_value), withText("Acidic Swamp Ooze"))).check(matches(isDisplayed()))

        // back to the cards list
        onView(isRoot()).perform(pressBack())

        // click the filter button
        onView(withId(R.id.filters_button))
            .perform(click())

        // check if the filters fragment is visible
        onView(withId(R.id.filters_root)).check(matches(isDisplayed()))

        // scroll
        onView(withId(R.id.filters_fragment_scrollview)).perform(swipeUp())

        // sort by rarity
        onView(withId(R.id.sort_rarity_rb)).perform(click())

        // apply filters
        onView(withId(R.id.apply_filters_button)).perform(click())

        // check if the main fragment is visible
        onView(withId(R.id.fragment_main_root)).check(matches(isDisplayed()))

        // click on the first item ("Anduin Wrynn" Card since we sorted by rarity)
        onView(allOf(withId(R.id.list_fragment_cards_rv), isDisplayed())).perform(actionOnItemAtPosition<CardViewHolder>(0, click()))


        onView(allOf(withId(R.id.card_details_section_value), withText("Anduin Wrynn"))).check(matches(isDisplayed()))
    }


    @Test
    fun testFavourites(){

        onView(withId(R.id.fragment_main_viewpager)).perform(swipeLeft())

        Thread.sleep(1000)


        onView(allOf(withId(R.id.list_fragment_cards_rv), isDisplayed())).perform(actionOnItemAtPosition<CardViewHolder>(0, click()))

        onView(withId(R.id.card_details_favourite_fab)).perform(click())

        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.fragment_main_viewpager)).perform(swipeRight())

        Thread.sleep(1000)

        assertListCount(1)

        onView(withId(R.id.fragment_main_viewpager)).perform(swipeLeft())

        Thread.sleep(1000)


        onView(allOf(withId(R.id.list_fragment_cards_rv), isDisplayed())).perform(actionOnItemAtPosition<CardViewHolder>(0, click()))
        onView(withId(R.id.card_details_favourite_fab)).perform(click())

        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.fragment_main_viewpager)).perform(swipeRight())

        Thread.sleep(1000)
        onView(allOf(withId(R.id.list_fragment_empty_label), isDisplayed())).check(matches(withText(R.string.no_cards_here)))
    }


    @Test
    fun testFilters(){


        // click the filter button
        onView(withId(R.id.filters_button)).perform(click())

        // check if the filters fragment is visible
        onView(withId(R.id.filters_root)).check(matches(isDisplayed()))

        onView(withId(R.id.mechanic_tv)).perform(click())
        onData(equalTo("Adapt")).inRoot(RootMatchers.isPlatformPopup()).perform(click())
//        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Adapt")))
//            .perform(click())
        Thread.sleep(1000)


        onView(withId(R.id.filters_fragment_scrollview)).perform(swipeUp())
        Thread.sleep(1000)
        onView(withId(R.id.sort_alphabetic_rb)).perform(click())

        onView(withId(R.id.apply_filters_button)).perform(click())
        onView(withId(R.id.fragment_main_root)).check(matches(isDisplayed()))

        onView(withId(R.id.fragment_main_tablayout)).perform(selectTabAtPosition(13))

        Thread.sleep(1000)

        onView(allOf(withId(R.id.list_fragment_cards_rv), isDisplayed())).perform(actionOnItemAtPosition<CardViewHolder>(0, click()))

        onView(allOf(withId(R.id.card_details_section_value), withText("Adaptation"))).check(matches(isDisplayed()))

        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.filters_button)).perform(click())
        onView(withId(R.id.filters_fragment_scrollview)).perform(swipeUp())
        onView(withId(R.id.clear_filters_button)).perform(click())
    }


    private fun assertListCount(count :Int) {
        onView(allOf(withId(R.id.list_fragment_cards_rv), isDisplayed()))
            .check(matches(CustomMatchers.withItemCount(count)))
    }


}

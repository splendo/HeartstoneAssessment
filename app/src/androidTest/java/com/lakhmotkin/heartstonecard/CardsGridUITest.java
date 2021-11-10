package com.lakhmotkin.heartstonecard;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.lakhmotkin.heartstonecards.R;
import com.lakhmotkin.heartstonecards.view.ui.CardsGridActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static org.hamcrest.CoreMatchers.allOf;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */
@RunWith(AndroidJUnit4.class)
public class CardsGridUITest {

    @Rule
    public ActivityTestRule<CardsGridActivity> mCardsGridActivityTestRule =
            new ActivityTestRule<>(CardsGridActivity.class);

    @Before
    public void init(){
        mCardsGridActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void clickProductInList_opensDetailedUi() throws Exception {
        SystemClock.sleep(2000);
        onView(withId(R.id.cards_recycler_grid))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        SystemClock.sleep(2000);
        onView(allOf(withId(R.id.card_name), isDisplayed())).check(matches(isCompletelyDisplayed()));
    }
}
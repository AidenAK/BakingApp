package com.example.doelay.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;



@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
        
        public static final String RECIPE_NAME_AT_POSITION_ZERO = "Brownies";

        @Rule
        public ActivityTestRule<MainActivity> mainActivityTestRule =
                new ActivityTestRule<>(MainActivity.class);

        @Test
        public void scrollToPosition_CheckRecipeName() {

        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.scrollToPosition(1));


        onView(withText("Brownies")).check(matches(isDisplayed()));
    }
}

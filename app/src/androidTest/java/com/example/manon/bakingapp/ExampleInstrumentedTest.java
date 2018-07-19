package com.example.manon.bakingapp;


import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.manon.bakingapp.Activity.ListIngredientsActivity;
import com.example.manon.bakingapp.Activity.MainActivity;
import com.example.manon.bakingapp.Widget.ListIngredientsWidgetProvider;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    // provides functional testing for a specific, single activity
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // indicates where we'll be testing
    @Test
    // test if the UI elements are displayed
    public void elementsDisplayedInMainActivity(){
        onView(withId(R.id.card_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.main_activity_action_refresh)).check(matches(isDisplayed()));
    }

    @Test
    public void recipeInAdapter(){
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
        onView(withText("Brownies")).check(matches(isDisplayed()));
        onView(withText("Yellow Cake")).check(matches(isDisplayed()));
        onView(withText("Cheesecake")).check(matches(isDisplayed()));
    }

    @Test
    //When we click on an item in the recyclerview showing the list of recipes, the master list is shown with another recyclerview for the ingredients and the steps
    //When we click on the list ingredients item of the recycler view, the list of ingredients is shown
    public void clickRecipeItemAdapter_goRecipeDetailsActivity_goRecipeListIngredients(){
        onView(withId(R.id.card_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.master_list_recycler)).check(matches(isDisplayed()));
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
        onView(withText("List of ingredients")).check(matches(isDisplayed()));

        onView(withId(R.id.master_list_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.list_ingredients_recyclerview)).check(matches(isDisplayed()));
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));

    }
}

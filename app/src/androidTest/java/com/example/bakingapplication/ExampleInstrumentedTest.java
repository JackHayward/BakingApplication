package com.example.bakingapplication;

import android.app.Activity;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.rule.ActivityTestRule;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
  @Rule
  public IntentsTestRule intentRule = new IntentsTestRule(MainActivity.class);

  @Test
  public void selectingRecipesOpensPlayerCorrectly() {
    Activity activity = intentRule.getActivity();

    Espresso.onView(ViewMatchers.withId(R.id.recipe_recyclerview))
        .check(ViewAssertions.matches(
            ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    Espresso.onView(ViewMatchers.withId(R.id.recipe_recyclerview))
        .check(ViewAssertions.matches(EspressoUtils.atPosition(0,
            ViewMatchers.hasDescendant(ViewMatchers.withText("Nutella Pie")))));

    Espresso.onView(ViewMatchers.withId(R.id.recipe_recyclerview))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

    Intents.intended(IntentMatchers.hasComponent(StepListActivity.class.getName()));

    Espresso.onView(ViewMatchers.withId(R.id.recipe_list))
        .check(ViewAssertions.matches(EspressoUtils.atPosition(0,
            ViewMatchers.hasDescendant(ViewMatchers.withText("Recipe Introduction")))));

    Espresso.onView(ViewMatchers.withId(R.id.recipe_list))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

    Intents.intended(IntentMatchers.hasComponent(StepDetailActivity.class.getName()));

    Espresso.onView(ViewMatchers.withId(R.id.player_view))
        .check(ViewAssertions.matches(
            ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
  }
}
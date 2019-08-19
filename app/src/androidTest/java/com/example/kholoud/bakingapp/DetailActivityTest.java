package com.example.kholoud.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.kholoud.bakingapp.DetailActivity;
import com.example.kholoud.bakingapp.R;
import com.example.kholoud.bakingapp.Ingredient;
import com.example.kholoud.bakingapp.Recipe;
import com.example.kholoud.bakingapp.RecipeStep;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {
    //*******************************************************

    Context context = InstrumentationRegistry.getTargetContext();

    Ingredient testIngredient = new Ingredient(5, "CUP", "Spice");
    RecipeStep testStep = new RecipeStep(3, "Test Short Description", "Test Description", "https://d17h27t6h515a5.cloudfront.net/topher/2017/" + "April/58ffd974_-intro-creampie/-intro-creampie.mp4", "");

    ArrayList<Ingredient> testIngredientsList= new ArrayList<Ingredient>();
    ArrayList<RecipeStep> testStepsList = new ArrayList<RecipeStep>();
//******************************************************
    @Rule
    public ActivityTestRule<DetailActivity> activityRule =
            new ActivityTestRule(DetailActivity.class, false, false);
//******************************************************************
    // Frist test
    @Test
    public void testIngredientsView() {
        testIngredientsList.add(testIngredient);
        testStepsList.add(testStep);
        Recipe testRecipe = new Recipe("Cake", 8, testIngredientsList, testStepsList, "");
        Intent testIntent = new Intent();
        testIntent.putExtra(context.getString(R.string.recipe_object), testRecipe);
        activityRule.launchActivity(testIntent);
        onView(withId(R.id.ingredients_text)).perform().check(matches(isDisplayed()));
    }
//Test Second
   @Test
    public void checkStepElement() {
        testIngredientsList.add(testIngredient);
        testStepsList.add(testStep);
        Recipe testRecipe = new Recipe("Cake", 8, testIngredientsList, testStepsList, "");
        Intent testIntent = new Intent();
        testIntent.putExtra(context.getString(R.string.recipe_object), testRecipe);
        activityRule.launchActivity(testIntent);
        onView(withContentDescription(testStep.getSmallDescription()))
                .perform().check(matches(isDisplayed()));
    }
//*********************
    @Test
    public void checkStepOpen() {
        testIngredientsList.add(testIngredient);
        testStepsList.add(testStep);
        Recipe testRecipe =
                new Recipe("Cake", 8, testIngredientsList, testStepsList, "");
        Intent testIntent = new Intent();
        testIntent.putExtra(context.getString(R.string.recipe_object), testRecipe);
        activityRule.launchActivity(testIntent);
        onView(withContentDescription(testStep.getSmallDescription()))
                .perform(click());
        onView(withId(R.id.video_container)).check(matches(isDisplayed()));
    }
}

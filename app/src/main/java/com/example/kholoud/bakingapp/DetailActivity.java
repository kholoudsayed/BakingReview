package com.example.kholoud.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kholoud.bakingapp.Ingredient;
import com.example.kholoud.bakingapp.Recipe;
import com.example.kholoud.bakingapp.RecipeStep;
import com.example.kholoud.bakingapp.DescriptionListFragment;
import com.example.kholoud.bakingapp.InstructionFragment;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
//DEF**********************************
    private String RecipeName;
    private int CurrentPosition;
    private ArrayList<Ingredient> IngredientArr;
    private ArrayList<RecipeStep> RecipeStepArr;
    //Object class ********************************************
    private DescriptionListFragment DescriptionListFragment;
    private InstructionFragment instructionFragment;

    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.recipe_object))) {
            //Extra //
            Recipe recipe;
            recipe= intent.getParcelableExtra(getString(R.string.recipe_object));
            RecipeName = recipe.getmRecipeName();
            getSupportActionBar().setTitle(RecipeName);
//Redefine **************************************************************
            IngredientArr = recipe.getmIngredientsList();
            RecipeStepArr = recipe.getmRecipeStepList();
        }

        instructionFragment = new com.example.kholoud.bakingapp.InstructionFragment();
        DescriptionListFragment = new DescriptionListFragment();
//Check Tablet ******************************************************
        isTablet = getResources().getBoolean(R.bool.isTablet);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.recipe_fragment_container, DescriptionListFragment,
                            getString(R.string.description_list_fragment))
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.position_value), CurrentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        CurrentPosition = savedInstanceState.getInt(getString(R.string.position_value));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
        return true;
    }

    public void openFragment() {
        if (isTablet) {
            InstructionFragment FragmentYes;
            FragmentYes= (InstructionFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.instruction_fragment));
            if (FragmentYes != null && FragmentYes.isVisible()) FragmentYes.onPosChanged(CurrentPosition);

            else
                getSupportFragmentManager().beginTransaction().add(R.id.instruction_fragment_container, instructionFragment, getString(R.string.instruction_fragment)).commit();

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_fragment_container, instructionFragment,
                            getString(R.string.instruction_fragment))
                    .addToBackStack(null)
                    .commit();
        }
    }


    //Geter Methods
    public String getRecipeName() {
        return RecipeName;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return IngredientArr;
    }


    public ArrayList<RecipeStep> getRecipeStepList() {
        return RecipeStepArr;
    }


    public int getCurrentPosition() {
        return CurrentPosition;
    }

    //Set Method *
    public void setCurrentPosition(int mCurrentPosition) {
        this.CurrentPosition = mCurrentPosition;
    }




}

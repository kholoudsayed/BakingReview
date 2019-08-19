package com.example.kholoud.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kholoud.bakingapp.DetailActivity;
import com.example.kholoud.bakingapp.R;
import com.example.kholoud.bakingapp.RecipeStepAdapter;
import com.example.kholoud.bakingapp.Ingredient;
import com.example.kholoud.bakingapp.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DescriptionListFragment extends Fragment {
    //***********DEF

    @BindView(R.id.ingredients_text)
    TextView mIngredientsText;
    @BindView(R.id.description_list_rv)
    RecyclerView RecipeStepRecyclerView;

    private RecipeStepAdapter recipestepadapter;
    private RecyclerView.LayoutManager layoutManager;

    //*****************constractor empty *******************************
    public DescriptionListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


//************************************************************************
    public void IngredientData() {
        ArrayList<Ingredient> ingredients = ((DetailActivity) getActivity()).getIngredientList();
        mIngredientsText.setText(getString(R.string.ingredients_text) + "\n");
            int i =0;
            while(i<ingredients.size()){
                Ingredient appendIngredient = ingredients.get(i);
            String ingredientLine =
                    "\n(" + appendIngredient.getQuantity() + " "
                            + appendIngredient.getMeasure() + ") "
                            + appendIngredient.getIngredientName();
            mIngredientsText.append(ingredientLine);
            i++;
        }
    }

//***************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // View Var
        final View viewDes = inflater.inflate(R.layout.fragment_description_list, container, false);

        ButterKnife.bind(this, viewDes);
//Call Function
        IngredientData();

        RecipeStepRecyclerView.setHasFixedSize(true);

        ArrayList<RecipeStep> recipeStepList ;
        recipeStepList=((DetailActivity) getActivity()).getRecipeStepList();
        String recipeName = ((DetailActivity) getActivity()).getRecipeName();
        //ReDef**************************************************************************

        recipestepadapter = new RecipeStepAdapter(getContext(), recipeName, recipeStepList);
        RecipeStepRecyclerView.setAdapter(recipestepadapter);
        layoutManager = new LinearLayoutManager(getContext());
        RecipeStepRecyclerView.setLayoutManager(layoutManager);

        return viewDes;
    }


    //************************
}
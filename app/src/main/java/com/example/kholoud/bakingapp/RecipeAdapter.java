package com.example.kholoud.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.example.kholoud.bakingapp.DetailActivity;
import com.example.kholoud.bakingapp.R;
import com.example.kholoud.bakingapp.AppWidget;
import com.example.kholoud.bakingapp.Ingredient;
import com.example.kholoud.bakingapp.Recipe;
import com.example.kholoud.bakingapp.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {
//********************************DEF***
    public Context context;
    private ArrayList<Recipe> RecipeList;

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearlayout;
        public ImageView imageView;
        public TextView textView;
        public RecipeAdapterViewHolder(LinearLayout view) {
            super(view);

            linearlayout = view;
            textView = new TextView(context);
            imageView = new ImageView(context);

            linearlayout.addView(imageView);
            linearlayout.addView(textView);
        }
    }



    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout recipeViewContainer = new LinearLayout(context);
        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        recipeViewContainer.setLayoutParams(cardParams);
        recipeViewContainer.setBackgroundResource(R.color.cardBack);
        RecipeAdapterViewHolder recipeHolder= new RecipeAdapterViewHolder(recipeViewContainer);
        return recipeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, final int position) {
        final Recipe RECP = RecipeList.get(position);

        //properties ************************
        LinearLayout.LayoutParams imageParams =
                new LinearLayout.LayoutParams(500,500,5f);
        imageParams.setMargins(20, 10, 20, 10);

        holder.imageView.setLayoutParams(imageParams);
        Picasso.get()
                .load(Uri.parse(RECP.getmImageUrl()))
                .placeholder(R.drawable.cake)
                .error(R.drawable.cake)
                .into(holder.imageView);
//propertes text
        LinearLayout.LayoutParams textParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 6f;
        textParams.gravity = Gravity.CENTER;
        holder.textView.setLayoutParams(textParams);
        holder.textView.setText(RECP.getmRecipeName());
        holder.textView.
                append("\n" + Integer.toString(RECP.getmServingSize()) + " servings");
        holder.textView.setTextAppearance(context, R.style.RecipeCardText);

       //when click listeners
        holder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AppWidget.class);

                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra
                        (context.getString(R.string.recipe_name), RECP.getmRecipeName());
                intent.putExtra
                        (context.getString
                                (R.string.ingredients_text), RECP.getmIngredientsList());
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra
                        (context.getString(R.string.recipe_object), RECP);
                context.sendBroadcast(intent);
                context.startActivity(detailIntent);
            }
        });
    }

    @Override

    public int getItemCount() {
        return RecipeList.size();
    }


    //***************
    public void RecipesData(String jsonResponse) {
        try {
            //Open Jeson /*****************
            JSONArray recipeJson = new JSONArray(jsonResponse);
            int i = 0;
            while (i< recipeJson.length()){

                String newRecipeNameobject = recipeJson.getJSONObject(i).getString(context.getString(R.string.name_param));
                int Siz = recipeJson.getJSONObject(i).getInt(context.getString(R.string.servings_param));
                JSONArray ingredientsJson = recipeJson.getJSONObject(i).getJSONArray(context.getString(R.string.ingredients_param));
                JSONArray recipeStepsJson = recipeJson.getJSONObject(i).getJSONArray(context.getString(R.string.steps_param));
                //Arraies ***********
                ArrayList<Ingredient> IngredientList = new ArrayList<>();
                ArrayList<RecipeStep> RecipeStepList = new ArrayList<>();
                //**************
                int j =0;
                    while (j<ingredientsJson.length()){
                    JSONObject objectAt = ingredientsJson.getJSONObject(j);
                    double Quantity = objectAt.getDouble(context.getString(R.string.quantity_param));
                    String Measure = objectAt.getString(context.getString(R.string.measure_param));
                    String IngredientName = objectAt.getString(context.getString(R.string.ingredient_param));
                    Ingredient ingredient = new Ingredient(Quantity, Measure, IngredientName);
                    IngredientList.add(ingredient);
                    j++;
                }




                //***********************************************
                int t = 0;
                while (t<recipeStepsJson.length()){
                    JSONObject objectAt = recipeStepsJson.getJSONObject(t);
                    int newId = objectAt.getInt(context.getString(R.string.id_param));
                    String newShortDescription = objectAt.getString(context.getString(R.string.short_description_param));
                    String newDescription = objectAt.getString(context.getString(R.string.description_param));
                    String newVideoUrl = objectAt.getString(context.getString(R.string.video_param));
                    String newThumbnailUrl = objectAt.getString(context.getString(R.string.thumbnail_param));
                    RecipeStep RecipeStep = new RecipeStep(newId, newShortDescription, newDescription, newVideoUrl, newThumbnailUrl);
                   // TO add

                    RecipeStepList.add(RecipeStep);
                    t++;
                }


                // For image ***************************
                String newImageUrl = recipeJson.getJSONObject(i)
                        .getString(context.getString(R.string.image_param));
                RecipeList.add(new Recipe (newRecipeNameobject, Siz, IngredientList, RecipeStepList, newImageUrl));
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Call Adapter /***to set data

    public RecipeAdapter(Context context, String jesonobject) {
        this.context = context;
        RecipeList = new ArrayList<>();
        RecipesData(jesonobject);
    }
}

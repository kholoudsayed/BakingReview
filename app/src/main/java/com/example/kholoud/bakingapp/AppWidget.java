package com.example.kholoud.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.kholoud.bakingapp.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;


public class AppWidget extends AppWidgetProvider {
    //DEF *******************************************************************
    @BindView(R.id.widget_recipe_text) TextView RecipeNameText;
    @BindView(R.id.widget_ingredients_text) TextView IngredientsText;

    private static String Name = "";

    private static ArrayList<Ingredient> IngredientsString = new ArrayList<Ingredient>();

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Widget
        CharSequence widgetRecipeText = Name;
        String ingredientsList = "\n";
        // To put in  IngredientsList


        for (Ingredient i : IngredientsString) {
            ingredientsList += "\n(" + i.getQuantity() + " " + i.getMeasure()
                    + ")\t\t" + i.getIngredientName();
        }

        // Construct the RemoteViews
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        if (!Name.equals("") && !IngredientsString.isEmpty()) {
            views.setTextViewText(R.id.widget_recipe_text, widgetRecipeText);
            views.setTextViewText(R.id.widget_ingredients_text, ingredientsList);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[]Widgets) {
        // for each *****************
        for (int appWidgetId:Widgets
                )
        {
            updateWidget(context, appWidgetManager, appWidgetId);

        }



    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String Search = context.getString(R.string.recipe_name);

        String Searchintg= context.getString(R.string.ingredients_text);


        //************************************************************************
        if (intent.hasExtra(Search) && intent.hasExtra(Searchintg)) {
            Name = intent.getStringExtra(Search);
            IngredientsString = intent.getParcelableArrayListExtra(Searchintg);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName component = new ComponentName(context.getPackageName(),
                    AppWidget.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(component);

            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }
}


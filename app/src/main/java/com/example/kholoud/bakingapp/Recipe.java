package com.example.kholoud.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Recipe implements Parcelable {
    //DEF********************************
    private String RecipeName;
    private int ServingSize;
    private ArrayList<Ingredient> IngredientsList;
    private ArrayList<RecipeStep> RecipeStepList;
    private String ImageUrl;
//Use Parcelable
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
//Constractor
    public Recipe(String RecipeName, int ServingSize, ArrayList<Ingredient> IngredientsList, ArrayList<RecipeStep> RecipeStepList, String ImageUrl) {
        this.RecipeName = RecipeName;
        this.ServingSize = ServingSize;
        this.IngredientsList = IngredientsList;
        this.RecipeStepList = RecipeStepList;
        this.ImageUrl = ImageUrl;
    }
    private Recipe(Parcel in) {
        RecipeName = in.readString();
        ServingSize = in.readInt();
        ImageUrl = in.readString();
        if (in.readByte() == 0x01) {
            IngredientsList = new ArrayList<>();
            in.readList(IngredientsList, Ingredient.class.getClassLoader());
        } else {
            IngredientsList = null;
        }
        if (in.readByte() == 0x01) {
            RecipeStepList = new ArrayList<>();
            in.readList(RecipeStepList,com.example.kholoud.bakingapp.RecipeStep.class.getClassLoader());
        } else {
            RecipeStepList = null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(RecipeName);
        dest.writeInt(ServingSize);
        dest.writeString(ImageUrl);
        if (IngredientsList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(IngredientsList);
        }
        if (RecipeStepList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(RecipeStepList);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

//Getter Methods
    public String getmRecipeName() {
        return RecipeName;
    }


    public int getmServingSize() {
        return ServingSize;
    }


    public ArrayList<Ingredient> getmIngredientsList() {
        return IngredientsList;
    }



    public ArrayList<RecipeStep> getmRecipeStepList() {
        return RecipeStepList;
    }



    public String getmImageUrl() {
        return ImageUrl;
    }

}
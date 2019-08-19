package com.example.kholoud.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
//DEF **********************************************************
    private double Quantity;
    private String Measure;
    private String IngredientName;
//*******************************************************************
    //Using Parcelable

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    //Constractor ******************************************************
    public Ingredient(double Quantity, String Measure, String IngredientName) {
        this.Quantity = Quantity;
        this.Measure = Measure;
        this.IngredientName = IngredientName;
    }

    private Ingredient(Parcel in) {
        Quantity = in.readDouble();
        Measure = in.readString();
        IngredientName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(Quantity);
        dest.writeString(Measure);
        dest.writeString(IngredientName);
    }
//Getter Mehods******************************************************
    public double getQuantity() {
        return Quantity;
    }


    public String getMeasure() {
        return Measure;
    }


    public String getIngredientName() {
        return IngredientName;
    }

}

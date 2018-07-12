package com.example.manon.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private double quantity;
    private String measure;
    private String ingredient;

    // constructor
    public Ingredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    // getters & setters
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure(){
        return measure;
    }

    public void setMeasure(String measure){
        this.measure = measure;
    }

    // toString
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(ingredient).append(" / ").append(Double.toString(quantity)).append(" / ").append(measure).append(".\n");
        return str.toString();
    }

    // Parcelable
    public Ingredient(Parcel in){
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>(){

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}

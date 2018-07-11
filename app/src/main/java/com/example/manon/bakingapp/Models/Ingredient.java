package com.example.manon.bakingapp.Models;

public class Ingredient {
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

    // toString
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(ingredient).append(" / ").append(Double.toString(quantity)).append(" / ").append(measure).append(".\n");
        return str.toString();
    }
}

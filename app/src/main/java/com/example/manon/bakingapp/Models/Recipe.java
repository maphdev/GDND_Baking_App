package com.example.manon.bakingapp.Models;

import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;

    // constructor
    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    // getters & setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // toString
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Step ").append(Integer.toString(id)).append(" : ").append(name).append("\n")
                .append("INGREDIENTS :\n");
        for(int i = 0; i < ingredients.size(); i++){
            str.append(ingredients.get(i));
        }
        str.append("STEPS :\n");
        for(int i = 0; i < steps.size(); i++){
            str.append(steps.get(i));
        }
        str.append("Servings : ").append(Integer.toString(servings)).append(".\n")
                .append("Image : ").append(image).append(".\n");

        return str.toString();
    }
}

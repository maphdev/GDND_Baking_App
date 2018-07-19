package com.example.manon.bakingapp.Utils;

import com.example.manon.bakingapp.Models.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    // parse Json
    public static List<Recipe> parseJson(String json){
        Gson gson = new GsonBuilder().create();
        Recipe[] tabRecipes = gson.fromJson(json, Recipe[].class);

        List<Recipe> listRecipes = new ArrayList<Recipe>();
        for (int i = 0; i < tabRecipes.length; i++){
            listRecipes.add(tabRecipes[i]);
        }

        return listRecipes;
    }
}

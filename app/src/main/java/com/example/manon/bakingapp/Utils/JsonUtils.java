package com.example.manon.bakingapp.Utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.manon.bakingapp.Models.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    // read json file from asset folder
    public static String loadJsonFromAsset(Context context, String filename){
        String json = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream input = assetManager.open(filename);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }

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

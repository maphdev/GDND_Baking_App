package com.example.manon.bakingapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Recipe recipe = getIntent().getParcelableExtra("recipe");
        Log.v("TRY", recipe.toString());
    }
}

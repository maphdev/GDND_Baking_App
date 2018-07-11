package com.example.manon.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.Utils.JsonUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get Json
        String myJson = JsonUtils.loadJsonFromAsset(getApplicationContext(), getString(R.string.json_filename));

        List<Recipe> listRecipes = JsonUtils.parseJson(myJson);

        TextView txt = (TextView) findViewById(R.id.txtviewS);
        txt.setText(listRecipes.get(3).toString());
    }
}

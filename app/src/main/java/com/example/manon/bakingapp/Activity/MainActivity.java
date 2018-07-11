package com.example.manon.bakingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.manon.bakingapp.Adapter.CardAdapter;
import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;
import com.example.manon.bakingapp.Utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CardAdapter.ListItemClickListener{

    @BindView(R.id.card_recycler_view) RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setCardAdapter();
    }

    // set the adapter
    public void setCardAdapter(){
        String myJson = JsonUtils.loadJsonFromAsset(getApplicationContext(), getString(R.string.json_filename));
        List<Recipe> listRecipes = JsonUtils.parseJson(myJson);

        int spanCount;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            spanCount = getResources().getInteger(R.integer.column_span_portrait);
        } else {
            spanCount = getResources().getInteger(R.integer.column_span_landscape);
        }

        gridLayoutManager = new GridLayoutManager(this, spanCount);
        cardAdapter = new CardAdapter(listRecipes, this, this);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cardAdapter);
    }

    @Override
    public void onListItemClicked(int clickedPosition) {
        Recipe recipe = cardAdapter.getListRecipes().get(clickedPosition);

        Context context = MainActivity.this;
        Class destinationClass = RecipeActivity.class;

        Intent startRecipeActivity = new Intent(context, destinationClass);
        startRecipeActivity.putExtra("recipe", recipe);
        startActivity(startRecipeActivity);
    }
}

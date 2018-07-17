package com.example.manon.bakingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manon.bakingapp.Adapter.CardAdapter;
import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;
import com.example.manon.bakingapp.Utils.JsonUtils;
import com.example.manon.bakingapp.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CardAdapter.ListItemClickListener{

    @BindView(R.id.card_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.no_internet_txtView) TextView noInternetTxtView;
    private CardAdapter cardAdapter;
    private GridLayoutManager gridLayoutManager;
    private Toast msgConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setCardAdapter();

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getResources().getString(R.string.preference_recipe_id), 0);
        editor.apply();
        Log.i("MSG", Integer.toString(sharedPreferences.getInt(getResources().getString(R.string.preference_recipe_id), 0)));
    }

    // set the adapter
    public void setCardAdapter(){
        List<Recipe> listRecipes = new ArrayList<>();
        if (!NetworkUtils.isNetworkAvailable(this)){
            displayNoInternetConnection();
        } else {
            listRecipes = NetworkUtils.getListRecipesFromURL();
            displayData();
        }

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

    // display helpers
    public void displayNoInternetConnection(){
        recyclerView.setVisibility(View.GONE);
        noInternetTxtView.setVisibility(View.VISIBLE);
    }

    public void displayData(){
        recyclerView.setVisibility(View.VISIBLE);
        noInternetTxtView.setVisibility(View.GONE);
    }

    // click listener on recipe cards
    @Override
    public void onListItemClicked(int clickedPosition) {
        Recipe recipe = cardAdapter.getListRecipes().get(clickedPosition);

        Context context = MainActivity.this;
        Class destinationClass = RecipeDetailsActivity.class;

        Intent startRecipeActivity = new Intent(context, destinationClass);
        startRecipeActivity.putExtra(getString(R.string.PARCELABLE_RECIPE), recipe);
        startActivity(startRecipeActivity);
    }

    // menu in order to refresh the recipes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();
        if (itemThatWasClicked == R.id.main_activity_action_refresh){
            if (NetworkUtils.isNetworkAvailable(getApplicationContext())){
                List<Recipe> listRecipes = NetworkUtils.getListRecipesFromURL();
                cardAdapter.setListRecipes(listRecipes);
                cardAdapter.notifyDataSetChanged();
                gridLayoutManager.scrollToPosition(0);
                displayData();
                return true;
            } else {
                if (msgConnection != null) {
                    msgConnection.cancel();
                }
                msgConnection = Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT);
                msgConnection.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

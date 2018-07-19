package com.example.manon.bakingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.manon.bakingapp.Fragment.ListIngredientsFragment;
import com.example.manon.bakingapp.Fragment.RecipeDetailsFragment;
import com.example.manon.bakingapp.Fragment.StepDetailsFragment;
import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;
import com.example.manon.bakingapp.Widget.ListIngredientsWidgetProvider;

import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnAdapterClickListener, StepDetailsFragment.ChangeFragment{

    private boolean twoPane = true;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        if(findViewById(R.id.sw600_separator) != null){
            twoPane = true;
        } else {
            twoPane = false;
        }

        if (savedInstanceState == null){
            // retrieve recipe infos
            Recipe recipe = getIntent().getParcelableExtra(getResources().getString(R.string.PARCELABLE_RECIPE));

            // Master list, on phone & tablet layout
            fragmentManager.beginTransaction()
                    .add(R.id.activity_recipe_master_list, RecipeDetailsFragment.newInstance(recipe))
                    .commit();

            // if tablet layout
            if(findViewById(R.id.sw600_separator) != null){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            setSharedPreference(recipe);
            sendBroadcast();
        }
    }

    // handle clicks on Adapter, on both phone and tablet layout
    @Override
    public void onAdapterClickListener(int clickedItemIndex, Recipe recipe) {
        Context context = getApplicationContext();
        if(twoPane){
            if(clickedItemIndex == 0){
                fragmentManager.beginTransaction().
                        replace(R.id.sw600_detailed_pane, ListIngredientsFragment.newInstance(recipe))
                        .commit();
            } else {
                StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipe, clickedItemIndex-1, false);
                fragmentManager.beginTransaction()
                        .replace(R.id.sw600_detailed_pane, stepDetailsFragment)
                        .commit();
            }
        } else {
            if(clickedItemIndex == 0){
                Class destinationClass = ListIngredientsActivity.class;
                Intent startListIngredientsActivity = new Intent(context, destinationClass);
                startListIngredientsActivity.putExtra(getString(R.string.PARCELABLE_RECIPE), recipe);
                startActivity(startListIngredientsActivity);
            } else {
                Class destinationClass = StepActivity.class;
                Intent startStepActivity = new Intent(context, destinationClass);
                startStepActivity.putExtra(getString(R.string.ID),clickedItemIndex-1);
                startStepActivity.putExtra(getString(R.string.PARCELABLE_RECIPE), recipe);
                startActivity(startStepActivity);
            }
        }
    }

    @Override
    public void increaseStep(Recipe recipe, int id, boolean navigationButton) {
        //StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipe, id+1, navigationButton);
        return;
    }

    @Override
    public void decreaseStep(Recipe recipe, int id, boolean navigationButton) {
        //StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipe, id-1, navigationButton);
        return;
    }

    // shared preference so the widget knows which recipe to display
    public void setSharedPreference(Recipe recipe){
        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getResources().getString(R.string.preference_recipe_id), recipe.getId());
        editor.putString(getResources().getString(R.string.preference_recipe_name), recipe.getName());
        editor.apply();
    }

    // send an intent to update the widget
    public void sendBroadcast(){
        Intent intent = new Intent(this, ListIngredientsWidgetProvider.class);
        intent.setAction(ListIngredientsWidgetProvider.WIDGET_UPDATE);
        sendBroadcast(intent);
    }
}

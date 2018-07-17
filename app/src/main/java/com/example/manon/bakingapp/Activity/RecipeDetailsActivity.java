package com.example.manon.bakingapp.Activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manon.bakingapp.Fragment.ListIngredientsFragment;
import com.example.manon.bakingapp.Fragment.RecipeDetailsFragment;
import com.example.manon.bakingapp.Fragment.StepDetailsFragment;
import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;
import com.example.manon.bakingapp.Widget.FetchDataRecipeService;

import butterknife.BindView;
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

            SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getResources().getString(R.string.preference_recipe_id), recipe.getId()-1);
            editor.apply();
            FetchDataRecipeService.startActionFetchDataRecipe(getApplicationContext());
            Log.i("MSG", "id : " + Integer.toString(recipe.getId()));
            Log.i("MSG", Integer.toString(sharedPreferences.getInt(getResources().getString(R.string.preference_recipe_id), 0)));

        }
    }

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
                startStepActivity.putExtra("ID",clickedItemIndex-1);
                startStepActivity.putExtra(getString(R.string.PARCELABLE_RECIPE), recipe);
                startActivity(startStepActivity);
            }
        }
    }

    @Override
    public void increaseStep(Recipe recipe, int id, boolean navigationButton) {
        StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipe, id+1, navigationButton);
        return;
    }

    @Override
    public void decreaseStep(Recipe recipe, int id, boolean navigationButton) {
        return;
    }
}

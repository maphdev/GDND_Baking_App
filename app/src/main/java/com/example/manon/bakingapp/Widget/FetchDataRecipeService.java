package com.example.manon.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.example.manon.bakingapp.Fragment.ListIngredientsFragment;
import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;
import com.example.manon.bakingapp.Utils.NetworkUtils;

public class FetchDataRecipeService extends IntentService{

    public static final String ACTION_FETCH_RECIPE = "com.example.manon.bakingapp.action.fetch_recipe";

    public FetchDataRecipeService() {
        super("FetchDataRecipeService");
    }

    public static void startActionFetchDataRecipe(Context context){
        Intent intent = new Intent(context, FetchDataRecipeService.class);
        intent.setAction(ACTION_FETCH_RECIPE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_RECIPE.equals(action)) {
                handleActionFetchDataRecipe();
            }
        }
    }

    private void handleActionFetchDataRecipe() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getApplicationContext().getResources().getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt(getApplicationContext().getResources().getString(R.string.preference_recipe_id), 0);

        Recipe recipe;
        if (id == 0){
            recipe = null;
        } else {
            recipe = NetworkUtils.getListRecipesFromURL().get(id);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ListIngredientsWidgetProvider.class));


        //ListIngredientsWidgetProvider.updateRecipeWidgets(this, appWidgetManager, appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_gridview);

    }
}

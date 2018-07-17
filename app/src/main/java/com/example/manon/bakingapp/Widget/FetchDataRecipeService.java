package com.example.manon.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.manon.bakingapp.Fragment.ListIngredientsFragment;
import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.Utils.NetworkUtils;

public class FetchDataRecipeService extends IntentService{

    public static final String ACTION_FETCH_RECIPE = "com.example.manon.bakingapp.action.fetch_recipe";
    public static final String EXTRA_RECIPE_ID = "com.example.manon.bakingapp.extra.RECIPE_ID";

    public FetchDataRecipeService() {
        super("FetchDataRecipeService");
    }

    public static void startActionFetchDataRecipe(Context context, int id){
        Intent intent = new Intent(context, FetchDataRecipeService.class);
        intent.setAction(ACTION_FETCH_RECIPE);
        intent.putExtra(EXTRA_RECIPE_ID, id);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_RECIPE.equals(action)) {
                final int recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, 0);
                handleActionFetchDataRecipe(recipeId);
            }
        }
    }

    private void handleActionFetchDataRecipe(int id) {
        Recipe recipe = NetworkUtils.getListRecipesFromURL().get(id);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ListIngredientsWidgetProvider.class));

        ListIngredientsWidgetProvider.updateRecipeWidgets(this, appWidgetManager, appWidgetIds, recipe);
    }
}

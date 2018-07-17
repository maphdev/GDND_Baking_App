package com.example.manon.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.manon.bakingapp.Activity.MainActivity;
import com.example.manon.bakingapp.Activity.RecipeDetailsActivity;
import com.example.manon.bakingapp.Fragment.RecipeDetailsFragment;
import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;
import com.example.manon.bakingapp.Utils.NetworkUtils;

/**
 * Implementation of App Widget functionality.
 */
public class ListIngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_ingredients_widget);

        Log.i("WIDGET", recipe.getName());

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        FetchDataRecipeService.startActionFetchDataRecipe(context, 0);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipe){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


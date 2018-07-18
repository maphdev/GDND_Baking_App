package com.example.manon.bakingapp.Widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
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

    public static final String WIDGET_UPDATE = "android.bakingapp.action.APPWIDGET_UPDATE";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // set remoteAdapter
        Intent gridIntent = new Intent(context, GridWidgetService.class);
        gridIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        gridIntent.putExtra("random", Long.toString(System.currentTimeMillis()));
        gridIntent.setData(Uri.parse(gridIntent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_ingredients_widget);
        remoteViews.setRemoteAdapter(R.id.widget_gridview, gridIntent);

        // set diplay
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt(context.getResources().getString(R.string.preference_recipe_id), -1);
        if (id == -1){
            remoteViews.setTextViewText(R.id.widget_recipe_name, context.getResources().getString(R.string.app_name));
            remoteViews.setViewVisibility(R.id.widget_text_no_recipe, View.VISIBLE);
        } else {
            String name = sharedPreferences.getString(context.getResources().getString(R.string.preference_recipe_name), context.getResources().getString(R.string.app_name));
            remoteViews.setTextViewText(R.id.widget_recipe_name, name);
            remoteViews.setViewVisibility(R.id.widget_text_no_recipe, View.INVISIBLE);
        }

        // set click intent
        Intent onClickIntent;
        if (id == -1){
            onClickIntent = new Intent(context, MainActivity.class);
        } else {
            onClickIntent = new Intent(context, RecipeDetailsActivity.class);
            onClickIntent.setAction(Long.toString(System.currentTimeMillis()));
            onClickIntent.putExtra(context.getResources().getString(R.string.PARCELABLE_RECIPE), NetworkUtils.getListRecipesFromURL().get(id-1));
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, onClickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);

        // update & notify
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_gridview);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), ListIngredientsWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void sendBroadCast(Context context, Class toClass){
        Intent intent = new Intent(context, ListIngredientsWidgetProvider.class);
        intent.setAction(WIDGET_UPDATE);
        context.getApplicationContext().sendBroadcast(intent);
    }
}


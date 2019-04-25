package com.example.doelay.bakingapp.widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.doelay.bakingapp.DetailStepActivity;
import com.example.doelay.bakingapp.MainActivity;
import com.example.doelay.bakingapp.R;
import com.example.doelay.bakingapp.RecipeDetailActivity;
import com.example.doelay.bakingapp.model.Ingredient;
import com.example.doelay.bakingapp.model.Recipe;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static Recipe recipeSelectedForWidget;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews views;
        if (width < 300) {

            Intent intent;
            views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

            if(recipeSelectedForWidget == null) {
                intent = new Intent(context, MainActivity.class);
                views.setTextViewText(R.id.tv_appwidget, context.getString(R.string.no_recipe_selected));
            } else {
                intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recipeSelected", recipeSelectedForWidget);
                views.setTextViewText(R.id.tv_appwidget, recipeSelectedForWidget.getName());
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

        } else {
            // TODO: 4/25/2019 need to fix this
            views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_list_view);

            Intent intent = new Intent(context, WidgetRemoteViewsService.class);
            views.setRemoteAdapter(R.layout.recipe_widget_list_view, intent);

            views.setEmptyView(R.layout.recipe_widget_list_view, R.id.empty_view);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    //call by the work thread to update the widgets
    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipeSelected) {
        //retreat the recipe. It may be null.
        recipeSelectedForWidget = recipeSelected;
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        UpdateRecipeWidgetIntentService.startActionUpdateRecipeWidget(context, recipeSelectedForWidget);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        UpdateRecipeWidgetIntentService.startActionUpdateRecipeWidget(context, recipeSelectedForWidget);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
//        recipeSelectedForWidget = null;
    }
}


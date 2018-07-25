package com.example.doelay.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import com.example.doelay.bakingapp.model.Recipe;

/**
 * Created by doelay on 2/23/18.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    private static Recipe recipeSelected;

    public static void setRecipeSelected(Recipe recipe) {
        recipeSelected = recipe;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        UpdateIngredientWidgetIntentService.startActionUpdateIngredientWidget(context, recipeSelected);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {

        }
    }
}

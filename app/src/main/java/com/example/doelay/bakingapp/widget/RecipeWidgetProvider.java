package com.example.doelay.bakingapp.widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
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

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        if(recipeSelectedForWidget == null) {
            views.setTextViewText(R.id.tv_appwidget, "No recipe selected.");
        } else {

//            Intent intent = new Intent(context, RecipeDetailActivity.class);
//            intent.putExtra("recipeSelected", recipeSelectedForWidget);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

            views.setTextViewText(R.id.tv_appwidget, buildIngredientList());
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipeSelected) {

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

    private static String buildIngredientList() {

        StringBuilder ingredientListBuilder = new StringBuilder();
        List<Ingredient> ingredientList = recipeSelectedForWidget.getIngredients();
        //get the recipe name
        ingredientListBuilder.append(recipeSelectedForWidget.getName());
        ingredientListBuilder.append("\n");

        //get the ingredients
        for(int i=0; i < ingredientList.size(); i++) {
            Ingredient ingredient = ingredientList.get(i);

            ingredientListBuilder.append("- ");
            ingredientListBuilder.append(ingredient.getQuantity());
            ingredientListBuilder.append("  ");
            ingredientListBuilder.append(ingredient.getMeasure());
            ingredientListBuilder.append("  ");
            ingredientListBuilder.append(ingredient.getIngredient());
            ingredientListBuilder.append("\n");
        }

        return ingredientListBuilder.toString();
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        recipeSelectedForWidget = null;
    }
}


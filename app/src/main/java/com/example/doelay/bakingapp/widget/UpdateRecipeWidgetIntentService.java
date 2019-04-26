package com.example.doelay.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.doelay.bakingapp.R;
import com.example.doelay.bakingapp.model.Recipe;

public class UpdateRecipeWidgetIntentService extends IntentService {

    public static final String TAG = UpdateRecipeWidgetIntentService.class.getSimpleName();
    public static final String ACTION_UPDATE_RECIPE_WIDGET = "com.example.doelay.bakingapp.widget";

    public UpdateRecipeWidgetIntentService() {
        super("UpdateRecipeWidgetIntentService");
    }

    public static void startActionUpdateRecipeWidget(Context context, Recipe recipeSelected) {
        Intent intent = new Intent(context, UpdateRecipeWidgetIntentService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGET);
        intent.putExtra("recipeSelected", recipeSelected);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(intent != null) {
            final String action = intent.getAction();
            if(ACTION_UPDATE_RECIPE_WIDGET.equals(action)) {
                if(intent.getExtras().containsKey("recipeSelected")) {
                    Recipe recipeSelected = intent.getParcelableExtra("recipeSelected");
                    handleActionUpdateRecipeWidget(recipeSelected);
                } else {
                    Log.d(TAG, "onHandleIntent: No recipe data.");
                }
            }
        }
    }

    private void handleActionUpdateRecipeWidget(Recipe recipeSelected) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.layout.recipe_widget_list_view);
        RecipeWidgetProvider.updateAppWidgets(this, appWidgetManager, appWidgetIds, recipeSelected);
        
    }
}

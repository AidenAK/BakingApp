package com.example.doelay.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.doelay.bakingapp.model.Recipe;

public class UpdateIngredientWidgetIntentService extends IntentService {

    public static final String TAG = UpdateIngredientWidgetIntentService.class.getSimpleName();
    public static final String ACTION_UPDATE_INGREDIENT_WIDGET = "com.example.doelay.bakingapp.widget";

    public UpdateIngredientWidgetIntentService() {
        super("UpdateIngredientWidgetIntentService");
    }

    public static void startActionUpdateIngredientWidget(Context context, Recipe recipeSelected) {
        Intent intent = new Intent(context, UpdateIngredientWidgetIntentService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENT_WIDGET);
        intent.putExtra("receipeSelected", recipeSelected);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(intent != null) {
            final String action = intent.getAction();


            if(ACTION_UPDATE_INGREDIENT_WIDGET.equals(action)) {

                if(intent.getExtras().containsKey("recipeSelected")) {
                    Recipe recipeSelected = intent.getParcelableExtra("recipeSelected");
                    handleActionUpdateIngredientWidget(recipeSelected);
                } else{
                    Log.d(TAG, "onHandleIntent: No recipe data.");
                }
            }
        }
    }

    private void handleActionUpdateIngredientWidget(Recipe recipeSelected) {
        // TODO: 7/12/18 Display the ingredients in the widget
        // TODO: 7/12/18 Pending intent to launch an activity when click on the widget
    }
}

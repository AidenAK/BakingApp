package com.example.doelay.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.doelay.bakingapp.R;
import com.example.doelay.bakingapp.Utils;
import com.example.doelay.bakingapp.model.Ingredient;
import com.example.doelay.bakingapp.model.Recipe;

import java.util.List;

public class WidgetRemoteViewsService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext()) ;
    }
}

class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String TAG = WidgetRemoteViewsFactory.class.getSimpleName();

    private Context mContext;
    private String recipeName;
    private String ingredientListString;
    private List<Ingredient> ingredientList;

    public WidgetRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }
    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        //retrieve the ingredient list String
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        ingredientListString = sharedPreferences.getString("ingredient_list_string", "");
        recipeName = sharedPreferences.getString("recipe_name", "");
        Log.d(TAG, "onDataSetChanged: "+ ingredientListString);

        //convert the ingredient list to a list object
        ingredientList = Utils.toIngredientList(ingredientListString);
        Log.d(TAG, "onDataSetChanged: "+ ingredientList);

    }

    @Override
    public int getCount() {
        if(ingredientList == null) return 0;
        return ingredientList.size();
    }
    @Override
    public RemoteViews getViewAt(int position) {

        if(ingredientList == null || ingredientList.size() == 0)  return null;

        float quantity = ingredientList.get(position).getQuantity();
        String measure = ingredientList.get(position).getMeasure();
        String ingredient = ingredientList.get(position).getIngredient();

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_list_item);

        views.setTextViewText(R.id.tv_quantity, String.valueOf(quantity));
        views.setTextViewText(R.id.tv_measure, measure);
        views.setTextViewText(R.id.tv_ingredient, ingredient);

        return views;

    }

//    private String formattedIngredientString() {
//
//        //Create ingredient list String
//        StringBuilder ingredientListBuilder = new StringBuilder();
//        //get recipe name
//        ingredientListBuilder.append(recipeName);
//        ingredientListBuilder.append("\n");
//        //get the ingredients
//        for(int i=0; i < ingredientList.size(); i++) {
//            Ingredient ingredient = ingredientList.get(i);
//
//            ingredientListBuilder.append("- ");
//            ingredientListBuilder.append(ingredient.getQuantity());
//            ingredientListBuilder.append("  ");
//            ingredientListBuilder.append(ingredient.getMeasure());
//            ingredientListBuilder.append("  ");
//            ingredientListBuilder.append(ingredient.getIngredient());
//            ingredientListBuilder.append("\n");
//        }
//
//        return ingredientListBuilder.toString();
//    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onDestroy() {

    }
}

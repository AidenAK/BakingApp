package com.example.doelay.bakingapp;

import com.example.doelay.bakingapp.model.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Convert a list object to String and vise versa.
 *
 * Reference:
 * https://stackoverflow.com/questions/44580702/android-room-persistent-library-how-to-insert-class-that-has-a-list-object-fie
 * https://mobikul.com/add-typeconverters-room-database-android/
 */

public class Utils {

    public static List<Ingredient> toIngredientList(String ingredientString) {
        if (ingredientString == null) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type stepListType = new TypeToken<List<Ingredient>>() {}.getType();
        return gson.fromJson(ingredientString, stepListType);
    }

    public static String toIngredientString (List<Ingredient> ingredientList) {
        if (ingredientList == null) {
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();
        return gson.toJson(ingredientList, listType);
    }
}

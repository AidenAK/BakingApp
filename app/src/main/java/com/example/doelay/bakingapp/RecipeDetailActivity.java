package com.example.doelay.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.doelay.bakingapp.model.Ingredient;
import com.example.doelay.bakingapp.model.Recipe;
import com.example.doelay.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 10/16/17.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private Recipe recipeSelected;
    private ArrayList<Ingredient> ingredientList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        // TODO: 10/17/17 Add recipe name on Appbar

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra == null) {
            return;
        } else {
            if (extra.containsKey("recipeSelected")) {
                recipeSelected = intent.getParcelableExtra("recipeSelected");
                Log.d(TAG, "onCreate: Recipe name is " + recipeSelected.getName());
            }
        }
        if(recipeSelected != null) {
            RecipeDetailFragment mRecipeDetailFragment = RecipeDetailFragment.newInstance(recipeSelected);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mRecipeDetailFragment).commit();
        }

    }
    public void onClickIngredients(View view) {
        // TODO: 11/13/17 check for dual pane layout
        Log.d(TAG, "onClickIngredient: Show me the ingredients!");
        if(recipeSelected != null) {
            ingredientList = (ArrayList) recipeSelected.getIngredients();
            IngredientFragment ingredientFragment = IngredientFragment.newInstance(ingredientList);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, ingredientFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}

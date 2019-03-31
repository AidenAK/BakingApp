package com.example.doelay.bakingapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.doelay.bakingapp.adapter.RecipeDetailAdapter;
import com.example.doelay.bakingapp.model.Ingredient;
import com.example.doelay.bakingapp.model.Recipe;
import com.example.doelay.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for hosting the fragments.
 */



public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailAdapter.RecipeDetailOnClickListener{


    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private static final String RECIPE_SELECTED = "recipe_selected";

    private Recipe recipeSelected;
    private RecipeDetailFragment mRecipeDetailFragment;
    private DetailStepFragment mDetailStepFragment;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Log.d(TAG, "onCreate: called!");

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle extra = intent.getExtras();
            if (extra == null) {
                return;
            } else {
                if (extra.containsKey("recipeSelected")) {
                    recipeSelected = intent.getParcelableExtra("recipeSelected");
                    Log.d(TAG, "onCreate: Recipe name is " + recipeSelected.getName());
                    mRecipeDetailFragment = RecipeDetailFragment.newInstance(recipeSelected);
                }
            }
        } else {
            recipeSelected = savedInstanceState.getParcelable(RECIPE_SELECTED);
            mRecipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager().findFragmentByTag("RECIPE_DETAIL_FRAGMENT");
            }


        getSupportActionBar().setTitle(recipeSelected.getName());
        mRecipeDetailFragment.setRecipeDetailOnClickListener(this);
        //check whether if tablet mode or not
        if (isTablet()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.two_pane_master, mRecipeDetailFragment, "RECIPE_DETAIL_FRAGMENT").commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.one_pane, mRecipeDetailFragment, "RECIPE_DETAIL_FRAGMENT").commit();
        }
    }

    @Override
    public void recipeDetailOnClickListener(int position) {
        Log.d(TAG, "recipeDetailOnClickListener: adapter position is " + position + "isTablet =" +isTablet());
        //load DetailStepFragment or ingredient lists if it is in tablet mode or start DetailStepActivity

        ArrayList<Step> stepList = (ArrayList) recipeSelected.getSteps();
        @SuppressWarnings("unchecked")
        ArrayList<Ingredient> ingredientList = (ArrayList) recipeSelected.getIngredients();
        Step step;

        // tablet mode
        if (isTablet() && position == 0) {
            Log.d(TAG, "recipeDetailOnClickListener: Tablet mode. Need to load ingredient list.");

            IngredientFragment ingredientFragment = new IngredientFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("ingredient_list", ingredientList);
            ingredientFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.two_pane_detail, ingredientFragment).commit();

        } else if (isTablet() && position > 0 && position <= stepList.size()) {
            Log.d(TAG, "recipeDetailOnClickListener: Tablet mode. Need to load DetailStepFragment.");

            step = stepList.get(position - 1); // index adjustment to retrieve the correct step
            mDetailStepFragment = new DetailStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("step", step);
            mDetailStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.two_pane_detail, mDetailStepFragment).commit();

        } else if (!isTablet() && position == 0) { //in phone mode
            Log.d(TAG, "recipeDetailOnClickListener: Phone mode. Need to load IngredientFragment through DetailStepActivity.");

            Intent intent = new Intent(this, DetailStepActivity.class);
            intent.putExtra("ingredient_list", ingredientList);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d(TAG, "recipeDetailOnClickListener: no activity to handle the intent");
            }
        } else if (!isTablet() && position > 0 && position <= stepList.size()) {
            Log.d(TAG, "recipeDetailOnClickListener: Phone mode. Need to load DetailStepFragment through DetailStepActivity.");

            int stepIndex = position - 1;
            Intent intent = new Intent(this, DetailStepActivity.class);
            intent.putExtra("step_index", stepIndex);
            intent.putParcelableArrayListExtra("step_list", stepList);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d(TAG, "recipeDetailOnClickListener: no activity to handle the intent");
            }
        }
    }

    private boolean isTablet() {
        return findViewById(R.id.two_pane) != null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called!");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: called!");
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_SELECTED, recipeSelected);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: called!");
    }


}

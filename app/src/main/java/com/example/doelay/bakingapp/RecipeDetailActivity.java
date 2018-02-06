package com.example.doelay.bakingapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.doelay.bakingapp.adapter.RecipeDetailAdapter;
import com.example.doelay.bakingapp.model.Recipe;
import com.example.doelay.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for hosting the fragments.
 */
// TODO: 11/16/17 need to implement back button for RecipeDetailactivity and each fragment
// TODO: 11/16/17 need to implement landscape layout for phone


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

                }
            }
        } else {
            recipeSelected = savedInstanceState.getParcelable(RECIPE_SELECTED);
            }


        getSupportActionBar().setTitle(recipeSelected.getName());
        mRecipeDetailFragment = RecipeDetailFragment.newInstance(recipeSelected, this);
        //check whether if tablet mode or not
        if (findViewById(R.id.two_pane) != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.two_pane_master, mRecipeDetailFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.one_pane, mRecipeDetailFragment).commit();
        }
    }

    @Override
    public void recipeDetailOnClickListener(int position) {
        Log.d(TAG, "recipeDetailOnClickListener: adapter position is " + position);
        //load DetailStepFragment if it is in tablet mode or start a DetailStepActivity
        // TODO: 2/6/18 need to check the position for "0" or not
        List<Step> stepList = recipeSelected.getSteps();
        Step step = stepList.get(position);

        if(findViewById(R.id.two_pane) != null) {
            //in tablet mode
            mDetailStepFragment = DetailStepFragment.newInstance(step);
            getSupportFragmentManager().beginTransaction().replace(R.id.two_pane_detail, mDetailStepFragment).commit();
        } else {
            //in phone mode
            Intent intent = new Intent(this, DetailStepActivity.class);
            intent.putExtra("step", step);

            if(intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d(TAG, "recipeDetailOnClickListener: no activity to handle the intent");
            }
        }
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

package com.example.doelay.bakingapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.doelay.bakingapp.adapter.StepAdapter;
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

public class RecipeDetailActivity extends AppCompatActivity
        implements StepAdapter.OnStepSelectedListener {


    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private static final String RECIPE_SELECTED = "recipe_selected";
    private static final String RECIPE_DETAIL_FRAGMENT = "recipe_detail_fragment";
    private Recipe recipeSelected;
    private ArrayList<Ingredient> ingredientList;
    private RecipeDetailFragment mRecipeDetailFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
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

        //set Appbar label
        getSupportActionBar().setTitle(recipeSelected.getName());
        //load fragment
        mRecipeDetailFragment = RecipeDetailFragment.newInstance(recipeSelected, this);
        if(findViewById(R.id.one_pane) != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.one_pane, mRecipeDetailFragment, RECIPE_DETAIL_FRAGMENT).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_master, mRecipeDetailFragment).commit();
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
        outState.putParcelable(RECIPE_SELECTED, recipeSelected);
        super.onSaveInstanceState(outState);
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

    public void onClickIngredients(View view) {

        ingredientList = (ArrayList) recipeSelected.getIngredients();
        IngredientFragment ingredientFragment = IngredientFragment.newInstance(ingredientList);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(findViewById(R.id.one_pane) != null) {
            transaction.replace(R.id.one_pane, ingredientFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            transaction.replace(R.id.fragment_container_detail, ingredientFragment);
            transaction.commit();
        }
    }

    @Override
    public void onStepSelected(int position) {
        Log.d(TAG, "onStepSelected: "+ position);

        Step step = recipeSelected.getSteps().get(position);
        DetailStepFragment detailStepFragment = DetailStepFragment.newInstance(step);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(findViewById(R.id.one_pane) != null) {
            transaction.replace(R.id.one_pane, detailStepFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            transaction.replace(R.id.fragment_container_detail, detailStepFragment);
            transaction.commit();
        }



    }
}

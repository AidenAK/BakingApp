package com.example.doelay.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doelay.bakingapp.model.Recipe;
import com.example.doelay.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * This class is responsible for displaying the ingredients and recipe steps
 */

public class RecipeDetailFragment extends Fragment {

    private static final String RECIPE_SELECTED = "recipe_selected";
    private Recipe recipeSelected;
    private ArrayList<Step> stepList;



    public static RecipeDetailFragment newInstance(Recipe recipeSelected){

        RecipeDetailFragment mRecipeDetailFragment = new RecipeDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("RecipeSelected", recipeSelected);
        mRecipeDetailFragment.setArguments(bundle);

        return mRecipeDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            recipeSelected = savedInstanceState.getParcelable(RECIPE_SELECTED);
        } else {
            if(getArguments() != null ) {
                recipeSelected = getArguments().getParcelable("RecipeSelected");
            }
        }

        //setup step fragment
        stepList = (ArrayList) recipeSelected.getSteps();
        StepFragment mStepFragment = StepFragment.newInstance(stepList);
        getChildFragmentManager().beginTransaction().add(R.id.step_frag_container, mStepFragment).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_SELECTED, recipeSelected);

    }
}

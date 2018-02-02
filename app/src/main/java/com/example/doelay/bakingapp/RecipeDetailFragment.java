package com.example.doelay.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doelay.bakingapp.model.Recipe;
import com.example.doelay.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class is responsible for displaying the ingredients and recipe steps
 */

public class RecipeDetailFragment extends Fragment {

    @BindView(R.id.rv_recipe_detail)
    RecyclerView RecipeDetailRecyclerView;

    private static final String RECIPE_SELECTED = "recipe_selected";
    private Recipe recipeSelected;
    private ArrayList<Step> stepList;

    public RecipeDetailFragment() {
       //empty constructor required
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(RECIPE_SELECTED, recipeSelected);
//
//    }
}

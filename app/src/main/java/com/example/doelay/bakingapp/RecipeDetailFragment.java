package com.example.doelay.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doelay.bakingapp.adapter.RecipeDetailAdapter;
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
    private RecipeDetailAdapter mRecipeDetailAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecipeDetailAdapter.RecipeDetailOnClickListener recipeDetailOnClickListener;

    public RecipeDetailFragment() {
       //empty constructor required
    }

    public static RecipeDetailFragment newInstance(Recipe recipe) {

        RecipeDetailFragment mRecipeDetailFragment = new RecipeDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_SELECTED, recipe);
        mRecipeDetailFragment.setArguments(bundle);

        return mRecipeDetailFragment;
    }

    public void setRecipeDetailOnClickListener(RecipeDetailAdapter.RecipeDetailOnClickListener callback){
        recipeDetailOnClickListener = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            recipeSelected = savedInstanceState.getParcelable(RECIPE_SELECTED);
        } else {
            if(getArguments() != null) {
                recipeSelected = getArguments().getParcelable(RECIPE_SELECTED);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getContext());
        RecipeDetailRecyclerView.setLayoutManager(mLayoutManager);
        RecipeDetailRecyclerView.setHasFixedSize(true);

        mRecipeDetailAdapter = new RecipeDetailAdapter(recipeDetailOnClickListener);
        RecipeDetailRecyclerView.setAdapter(mRecipeDetailAdapter);
        mRecipeDetailAdapter.setRecipe(recipeSelected);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_SELECTED, recipeSelected);

    }
}

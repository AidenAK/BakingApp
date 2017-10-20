package com.example.doelay.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doelay.bakingapp.adapter.IngredientAdapter;
import com.example.doelay.bakingapp.model.Ingredient;
import com.example.doelay.bakingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 10/18/17.
 */

public class IngredientFragment extends Fragment {

    @BindView(R.id.rv_ingredient)
    RecyclerView ingredientRecyclerView;

    private static final String TAG = Ingredient.class.getSimpleName();
    private LinearLayoutManager layoutManager;
    private IngredientAdapter ingredientAdapter;
    private List<Ingredient> ingredientList;
    private Recipe recipeSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {

            recipeSelected = getArguments().getParcelable("RecipeSelected");
            Log.d(TAG, "onCreate: "+ recipeSelected.getName());
            ingredientList = recipeSelected.getIngredients();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getContext());
        ingredientRecyclerView.setLayoutManager(layoutManager);

        ingredientAdapter = new IngredientAdapter();
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        ingredientRecyclerView.setHasFixedSize(true);
        ingredientAdapter.setIngredient(ingredientList);

        return view;
    }
}

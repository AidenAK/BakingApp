package com.example.doelay.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doelay.bakingapp.adapter.IngredientAdapter;
import com.example.doelay.bakingapp.model.Ingredient;
import com.example.doelay.bakingapp.model.Recipe;

import java.util.ArrayList;
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
    private static final String INGREDIENT_LIST = "ingredient_list";
    private LinearLayoutManager layoutManager;
    private IngredientAdapter ingredientAdapter;
    private ArrayList<Ingredient> ingredientList;


    public static IngredientFragment newInstance(ArrayList<Ingredient> list){

        IngredientFragment mFragment = new IngredientFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredientList", list);
        mFragment.setArguments(bundle);

        return mFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            ingredientList = savedInstanceState.getParcelableArrayList(INGREDIENT_LIST);
        } else {
            if(getArguments() != null) {
                ingredientList = getArguments().getParcelableArrayList("ingredientList");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getContext());
        ingredientRecyclerView.setLayoutManager(layoutManager);
        ingredientRecyclerView.setHasFixedSize(true);
//        ingredientRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        ingredientAdapter = new IngredientAdapter();
        ingredientRecyclerView.setAdapter(ingredientAdapter);
        ingredientAdapter.setIngredient(ingredientList);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INGREDIENT_LIST, ingredientList);
    }
}

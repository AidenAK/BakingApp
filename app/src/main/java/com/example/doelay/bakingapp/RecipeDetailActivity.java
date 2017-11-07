package com.example.doelay.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    @BindView(R.id.one_pane)
    LinearLayout singlePaneLayout;



    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private Recipe recipeSelected;
    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Step> stepList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_steps);

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

        if(singlePaneLayout != null) {

            //setup ingredient fragment
            ingredientList = (ArrayList) recipeSelected.getIngredients();
            IngredientFragment mIngredientFragment = IngredientFragment.newInstance(ingredientList);
            getSupportFragmentManager().beginTransaction().add(R.id.ingredient_frag_container, mIngredientFragment).commit();

            //setup step fragment
            stepList = (ArrayList) recipeSelected.getSteps();
            StepFragment mStepFragment = StepFragment.newInstance(stepList);
            getSupportFragmentManager().beginTransaction().add(R.id.step_frag_container, mStepFragment).commit();
        }
    }
}

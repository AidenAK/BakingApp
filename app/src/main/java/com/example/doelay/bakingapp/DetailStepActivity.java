package com.example.doelay.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.doelay.bakingapp.model.Ingredient;
import com.example.doelay.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * This class is responsible for loading ingredient list or ic_recipe detail step
 */

public class DetailStepActivity extends AppCompatActivity {
    public static final String TAG = DetailStepActivity.class.getSimpleName();


    private ArrayList<Ingredient> ingredientList;
    private Step step;
    private ArrayList<Step> stepList;
    private int stepIndex;
    private IngredientFragment ingredientFragment;
    private DetailStepFragment detailStepFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle extra = intent.getExtras();
            if (extra == null) {
                return;
            } else {
                if (extra.containsKey("ingredient_list")) {

                    ingredientList = intent.getParcelableArrayListExtra("ingredient_list");
                    ingredientFragment = new IngredientFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("ingredient_list", ingredientList);
                    ingredientFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ingredientFragment).commit();

                } else if (extra.containsKey("step_list") && extra.containsKey("step_index")) {

                    stepList = intent.getParcelableArrayListExtra("step_list");
                    stepIndex = intent.getIntExtra("step_index", -1);
                    if (stepIndex != -1) {
                        step = stepList.get(stepIndex);
                        detailStepFragment = DetailStepFragment.newInstance(step);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailStepFragment).commit();
                    }

                } else {
                    Log.d(TAG, "onCreate: Invalid extra.");
                }
            }
        } else {

            ingredientList = savedInstanceState.getParcelable("ingredient_list");
            step = savedInstanceState.getParcelable("step");
            stepList = savedInstanceState.getParcelableArrayList("step_list");
            stepIndex = savedInstanceState.getInt("step_index");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ingredient_list", ingredientList);
        outState.putParcelable("step", step);
        outState.putParcelableArrayList("step_lsit", stepList);
        outState.putInt("step_index", stepIndex);
    }
    public void onNavigationButtonClick(View view){

        switch(view.getId()) {
            case R.id.ib_previous :
                Log.d(TAG, "onNavigationButtonClick: previous");
                break;
            case R.id.ib_next :
                Log.d(TAG, "onNavigationButtonClick: next");
        }

    }

}

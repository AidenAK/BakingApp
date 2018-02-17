package com.example.doelay.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doelay.bakingapp.model.Ingredient;
import com.example.doelay.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * This class is responsible for loading ingredient list or ic_recipe detail step
 */

public class DetailStepActivity extends AppCompatActivity {
    public static final String TAG = DetailStepActivity.class.getSimpleName();



    @BindView(R.id.ib_next)
    ImageButton navigationNext;

    @BindView(R.id.ib_previous)
    ImageButton navigationPrevious;

    @BindView(R.id.tv_previous)
    TextView goToPrevious;

    @BindView(R.id.tv_next)
    TextView goToNext;

    private ArrayList<Ingredient> ingredientList;
    private Step step;
    private ArrayList<Step> stepList;
    private int currentStepIndex;
    private IngredientFragment ingredientFragment;
    private DetailStepFragment detailStepFragment;
    private int stepCount;


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
                    currentStepIndex = intent.getIntExtra("step_index", -1);
                    if (currentStepIndex != -1) {
                        step = stepList.get(currentStepIndex);
                        stepCount = stepList.size();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("step", step);
                        bundle.putInt("current_step_index", currentStepIndex);
                        bundle.putInt("step_count", stepCount);

//                        detailStepFragment = DetailStepFragment.newInstance(step);
                        detailStepFragment = new DetailStepFragment();
                        detailStepFragment.setArguments(bundle);
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
            currentStepIndex = savedInstanceState.getInt("step_index");
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ingredient_list", ingredientList);
        outState.putParcelable("step", step);
        outState.putParcelableArrayList("step_list", stepList);
        outState.putInt("step_index", currentStepIndex);
    }

    private void setNavigationButtonVisibility() {

        if (currentStepIndex == 0) {
            navigationPrevious.setVisibility(View.INVISIBLE);
            goToPrevious.setVisibility(View.INVISIBLE);
        }
        if (currentStepIndex == (stepCount - 1)) {
            navigationNext.setVisibility(View.INVISIBLE);
            goToNext.setVisibility(View.INVISIBLE);
        }
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

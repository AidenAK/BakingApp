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
import butterknife.ButterKnife;

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
        ButterKnife.bind(this);

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

                    //hide the navigation buttons since IngredientFragment doesn't need them
                    hideNavigationButton();

                } else if (extra.containsKey("step_list") && extra.containsKey("step_index")) {

                    stepList = intent.getParcelableArrayListExtra("step_list");
                    stepCount = stepList.size();
                    currentStepIndex = intent.getIntExtra("step_index", -1);
                    if (currentStepIndex != -1) {
                        startDetailStepFragment();
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
            stepCount = savedInstanceState.getInt("step_count");

            if(savedInstanceState.getBoolean("navigation_previous")) {
                navigationPrevious.setVisibility(View.VISIBLE);
                goToPrevious.setVisibility(View.VISIBLE);
            } else {
                navigationPrevious.setVisibility(View.INVISIBLE);
                goToPrevious.setVisibility(View.INVISIBLE);
            }
            if(savedInstanceState.getBoolean("navigation_next")) {
                navigationNext.setVisibility(View.VISIBLE);
                goToNext.setVisibility(View.VISIBLE);
            } else {
                navigationNext.setVisibility(View.INVISIBLE);
                goToNext.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void hideNavigationButton() {
        navigationNext.setVisibility(View.INVISIBLE);
        goToNext.setVisibility(View.INVISIBLE);
        navigationPrevious.setVisibility(View.INVISIBLE);
        goToPrevious.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ingredient_list", ingredientList);
        outState.putParcelable("step", step);
        outState.putParcelableArrayList("step_list", stepList);
        outState.putInt("step_index", currentStepIndex);
        outState.putInt("step_count", stepCount);

        if (isVisible(navigationNext)){
            outState.putBoolean("navigation_next", true);
        }else {
            outState.putBoolean("navigation_next", false);
        }
        if (isVisible(navigationPrevious)){
            outState.putBoolean("navigation_previous", true);
        }else {
            outState.putBoolean("navigation_previous", false);
        }
    }


    private Boolean isVisible(View view){
        if (view.getVisibility() == View.VISIBLE) {
             return true;
        } else {
            return false;
        }
    }

    /**
     * This method determine whether it should hide the navigation button Next or Previous
     */

    private void setNavigationButtonVisibility() {

        if (currentStepIndex == 0) {
            navigationPrevious.setVisibility(View.INVISIBLE);
            goToPrevious.setVisibility(View.INVISIBLE);
        } else if (currentStepIndex == (stepCount - 1)) {
            navigationNext.setVisibility(View.INVISIBLE);
            goToNext.setVisibility(View.INVISIBLE);
        } else {
            navigationPrevious.setVisibility(View.VISIBLE);
            goToPrevious.setVisibility(View.VISIBLE);
            navigationNext.setVisibility(View.VISIBLE);
            goToNext.setVisibility(View.VISIBLE);
        }
    }

    private void startDetailStepFragment() {
        step = stepList.get(currentStepIndex);

        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);

        detailStepFragment = new DetailStepFragment();
        detailStepFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailStepFragment).commit();
        setNavigationButtonVisibility();
    }
    public void onNavigationButtonClick(View view){

        switch(view.getId()) {
            case R.id.ib_previous :
                Log.d(TAG, "onNavigationButtonClick: previous");
                if(currentStepIndex != 0) {
                    currentStepIndex = currentStepIndex - 1;
                    startDetailStepFragment();
                }
                break;
            case R.id.ib_next :
                Log.d(TAG, "onNavigationButtonClick: next");
                if(currentStepIndex != stepCount - 1) {
                    currentStepIndex = currentStepIndex + 1;
                    startDetailStepFragment();
                }
        }

    }


}

package com.example.doelay.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.doelay.bakingapp.model.Recipe;
import com.example.doelay.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 11/13/17.
 */

public class RecipeDetailFragment extends Fragment {

    private Recipe recipeSelected;
    private ArrayList<Step> stepList;
    private static Context context;

    public static RecipeDetailFragment newInstance(Recipe recipeSelected, Context mContext){
        context = mContext;

        RecipeDetailFragment mRecipeDetailFragment = new RecipeDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("RecipeSelected", recipeSelected);
        mRecipeDetailFragment.setArguments(bundle);

        return mRecipeDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null ) {
            recipeSelected = getArguments().getParcelable("RecipeSelected");
        }

        //setup step fragment
        stepList = (ArrayList) recipeSelected.getSteps();
        StepFragment mStepFragment = StepFragment.newInstance(stepList, context);
        getChildFragmentManager().beginTransaction().add(R.id.step_frag_container, mStepFragment).commit();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_detail_steps, container, false);
        return view;
    }
}

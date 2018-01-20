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
import android.widget.LinearLayout;

import com.example.doelay.bakingapp.adapter.StepAdapter;
import com.example.doelay.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class is responsible for displaying recipe steps.
 */

public class StepFragment extends Fragment {

    @BindView(R.id.rv_step)
    RecyclerView stepRecyclerView;

    private static final String TAG = StepFragment.class.getSimpleName();
    private static final String STEP_LIST = "step_list";
    private ArrayList<Step> stepList;
    private LinearLayoutManager layoutManager;
    private StepAdapter stepAdapter;

    public static StepFragment newInstance(ArrayList<Step> list) {

        StepFragment mFragment = new StepFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("stepList", list);
        mFragment.setArguments(bundle);

        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            stepList = savedInstanceState.getParcelableArrayList(STEP_LIST);
        } else {
            if(getArguments() != null) {
                stepList = getArguments().getParcelableArrayList("stepList");
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getContext());
        stepRecyclerView.setLayoutManager(layoutManager);
        stepRecyclerView.setHasFixedSize(true);

        stepAdapter = new StepAdapter();
        stepRecyclerView.setAdapter(stepAdapter);
        stepAdapter.setStep(stepList);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO: 11/17/17 need to save recycler view state
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_LIST, stepList);
    }


}

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
 * Created by doelay on 11/6/17.
 */

public class StepFragment extends Fragment {

    @BindView(R.id.rv_step)
    RecyclerView stepRecyclerView;

    private static final String TAG = StepFragment.class.getSimpleName();
    private List<Step> stepList;
    private LinearLayoutManager layoutManager;
    private StepAdapter stepAdapter;
    private static Context context;

    public static StepFragment newInstance(ArrayList<Step> list, Context mContext) {
        context = mContext;
        StepFragment mFragment = new StepFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("stepList", list);
        mFragment.setArguments(bundle);

        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            stepList = getArguments().getParcelableArrayList("stepList");
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

        stepAdapter = new StepAdapter(context);
        stepRecyclerView.setAdapter(stepAdapter);
        stepAdapter.setStep(stepList);

        return view;
    }
}

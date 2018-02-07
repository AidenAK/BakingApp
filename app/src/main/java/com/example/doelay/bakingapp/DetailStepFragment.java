package com.example.doelay.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doelay.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 11/13/17.
 */

public class DetailStepFragment extends Fragment{

    @BindView(R.id.tv_description)
    TextView description;
    // TODO: 2/6/18 need to add video player functionality
    // TODO: 2/7/18 need to create a full screen layout for video player in landscape mode


    private Step step;

    public static DetailStepFragment newInstance(Step step) {
        DetailStepFragment detailStepFragment = new DetailStepFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("Step", step);
        detailStepFragment.setArguments(bundle);

        return detailStepFragment;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            step = getArguments().getParcelable("Step");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_step, container, false);
        ButterKnife.bind(this, view);
        description.setText(step.getDescription());
        return view;
    }
}

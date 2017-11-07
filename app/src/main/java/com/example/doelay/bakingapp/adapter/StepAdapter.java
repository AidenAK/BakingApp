package com.example.doelay.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doelay.bakingapp.R;
import com.example.doelay.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by doelay on 11/6/17.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private static final String TAG = StepAdapter.class.getSimpleName();
    private List<Step> stepList;

    @Override
    public StepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapterViewHolder holder, int position) {
        if(stepList != null || stepList.size() != 0) {
            Step step = stepList.get(position);
            holder.shortDescription.setText(step.getShortDescription());
        } else {
            Log.d(TAG, "onBindViewHolder: Check the step list!");
        }

    }

    @Override
    public int getItemCount() {
        if(stepList == null)
        {
            return 0;
        }
        return stepList.size();
    }

    public void setStep(List<Step> stepList) {
        this.stepList = stepList;
        notifyDataSetChanged();
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_short_description)
        TextView shortDescription;

        public StepAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

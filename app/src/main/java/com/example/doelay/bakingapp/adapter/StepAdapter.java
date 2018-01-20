package com.example.doelay.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doelay.bakingapp.R;
import com.example.doelay.bakingapp.RecipeDetailActivity;
import com.example.doelay.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.media.CamcorderProfile.get;


/**
 * Created by doelay on 11/6/17.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private static final String TAG = StepAdapter.class.getSimpleName();
    private List<Step> stepList;
    private static OnStepSelectedListener callback;

    public interface OnStepSelectedListener{
        void onStepSelected(int position);
    }

    public static void setCallback(Context mContext) {
        try {
            callback = (OnStepSelectedListener) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString() + " must implement OnStepSelectedListener");
        }
    }

    public StepAdapter(){

    }

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

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.tv_short_description)
        TextView shortDescription;

        public StepAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View mView) {
            int adapterPosition = getAdapterPosition();
            callback.onStepSelected(adapterPosition);

        }


    }
}

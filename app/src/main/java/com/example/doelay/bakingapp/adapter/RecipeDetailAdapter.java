package com.example.doelay.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doelay.bakingapp.R;
import com.example.doelay.bakingapp.model.Recipe;
import com.example.doelay.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 2/2/18.
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailAdapterViewHolder> {

    private static final String TAG = RecipeDetailAdapter.class.getSimpleName();

    private static final int VIEW_TYPE_INGREDIENT = 0;
    private static final int VIEW_TYPE_STEP = 1;

    private ArrayList<Step> stepList;
    private Recipe recipeSelected;
    private RecipeDetailOnClickListener callback;

    public interface RecipeDetailOnClickListener {
        void recipeDetailOnClickListener (int position);
    }

    public RecipeDetailAdapter(Context context){
        try {
            callback = (RecipeDetailOnClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement RecipeDetailOnClickListener");
        }
    }


    @Override
    public RecipeDetailAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecipeDetailAdapterViewHolder viewHolder = null;

        switch(viewType) {
            case VIEW_TYPE_INGREDIENT: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_detail_ingredient, parent, false);
                viewHolder = new IngredientViewHolder(view);
                break;
            }
            case VIEW_TYPE_STEP: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_detail_step, parent, false);
                viewHolder = new StepViewHolder(view);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid view type." + viewType);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeDetailAdapterViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (stepList.size() != 0){
            return stepList.size() + 1;
        } else {
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == VIEW_TYPE_INGREDIENT){
            return VIEW_TYPE_INGREDIENT;
        } else {
            return VIEW_TYPE_STEP;
        }
    }

    public void setRecipe(Recipe recipe){
        recipeSelected = recipe;
        stepList = (ArrayList) recipeSelected.getSteps();
        notifyDataSetChanged();
    }

    public abstract class RecipeDetailAdapterViewHolder extends RecyclerView.ViewHolder {

        // TODO: 2/2/18 add OnClickListener
        public abstract void bind(int position);

        public RecipeDetailAdapterViewHolder(View view) {
            super(view);
        }

    }

    /**
     * ViewHolder for ingredient
     */

    public class IngredientViewHolder extends RecipeDetailAdapterViewHolder {

        public IngredientViewHolder(View view){
            super(view);
        }

        @Override
        public void bind(int position) {
            //the text to display is already in the layout
        }
    }

    /**
     * ViewHolder for step
     */

    public class StepViewHolder extends RecipeDetailAdapterViewHolder {
        @BindView(R.id.tv_short_description)
        TextView shortDescription;



        public StepViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void bind(int position) {
            if(stepList != null || stepList.size() != 0) {
                int index = position - 1;//make adjustment to get the correct index
                Step step = stepList.get(index);
                this.shortDescription.setText(step.getShortDescription());
            }
        }
    }
}

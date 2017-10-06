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

import java.util.List;

/**
 * Created by doelay on 10/5/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {
    private static final String TAG = RecipeAdapter.class.getSimpleName();

    private List<Recipe> mRecipeList;
    private OnRecipeSelectedListener callback;
    private Context mContext;

    public interface OnRecipeSelectedListener {
        void onRecipeSelected(int id);
    }

    public RecipeAdapter(Context mContext) {
        try {
            callback = (OnRecipeSelectedListener) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString() + " must implement OnRecipeSelectedListener");
        }

    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_name, parent, false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        if(mRecipeList != null && mRecipeList.size() != 0) {
            Recipe mRecipe = mRecipeList.get(position);
            Log.d(TAG, "onBindViewHolder: " + mRecipe.getName());
            holder.recipeName.setText(mRecipe.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) {
            return 0;
        }
        Log.d(TAG, "getItemCount: "+ mRecipeList.size());
        return mRecipeList.size();
    }

    public void setRecipeList(List<Recipe> list) {
        mRecipeList = list;
        notifyDataSetChanged();
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            recipeName = (TextView) view.findViewById(R.id.tv_recipe_name);
        }
    }
}

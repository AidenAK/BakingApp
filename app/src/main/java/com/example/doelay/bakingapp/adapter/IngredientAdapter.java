package com.example.doelay.bakingapp.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doelay.bakingapp.R;
import com.example.doelay.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 10/18/17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    private static final String TAG = IngredientAdapter.class.getSimpleName();
    private List<Ingredient> ingredientList;

    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder holder, int position) {
        if(ingredientList != null && ingredientList.size() != 0) {
            Ingredient ingredient = ingredientList.get(position);
            holder.quantity.setText(String.valueOf(ingredient.getQuantity()));
            holder.measure.setText(ingredient.getMeasure());
            holder.ingredient.setText(ingredient.getIngredient());
        } else {
            Log.d(TAG, "onBindViewHolder: Check the ingredient list!");
        }
    }

    @Override
    public int getItemCount() {
        if(ingredientList == null) {
            return 0;
        }
        return ingredientList.size();
    }

    public void setIngredient(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_quantity)
        TextView quantity;
        @BindView(R.id.tv_measure)
        TextView measure;
        @BindView(R.id.tv_ingredient)
        TextView ingredient;

        public IngredientAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

package com.example.doelay.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.doelay.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 10/16/17.
 */
// TODO: 10/17/17 Add recipe name on Appbar
public class RecipeDetailActivity extends AppCompatActivity {

    @BindView(R.id.one_pane)
    LinearLayout singlePaneLayout;

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private Recipe recipeSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_steps);

        ButterKnife.bind(this);

        // TODO: 10/17/17 retrieve data
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra == null) {
            return;
        } else {
            if (extra.containsKey("RecipeSelected")) {
                recipeSelected = intent.getParcelableExtra("RecipeSelected");
                Log.d(TAG, "onCreate: Recipe name is " + recipeSelected.getName());
            }
        }

        if(singlePaneLayout != null) {
            // TODO: 10/17/17 load fragment related to one pane mode
            
        }
    }
}

package com.example.doelay.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 10/16/17.
 */
// TODO: 10/17/17 Add recipe name on Appbar
public class RecipeDetailActivity extends AppCompatActivity {

    @BindView(R.id.one_pane)
    LinearLayout singlePaneLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_steps);

        ButterKnife.bind(this);
        // TODO: 10/17/17 retrieve data
         
        if(singlePaneLayout != null) {
            // TODO: 10/17/17 load fragment related to one pane mode
            
        }
    }
}

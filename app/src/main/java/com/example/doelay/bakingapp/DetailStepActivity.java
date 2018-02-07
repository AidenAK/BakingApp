package com.example.doelay.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by doelay on 2/6/18.
 */

public class DetailStepActivity extends AppCompatActivity {

    // TODO: 2/6/18 get the extra from intent
    // TODO: 2/6/18 need to load DetailStepFragment
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);
        getSupportActionBar().setTitle("Details");
    }
}

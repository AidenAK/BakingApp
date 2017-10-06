package com.example.doelay.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.doelay.bakingapp.adapter.RecipeAdapter;
import com.example.doelay.bakingapp.model.Recipe;
import com.example.doelay.bakingapp.networking.ApiBaseUrl;
import com.example.doelay.bakingapp.networking.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements RecipeAdapter.OnRecipeSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recipeRecyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;// to restore data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeRecyclerView = (RecyclerView) findViewById(R.id.rv_recipe);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recipeRecyclerView.setLayoutManager(layoutManager);
        recipeRecyclerView.setHasFixedSize(true);


        recipeAdapter = new RecipeAdapter(this);
        recipeRecyclerView.setAdapter(recipeAdapter);


        makeApiRequest();



    }

    private void makeApiRequest() {

        ApiService mApiService = ApiBaseUrl.getRetrofit().create(ApiService.class);
        mApiService.getRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if(response.isSuccessful()) {
                    recipeList = response.body();//save a copy to restore data
                    recipeAdapter.setRecipeList(recipeList);//pass the data to the adapter
                    Log.d(TAG, "onResponse: " + recipeList.size());
                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // TODO: 10/4/17 show error message.
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }

    @Override
    public void onRecipeSelected(int position) {
        // TODO: 10/5/17 open recipe detail
        Log.d(TAG, "onRecipeSelected: "+ position);
    }
}

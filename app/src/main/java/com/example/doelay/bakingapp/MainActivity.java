package com.example.doelay.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.doelay.bakingapp.model.Recipe;
import com.example.doelay.bakingapp.networking.ApiBaseUrl;
import com.example.doelay.bakingapp.networking.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiService mApiService = ApiBaseUrl.getRetrofit().create(ApiService.class);
        mApiService.getRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if(response.isSuccessful()) {
                    List<Recipe> mReceipeList = response.body();
                    Log.d(TAG, "onResponse: " + mReceipeList.size());
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

}

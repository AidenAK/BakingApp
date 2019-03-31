package com.example.doelay.bakingapp.networking;

import com.example.doelay.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiService {

    @GET("2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipe();
}

package com.example.doelay.bakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.doelay.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.doelay.bakingapp.adapter.RecipeAdapter;
import com.example.doelay.bakingapp.model.Ingredient;
import com.example.doelay.bakingapp.model.Recipe;
import com.example.doelay.bakingapp.networking.ApiBaseUrl;
import com.example.doelay.bakingapp.networking.ApiService;
import com.example.doelay.bakingapp.widget.RecipeWidgetProvider;
import com.example.doelay.bakingapp.widget.UpdateRecipeWidgetIntentService;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements RecipeAdapter.OnRecipeSelectedListener {

    @BindView(R.id.rv_recipe)
    RecyclerView recipeRecyclerView;
    @BindView(R.id.pb_loading)
    ProgressBar loadingBar;
    @BindView(R.id.tv_error_loading)
    TextView errorLoading;
    @BindInt(R.integer.number_of_column)
    int numberOfColumn;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String RECIPE_RECYCLER_VIEW_STATE = "recipe_recycler_view_state";
    private static final String DOWNLOADED_RECIPE_LIST = "downloaded_recipe_list";

    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;// to restore data
    private GridLayoutManager layoutManager;
    private Parcelable recyclerViewState;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResoruce() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        layoutManager = new GridLayoutManager(this, numberOfColumn);
        recipeRecyclerView.setLayoutManager(layoutManager);
        recipeRecyclerView.setHasFixedSize(true);

        recipeAdapter = new RecipeAdapter(this);
        recipeRecyclerView.setAdapter(recipeAdapter);

        getIdlingResoruce();

        if(savedInstanceState == null) {
            makeApiRequest();
        } else {
            recipeList = savedInstanceState.getParcelableArrayList(DOWNLOADED_RECIPE_LIST);
            recipeAdapter.setRecipeList(recipeList);
            recyclerViewState = savedInstanceState.getParcelable(RECIPE_RECYCLER_VIEW_STATE);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the recycler view state
        recyclerViewState = layoutManager.onSaveInstanceState();
        outState.putParcelable(RECIPE_RECYCLER_VIEW_STATE, recyclerViewState);
        //save the downloaded recipe list
        outState.putParcelableArrayList(DOWNLOADED_RECIPE_LIST, (ArrayList<Recipe>) recipeList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //restore the recycler view state
        if(recyclerViewState != null) {
            layoutManager.onRestoreInstanceState(recyclerViewState);
        }
        showRecipeList();
    }

    private void makeApiRequest() {

        if(mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

        showLoadingBar();
        ApiService mApiService = ApiBaseUrl.getRetrofit().create(ApiService.class);
        mApiService.getRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if(response.isSuccessful()) {
                    recipeList = response.body();//save a copy to restore data
                    recipeAdapter.setRecipeList(recipeList);//pass the data to the adapter

                    showRecipeList();

                    Log.d(TAG, "onResponse: " + recipeList.size());
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, "onResponse: status code is "+ statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                showErrorMessage();
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
        mIdlingResource.setIdleState(true);
    }

    @Override
    public void onRecipeSelected(int position) {

        Log.d(TAG, "onRecipeSelected: "+ recipeList.get(position).getName());

        Recipe recipeSelected = recipeList.get(position);

        Intent intent = new Intent(MainActivity.this, RecipeDetailActivity.class);
        intent.putExtra("recipeSelected", recipeSelected);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "onRecipeSelected: No activity to handle the intent!");
        }
    }

    @Override
    public void onRecipeSelectedForWidget(int position) {

        Recipe recipeSelectedForWidget = recipeList.get(position);

        String ingredientListString = Utils.toIngredientString(recipeSelectedForWidget.getIngredients());

        //save the ingredientListString in SharePreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ingredient_list_string",ingredientListString);
        editor.putString("recipe_name", recipeSelectedForWidget.getImage());
        Log.d(TAG, "onRecipeSelectedForWidget: "+ ingredientListString);
        editor.apply();

        UpdateRecipeWidgetIntentService.startActionUpdateRecipeWidget(this, recipeSelectedForWidget);
    }

    private void showLoadingBar() {
        loadingBar.setVisibility(View.VISIBLE);
    }


    private void showRecipeList() {
        loadingBar.setVisibility(View.INVISIBLE);
        recipeRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        loadingBar.setVisibility(View.INVISIBLE);
        errorLoading.setVisibility(View.VISIBLE);
    }
}

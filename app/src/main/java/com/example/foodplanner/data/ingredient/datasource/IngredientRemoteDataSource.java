package com.example.foodplanner.data.ingredient.datasource;

import android.util.Log;

import com.example.foodplanner.data.ingredient.model.Ingredient;
import com.example.foodplanner.data.ingredient.model.IngredientResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientRemoteDataSource {
    private final Services ingredientService;

    public IngredientRemoteDataSource() {
        ingredientService = Network.getInstance().services;
    }

    public void getAllIngredients(IngredientNetworkResponse callback){
        ingredientService.getAllIngredientsList().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Ingredient> ingredients = response.body().getAllIngredientsList();
                    if (ingredients != null) {
                        callback.onSuccess(ingredients);
                    } else {
                        callback.onError("No ingredients found");
                    }
                } else {
                    callback.onError("Response empty or failed");
                    Log.e("IngredientRemoteDataSource", "Response failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                Log.e("IngredientRemoteDataSource", "API call failed", t);
                callback.onError(t.getMessage());
            }
        });
    }

}

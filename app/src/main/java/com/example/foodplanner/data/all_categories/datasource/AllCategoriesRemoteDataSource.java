package com.example.foodplanner.data.all_categories.datasource;

import android.util.Log;

import com.example.foodplanner.data.all_categories.model.AllCategories;
import com.example.foodplanner.data.all_categories.model.AllCategoriesResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategoriesRemoteDataSource {
    private final Services categoryFilterService;

    public AllCategoriesRemoteDataSource() {
        categoryFilterService = Network.getInstance().services;
    }

    public void getAllCategoriesFilter(AllCategoriesNetworkResponse callback){
        categoryFilterService.getAllCategoriesList().enqueue(new Callback<AllCategoriesResponse>() {
            @Override
            public void onResponse(Call<AllCategoriesResponse> call, Response<AllCategoriesResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<AllCategories> categoriesFilter = response.body().getCategoriesFilter();
                    if (categoriesFilter != null) {
                        callback.onSuccess(categoriesFilter);
                    } else {
                        callback.onError("No categoriesFilter found");
                    }
                } else {
                    callback.onError("Response empty or failed");
                    Log.e("CategoryFilterRemoteDataSource", "Response failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AllCategoriesResponse> call, Throwable t) {
                Log.e("CategoryFilterRemoteDataSource", "API call failed", t);
                callback.onError(t.getMessage());
            }
        });
    }
}

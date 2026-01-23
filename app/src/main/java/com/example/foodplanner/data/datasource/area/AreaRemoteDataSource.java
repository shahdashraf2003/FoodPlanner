package com.example.foodplanner.data.datasource.area;

import android.util.Log;

import com.example.foodplanner.data.models.area.Area;
import com.example.foodplanner.data.models.area.AreaResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AreaRemoteDataSource {
    private final Services araeServices;

    public AreaRemoteDataSource() {

        araeServices = Network.getInstance().services;
    }

    public void getAllAreasList(AreaNetworkResponse callback){
        araeServices.getAllAreasList().enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                Log.d("AreaRemoteDS", "Response body: " + response.body());
                if(response.isSuccessful() && response.body() != null){
                    List<Area> areas = response.body().getAllAreas();
                    Log.d("AreaRemoteDS", "Areas list: " + areas);
                    if (areas != null) {
                        callback.onSuccess(areas);
                    } else {
                        callback.onError("No areas found");
                    }
                } else {
                    callback.onError("Response empty or failed");
                    Log.e("AreaRemoteDS", "Response failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                Log.e("AreaRemoteDataSource", "API call failed", t);
                callback.onError(t.getMessage());
            }
        });
    }
}

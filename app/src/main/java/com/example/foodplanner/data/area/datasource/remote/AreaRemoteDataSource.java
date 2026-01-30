package com.example.foodplanner.data.area.datasource.remote;

import com.example.foodplanner.data.area.model.AreaResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import io.reactivex.rxjava3.core.Single;


public class AreaRemoteDataSource {
    private final Services araeServices;

    public AreaRemoteDataSource() {

        araeServices = Network.getInstance().services;
    }

    public Single<AreaResponse> getAllAreasList(){
        return araeServices.getAllAreasList();

    }
}

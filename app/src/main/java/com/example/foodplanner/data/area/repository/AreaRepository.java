package com.example.foodplanner.data.area.repository;

import android.content.Context;

import com.example.foodplanner.data.area.datasource.remote.AreaRemoteDataSource;
import com.example.foodplanner.data.area.model.AreaResponse;

import io.reactivex.rxjava3.core.Single;

public class AreaRepository {

    private AreaRemoteDataSource areaRemoteDataSource;

    public AreaRepository(Context context) {
        this.areaRemoteDataSource = new AreaRemoteDataSource();
    }

    public Single<AreaResponse> getAllAreasList() {
        return areaRemoteDataSource.getAllAreasList();
    }
}

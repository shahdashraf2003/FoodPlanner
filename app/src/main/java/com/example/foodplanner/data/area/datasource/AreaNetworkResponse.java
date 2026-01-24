package com.example.foodplanner.data.area.datasource;

import com.example.foodplanner.data.area.model.Area;

import java.util.List;

public interface AreaNetworkResponse {
    void onSuccess (List<Area> areas);
    void onError (String errorMsg);
    void onLoading();
}

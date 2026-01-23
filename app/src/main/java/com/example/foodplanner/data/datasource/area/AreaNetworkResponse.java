package com.example.foodplanner.data.datasource.area;

import com.example.foodplanner.data.models.area.Area;

import java.util.List;

public interface AreaNetworkResponse {
    void onSuccess (List<Area> areas);
    void onError (String errorMsg);
    void onLoading();
}

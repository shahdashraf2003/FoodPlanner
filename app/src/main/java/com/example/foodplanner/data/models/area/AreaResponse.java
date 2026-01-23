package com.example.foodplanner.data.models.area;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {
    @SerializedName("meals")
    List<Area> areas;

    public List<Area> getAllAreas() {
        return areas;
    }
}

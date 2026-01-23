package com.example.foodplanner.data.models.area;

import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName("strArea")
    private String strArea;

    public Area(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}

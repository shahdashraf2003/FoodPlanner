package com.example.foodplanner;


import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("idCategory")
    private String idCategory;

    @SerializedName("strCategory")
    private String strCategory;

    @SerializedName("strCategoryThumb")
    private String strCategoryThumb;

    @SerializedName("strCategoryDescription")
    private String strCategoryDescription;

    public String getIdCategory() {
        return idCategory != null ? idCategory : "";
    }

    public String getStrCategory() {
        return strCategory != null ? strCategory : "";
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb != null ? strCategoryThumb : "";
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription != null ? strCategoryDescription : "";
    }
}

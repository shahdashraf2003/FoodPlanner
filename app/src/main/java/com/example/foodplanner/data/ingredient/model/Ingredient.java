package com.example.foodplanner.data.ingredient.model;


import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("idIngredient")
    private String id;

    @SerializedName("strIngredient")
    private String name;

    @SerializedName("strDescription")
    private String description;

    @SerializedName("strThumb")
    private String imageUrl;

    @SerializedName("strType")
    private String type;

    public Ingredient(String salt) {
    }


    public String getId() {
        return id != null ? id : "";
    }

    public String getName() {
        return name != null ? name : "";
    }

    public String getDescription() {
        return description != null ? description : "";
    }

    public String getImageUrl() {
        return imageUrl != null ? imageUrl : "";
    }

    public String getType() {
        return type != null ? type : "";
    }
}

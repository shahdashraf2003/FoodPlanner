package com.example.foodplanner.data.category.datasource.remote;

import com.example.foodplanner.data.category.model.CategoryResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import io.reactivex.rxjava3.core.Single;

public class CategoryRemoteDataSource {
    private final Services categoryService;

    public CategoryRemoteDataSource() {
        categoryService = Network.getInstance().services;
    }

    public Single<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();

    }
}

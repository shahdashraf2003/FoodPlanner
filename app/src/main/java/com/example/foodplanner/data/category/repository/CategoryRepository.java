package com.example.foodplanner.data.category.repository;

import android.content.Context;

import com.example.foodplanner.data.category.datasource.remote.CategoryRemoteDataSource;
import com.example.foodplanner.data.category.model.CategoryResponse;

import io.reactivex.rxjava3.core.Single;

public class CategoryRepository {
    private CategoryRemoteDataSource categoryRemoteDataSource;
    public CategoryRepository(Context context) {
        categoryRemoteDataSource = new CategoryRemoteDataSource();
    }
    public Single<CategoryResponse> getAllCategories() {
        return categoryRemoteDataSource.getAllCategories();
    }

}

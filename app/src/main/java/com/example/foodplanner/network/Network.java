package com.example.foodplanner.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    public MealService mealService;
 private static final String BASE_URL="https://www.themealdb.com/api/json/v1/1/";
 private static Network instance =null;


    public Network() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Network.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService =retrofit.create(MealService.class);
    }

    public static Network getInstance(){
        if (instance==null)
        {
            instance=new Network();
        }
        return instance;
    }



}

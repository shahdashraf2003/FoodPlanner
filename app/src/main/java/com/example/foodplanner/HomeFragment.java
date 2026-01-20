package com.example.foodplanner;

import static com.example.foodplanner.R.id.iv_meal;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.datasource.MealNetworkResponse;
import com.example.foodplanner.datasource.MealRemoteDataSource;
import com.example.foodplanner.model.Meal;

import java.util.List;


public class HomeFragment extends Fragment {
public static String TAG="Home Fra";
ImageView iv_meal;
TextView name , desc;
MealRemoteDataSource mealRemoteDataSource;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealRemoteDataSource=new MealRemoteDataSource();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_home, container, false);
        View mealCardView = view.findViewById(R.id.meal_card_include);
        iv_meal=mealCardView.findViewById(R.id.iv_meal);
        name=mealCardView.findViewById(R.id.tv_name);
        desc=mealCardView.findViewById(R.id.tv_desc);
        Log.d(TAG, "iv_meal = " + iv_meal);
        Log.d(TAG, "name = " + name);
        Log.d(TAG, "desc = " + desc);
        mealRemoteDataSource.getRandomMeal(
                new MealNetworkResponse() {
                    @Override
                    public void onSuccess(List<Meal> meal) {
                        System.out.println(meal.get(0).getStrMeal());
                        Log.d(TAG, "onSuccess: "+meal.get(0).getStrMeal());
                        Log.d(TAG, "Meal image URL: " + meal.get(0).getStrMealThumb());
                        Toast.makeText(getContext(), "succ"+meal.get(0).getStrMeal(), Toast.LENGTH_SHORT).show();

                        name.setText(meal.get(0).getStrMeal());
                        desc.setText(meal.get(0).getStrInstructions());
                        Glide.with(requireContext())
                                .load(meal.get(0).getStrMealThumb())
                                .into(iv_meal);

                    }

                    @Override
                    public void onError(String errorMsg) {

                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onLoading() {
                        Toast.makeText(getContext(), "loading", Toast.LENGTH_SHORT).show();

                    }

                }

        );
        return view;

    }
}
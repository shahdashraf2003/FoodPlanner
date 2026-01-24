package com.example.foodplanner.prsentation.filtered_meals.view;

import static androidx.core.util.TypedValueCompat.dpToPx;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.data.mealsfilterby.datasource.MealFilterByDataSource;
import com.example.foodplanner.data.mealsfilterby.model.MealFilterBy;
import com.example.foodplanner.prsentation.filtered_meals.presenter.FilteredMealsPresenter;
import com.example.foodplanner.prsentation.filtered_meals.presenter.FilteredMealsPresenterImp;
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsFragment;

import java.util.List;

public class FilteredMealsFragment extends Fragment implements FilteredMealsView{

    private String areaName ,category_name,ingredientName;
    private FilteredMealsPresenter filteredMealsPresenter;
    private RecyclerView recyclerView;
    private FilteredMealAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        filteredMealsPresenter = new FilteredMealsPresenterImp(requireContext(),this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_filtered_meals, container, false);
        recyclerView = view.findViewById(R.id.filteredRecyclerView);

        adapter = new FilteredMealAdapter((meal) -> {
           openMealDetails(meal);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        Bundle bundle = getArguments();
        if (bundle != null) {
            areaName = bundle.getString("area_name");
            ingredientName = bundle.getString("ingredient_name");
            category_name = bundle.getString("category_name");
        }
        if (category_name != null) {
            filteredMealsPresenter.filterByCategory(category_name);

        } else if (ingredientName != null) {
            filteredMealsPresenter.filterByIngredient(ingredientName);

        } else if (areaName != null) {
            filteredMealsPresenter.filterByArea(areaName);
        }

        return view;
    }

    private void openMealDetails(MealFilterBy meal) {
        MealDetailsFragment fragment = new MealDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("idMeal", meal.getIdMeal());
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();


    }


    @Override
    public void onFilteredMealsByCategoryError(String errMsg) {

    }

    @Override
    public void onFilteredMealsByCategoryLoading() {

    }

    @Override
    public void onFilteredMealsByCategorySuccess(List<MealFilterBy> meals) {
        adapter.setMealList(meals);
    }

    @Override
    public void onFilteredMealsByIngredientError(String errMsg) {

    }

    @Override
    public void onFilteredMealsByIngredientLoading() {

    }

    @Override
    public void onFilteredMealsByIngredientSuccess(List<MealFilterBy> meals) {
        adapter.setMealList(meals);

    }

    @Override
    public void onFilteredMealsByCountryError(String errMsg) {

    }

    @Override
    public void onFilteredMealsByCountryLoading() {

    }

    @Override
    public void onFilteredMealsByCountrySuccess(List<MealFilterBy> meals) {
        adapter.setMealList(meals);

    }
}
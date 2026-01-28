package com.example.foodplanner.prsentation.filtered_meals.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.data.mealsfilterby.model.MealFilterBy;
import com.example.foodplanner.prsentation.filtered_meals.presenter.FilteredMealsPresenter;
import com.example.foodplanner.prsentation.filtered_meals.presenter.FilteredMealsPresenterImp;
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsFragment;
import com.example.foodplanner.utils.NetworkConnectionObserver;
import com.example.foodplanner.utils.NoInternetDialog;

import java.util.List;

public class FilteredMealsFragment extends Fragment implements FilteredMealsView {

    private String areaName, categoryName, ingredientName;
    private FilteredMealsPresenter filteredMealsPresenter;
    private RecyclerView recyclerView;
    private FilteredMealAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvError;

    private NetworkConnectionObserver networkObserver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filteredMealsPresenter = new FilteredMealsPresenterImp(requireContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtered_meals, container, false);
        recyclerView = view.findViewById(R.id.filteredRecyclerView);
        progressBar = view.findViewById(R.id.progressBarFiltered);
        tvError = view.findViewById(R.id.tvErrorFiltered);
        adapter = new FilteredMealAdapter(this::openMealDetails);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        NoInternetDialog noInternetDialog = new NoInternetDialog(requireContext());



        networkObserver = new NetworkConnectionObserver(requireContext(), new NetworkConnectionObserver.NetworkListener() {
            @Override
            public void onNetworkLost() {
                Log.d("No", "onNetworkLost: ");
                System.out.println("onNetworkLost");
                requireActivity().runOnUiThread(() -> noInternetDialog.showDialog());

            }

            @Override
            public void onNetworkAvailable() {
                requireActivity().runOnUiThread(() -> noInternetDialog.hideDialog());
            }
        });

    Bundle bundle = getArguments();
        if (bundle != null) {
            areaName = bundle.getString("area_name");
            ingredientName = bundle.getString("ingredient_name");
            categoryName = bundle.getString("category_name");
        }

        loadFilteredMeals();

        return view;
    }

    private void loadFilteredMeals() {
        if (categoryName != null) {
            filteredMealsPresenter.filterByCategory(categoryName);
        } else if (ingredientName != null) {
            filteredMealsPresenter.filterByIngredient(ingredientName);
        } else if (areaName != null) {
            filteredMealsPresenter.filterByArea(areaName);
        } else {
            showError("No filter selected");
        }
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }

    private void showError(String message) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private void showMeals(List<MealFilterBy> meals) {
        if (meals == null || meals.isEmpty()) {
            showError("No meals found");
        } else {
            adapter.setMealList(meals);
            recyclerView.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void openMealDetails(MealFilterBy meal) {
        MealDetailsFragment fragment = new MealDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("idMeal", meal.getIdMeal());
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (networkObserver != null) {
            networkObserver.unregister();
        }
    }

    @Override
    public void onFilteredMealsByCategoryLoading() { showLoading(true); }

    @Override
    public void onFilteredMealsByCategoryError(String errMsg) { showError(errMsg); }

    @Override
    public void onFilteredMealsByCategorySuccess(List<MealFilterBy> meals) { showMeals(meals); }

    @Override
    public void onFilteredMealsByIngredientLoading() { showLoading(true); }

    @Override
    public void onFilteredMealsByIngredientError(String errMsg) { showError(errMsg); }

    @Override
    public void onFilteredMealsByIngredientSuccess(List<MealFilterBy> meals) { showMeals(meals); }

    @Override
    public void onFilteredMealsByCountryLoading() { showLoading(true); }

    @Override
    public void onFilteredMealsByCountryError(String errMsg) { showError(errMsg); }

    @Override
    public void onFilteredMealsByCountrySuccess(List<MealFilterBy> meals) { showMeals(meals); }


}

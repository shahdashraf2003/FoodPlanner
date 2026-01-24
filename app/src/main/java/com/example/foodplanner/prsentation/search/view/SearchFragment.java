package com.example.foodplanner.prsentation.search.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.all_categories.model.AllCategories;
import com.example.foodplanner.data.area.model.Area;
import com.example.foodplanner.data.ingredient.model.Ingredient;
import com.example.foodplanner.data.common.DisplayItem;
import com.example.foodplanner.prsentation.filtered_meals.view.FilteredMealsFragment;
import com.example.foodplanner.prsentation.filtered_meals.view.FilteredMealsView;
import com.example.foodplanner.prsentation.search.presenter.SearchPresenter;
import com.example.foodplanner.prsentation.search.presenter.SearchPresenterImp;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements GridAdapter.OnItemClickListener, SearchView {

    private RecyclerView recyclerView;
    private GridAdapter adapter;
    private ChipGroup chipGroup;

    private SearchPresenter presenter;

    private enum FilterType { CATEGORY, INGREDIENT, AREA }
    private FilterType currentFilter = FilterType.CATEGORY;

    private List<AllCategories> allCategoriesList = new ArrayList<>();
    private List<Ingredient> allIngredientsList = new ArrayList<>();
    private List<Area> allAreasList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.gridRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new GridAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        chipGroup = view.findViewById(R.id.chipGroup);
        chipGroup.setSingleSelection(true);

        presenter = new SearchPresenterImp(requireContext(), this);

        setupChipListeners();

        chipGroup.check(R.id.chip_category);
        presenter.getAllCategoriesList();

        return view;
    }

    private void setupChipListeners() {
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chip_category) {
                currentFilter = FilterType.CATEGORY;
                if (allCategoriesList.isEmpty()) {
                    presenter.getAllCategoriesList();
                } else {
                    updateAdapterWithCategories();
                }
            } else if (checkedId == R.id.chip_ingredient) {
                currentFilter = FilterType.INGREDIENT;
                if (allIngredientsList.isEmpty()) {
                    presenter.getAllIngredientsList();
                } else {
                    updateAdapterWithIngredients();
                }
            } else if (checkedId == R.id.chip_country) {
                currentFilter = FilterType.AREA;
                if (allAreasList.isEmpty()) {
                    presenter.getAllAreasList();
                } else {
                    updateAdapterWithAreas();
                }
            }
        });
    }

    @Override
    public void onCategoriesFilterListFetchError(String errMsg) {
    }

    @Override
    public void onCategoriesFilterFetchLoading() {
    }

    @Override
    public void onCategoriesFilterFetchSuccess(List<AllCategories> allCategories) {
        this.allCategoriesList = allCategories;
        updateAdapterWithCategories();
    }

    private void updateAdapterWithCategories() {
        List<DisplayItem> items = new ArrayList<>();
        for (AllCategories category : allCategoriesList) {
            items.add(new DisplayItem(
                    category.getStrCategory()
            ));
        }
        adapter.setItems(items);
    }

    @Override
    public void onIngredientsListFetchError(String errMsg) {
        Toast.makeText(requireContext(), "Error loading ingredients: " + errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIngredientsFetchLoading() {
    }

    @Override
    public void onIngredientsFetchSuccess(List<Ingredient> ingredients) {
        this.allIngredientsList = ingredients;
        updateAdapterWithIngredients();
    }

    private void updateAdapterWithIngredients() {
        List<DisplayItem> items = new ArrayList<>();
        for (Ingredient ingredient : allIngredientsList) {
            items.add(new DisplayItem(
                    ingredient.getName()
            ));
        }
        adapter.setItems(items);
    }

    @Override
    public void onAreaListFetchError(String errMsg) {
        Toast.makeText(requireContext(), "Error loading areas: " + errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAreaListFetchLoading() {
    }

    @Override
    public void onAreaFetchSuccess(List<Area> areas) {
        this.allAreasList = areas;
        updateAdapterWithAreas();
    }

    private void updateAdapterWithAreas() {
        List<DisplayItem> items = new ArrayList<>();
        for (Area area : allAreasList) {
            items.add(new DisplayItem(
                    area.getStrArea()

            ));
        }
        adapter.setItems(items);
    }

    @Override
    public void onItemClick(DisplayItem item) {
        switch (currentFilter) {
            case CATEGORY:
                handleCategoryClick(item);
                break;
            case INGREDIENT:
                handleIngredientClick(item);
                break;
            case AREA:
                handleAreaClick(item);
                break;
        }
    }

    private void handleCategoryClick(DisplayItem item) {
        AllCategories selectedCategory = null;
        for (AllCategories category : allCategoriesList) {
            if (category.getStrCategory().equals(item.getName())) {
                selectedCategory = category;
                break;
            }
        }

        if (selectedCategory != null) {
            String categoryName = selectedCategory.getStrCategory();
            navigateToCategoryDetails(categoryName);
        }
    }

    private void handleIngredientClick(DisplayItem item) {
        Ingredient selectedIngredient = null;
        for (Ingredient ingredient : allIngredientsList) {
            if (ingredient.getName().equals(item.getName())) {
                selectedIngredient = ingredient;
                break;
            }
        }

        if (selectedIngredient != null) {
            String ingredientName = selectedIngredient.getName();
            navigateToIngredientDetails(ingredientName);
        }
    }

    private void handleAreaClick(DisplayItem item) {
        Area selectedArea = null;
        for (Area area : allAreasList) {
            if (area.getStrArea().equals(item.getName())) {
                selectedArea = area;
                break;
            }
        }

        if (selectedArea != null) {
            String areaName = selectedArea.getStrArea();
            navigateToAreaDetails(areaName);
        }
    }

    private void navigateToCategoryDetails(String categoryName) {
        Bundle bundle = new Bundle();
        bundle.putString("category_name", categoryName);
        FilteredMealsFragment fragment = new FilteredMealsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToIngredientDetails(String ingredientName) {
        Bundle bundle = new Bundle();
        bundle.putString("ingredient_name", ingredientName);
        FilteredMealsFragment fragment = new FilteredMealsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();

    }

    private void navigateToAreaDetails(String areaName) {
        Bundle bundle = new Bundle();
        bundle.putString("area_name", areaName);
        FilteredMealsFragment fragment = new FilteredMealsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();

    }
}
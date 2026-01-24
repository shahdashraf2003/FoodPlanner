package com.example.foodplanner.prsentation.search.view;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.all_categories.model.AllCategories;
import com.example.foodplanner.data.area.model.Area;
import com.example.foodplanner.data.ingredient.model.Ingredient;
import com.example.foodplanner.data.common.DisplayItem;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.filtered_meals.view.FilteredMealsFragment;
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsFragment;
import com.example.foodplanner.prsentation.search.presenter.SearchPresenter;
import com.example.foodplanner.prsentation.search.presenter.SearchPresenterImp;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements GridAdapter.OnItemClickListener, SearchView,OnMealClickListener  {

    private RecyclerView recyclerView;
    private GridAdapter adapter;
    private SearchAdapter searchAdapter;
    private ChipGroup chipGroup;
    private EditText searchEditText;

    private SearchPresenter presenter;


    private enum FilterType { CATEGORY, INGREDIENT, AREA, MEAL }
    private FilterType currentFilter = FilterType.CATEGORY;

    private List<AllCategories> allCategoriesList = new ArrayList<>();
    private List<Ingredient> allIngredientsList = new ArrayList<>();
    private List<Area> allAreasList = new ArrayList<>();
    private List<DisplayItem> currentDisplayItems = new ArrayList<>();

    private boolean isSearching = false;
    private Handler handler;
    private Runnable searchRunnable;

    private String lastSearchQuery = "";

    private static final int MIN_SEARCH_CHARS = 2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.gridRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        adapter = new GridAdapter(new ArrayList<>(), this);
        searchAdapter = new SearchAdapter(this);

        recyclerView.setAdapter(adapter);

        chipGroup = view.findViewById(R.id.chipGroup);
        chipGroup.setSingleSelection(true);

        searchEditText = view.findViewById(R.id.searchEditText);
        presenter = new SearchPresenterImp(requireContext(), this);
        handler = new Handler();

        setupChipListeners();
        setupSearchListener();

        chipGroup.check(R.id.chip_category);
        presenter.getAllCategoriesList();

        return view;
    }

    private void setupChipListeners() {
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            searchEditText.setText("");
            lastSearchQuery = "";

            if (isSearching) {
                isSearching = false;
                recyclerView.setAdapter(adapter);
                adapter.setItems(currentDisplayItems);
            }

            if (checkedId == R.id.chip_category) {
                currentFilter = FilterType.CATEGORY;
                if (allCategoriesList.isEmpty()) {
                    presenter.getAllCategoriesList();
                } else {
                    updateDisplayItemsFromCategories();
                }
            } else if (checkedId == R.id.chip_ingredient) {
                currentFilter = FilterType.INGREDIENT;
                if (allIngredientsList.isEmpty()) {
                    presenter.getAllIngredientsList();
                } else {
                    updateDisplayItemsFromIngredients();
                }
            } else if (checkedId == R.id.chip_country) {
                currentFilter = FilterType.AREA;
                if (allAreasList.isEmpty()) {
                    presenter.getAllAreasList();
                } else {
                    updateDisplayItemsFromAreas();
                }
            }
        });
    }

    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }

                String query = s.toString().trim();

                if (query.isEmpty() || query.length() < MIN_SEARCH_CHARS) {
                    if (isSearching) {
                        revertToFilterView();
                    }
                    return;
                }

                if (query.equals(lastSearchQuery)) {
                    return;
                }

                lastSearchQuery = query;

                searchRunnable = () -> {
                    if (query.length() >= MIN_SEARCH_CHARS && !isSearching) {
                        switchToSearchMode();
                    }

                    if (query.length() >= MIN_SEARCH_CHARS) {
                        presenter.getSearchedMeal(query);
                    }
                };
                handler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchEditText.getText().toString().trim();
                if (query.length() >= MIN_SEARCH_CHARS) {
                    if (searchRunnable != null) {
                        handler.removeCallbacks(searchRunnable);
                    }

                    if (!isSearching) {
                        switchToSearchMode();
                    }
                    presenter.getSearchedMeal(query);

                    hideKeyboard();
                } else {
                    Toast.makeText(requireContext(),
                            "Please enter at least " + MIN_SEARCH_CHARS + " characters",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
    }

    private void switchToSearchMode() {
        isSearching = true;
        chipGroup.clearCheck();

    }

    private void revertToFilterView() {
        isSearching = false;
        lastSearchQuery = "";
        recyclerView.post(() -> {
            recyclerView.setAdapter(adapter);
            adapter.setItems(currentDisplayItems);
        });
    }

    private void hideKeyboard() {
        android.view.inputmethod.InputMethodManager imm =
                (android.view.inputmethod.InputMethodManager) requireContext()
                        .getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        if (imm != null && getActivity() != null && getActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void navigateToMealDetails(Meal meal) {
        Bundle bundle = new Bundle();
        bundle.putString("idMeal", meal.getIdMeal());

        MealDetailsFragment fragment = new MealDetailsFragment();
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCategoriesFilterFetchSuccess(List<AllCategories> allCategories) {
        allCategoriesList = allCategories;
        updateDisplayItemsFromCategories();
    }

    private void updateDisplayItemsFromCategories() {
        currentDisplayItems.clear();
        for (AllCategories category : allCategoriesList) {
            currentDisplayItems.add(new DisplayItem(category.getStrCategory()));
        }
        adapter.setItems(currentDisplayItems);

        if (currentFilter == FilterType.CATEGORY && !isSearching) {
            recyclerView.post(() -> {
                if (recyclerView.getAdapter() != adapter) {
                    recyclerView.setAdapter(adapter);
                }
                adapter.setItems(currentDisplayItems);
            });
        }
    }

    @Override
    public void onIngredientsFetchSuccess(List<Ingredient> ingredients) {
        allIngredientsList = ingredients;
        updateDisplayItemsFromIngredients();
    }

    private void updateDisplayItemsFromIngredients() {
        currentDisplayItems.clear();
        for (Ingredient ingredient : allIngredientsList) {
            currentDisplayItems.add(new DisplayItem(ingredient.getName()));
        }
        adapter.setItems(currentDisplayItems);

        if (currentFilter == FilterType.INGREDIENT && !isSearching) {
            recyclerView.post(() -> {
                if (recyclerView.getAdapter() != adapter) {
                    recyclerView.setAdapter(adapter);
                }
                adapter.setItems(currentDisplayItems);
            });
        }
    }

    @Override
    public void onAreaFetchSuccess(List<Area> areas) {
        allAreasList = areas;
        updateDisplayItemsFromAreas();
    }

    private void updateDisplayItemsFromAreas() {
        currentDisplayItems.clear();
        for (Area area : allAreasList) {
            currentDisplayItems.add(new DisplayItem(area.getStrArea()));
        }
        adapter.setItems(currentDisplayItems);

        if (currentFilter == FilterType.AREA && !isSearching) {
            recyclerView.post(() -> {
                if (recyclerView.getAdapter() != adapter) {
                    recyclerView.setAdapter(adapter);
                }
                adapter.setItems(currentDisplayItems);
            });
        }
    }

    @Override
    public void onSearchedMaelFetchSuccess(List<Meal> meals) {
        if (!isSearching) {
            return;
        }

        if (meals == null || meals.isEmpty()) {
            if (searchAdapter != null) {
                searchAdapter.clearMeals();
            }
            Toast.makeText(requireContext(),
                    "No meals found for: " + lastSearchQuery,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        searchAdapter.setMeals(meals);

        recyclerView.post(() -> {
            if (recyclerView.getAdapter() != searchAdapter) {
                recyclerView.setAdapter(searchAdapter);
            }
            searchAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onItemClick(DisplayItem item) {
        switch (currentFilter) {
            case CATEGORY:
                navigateToCategoryDetails(item.getName());
                break;
            case INGREDIENT:
                navigateToIngredientDetails(item.getName());
                break;
            case AREA:
                navigateToAreaDetails(item.getName());
                break;
            case MEAL:
                break;
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

    @Override
    public void onCategoriesFilterListFetchError(String errMsg) {
        Toast.makeText(requireContext(), "Error loading categories: " + errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoriesFilterFetchLoading() {
    }

    @Override
    public void onIngredientsListFetchError(String errMsg) {
        Toast.makeText(requireContext(), "Error loading ingredients: " + errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIngredientsFetchLoading() {
    }

    @Override
    public void onAreaListFetchError(String errMsg) {
        Toast.makeText(requireContext(), "Error loading areas: " + errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAreaListFetchLoading() {
    }

    @Override
    public void onSearchedMaelFetchError(String errMsg) {
        Toast.makeText(requireContext(), "Search error: " + errMsg, Toast.LENGTH_SHORT).show();

        if (isSearching) {
            revertToFilterView();
        }
    }

    @Override
    public void onSearchedMaelFetchLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
    @Override
    public void onMealClick(Meal meal) {
        navigateToMealDetails(meal);

    }

}
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
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class SearchFragment extends Fragment implements GridAdapter.OnItemClickListener, SearchView, OnMealClickListener {

    private RecyclerView recyclerView;
    private GridAdapter adapter;
    private SearchAdapter searchAdapter;
    private ChipGroup chipGroup;
    private EditText searchEditText;
    private ProgressBar progressBar;
    private TextView tvError;

    private SearchPresenter presenter;

    private enum FilterType { CATEGORY, INGREDIENT, AREA }
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
        progressBar = view.findViewById(R.id.progressSearch);
        tvError = view.findViewById(R.id.tvSearchError);

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
            tvError.setVisibility(View.GONE);

            if (isSearching) {
                isSearching = false;
                recyclerView.setAdapter(adapter);
                adapter.setItems(currentDisplayItems);
            }

            if (checkedId==R.id.chip_category) {
                    currentFilter = FilterType.CATEGORY;
                    if (allCategoriesList.isEmpty()) presenter.getAllCategoriesList();
                    else updateDisplayItemsFromCategories();
            }
                else if(checkedId== R.id.chip_ingredient) {
                currentFilter = FilterType.INGREDIENT;
                if (allIngredientsList.isEmpty()) presenter.getAllIngredientsList();
                else updateDisplayItemsFromIngredients();
            }
            else if (checkedId==R.id.chip_country)
            {
                    currentFilter = FilterType.AREA;
                    if (allAreasList.isEmpty()) presenter.getAllAreasList();
                    else updateDisplayItemsFromAreas();

            }
        });
    }

    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) handler.removeCallbacks(searchRunnable);

                String query = s.toString().trim();
                if (query.length() < MIN_SEARCH_CHARS) {
                    if (isSearching) revertToFilterView();
                    return;
                }

                searchRunnable = () -> {
                    if (!isSearching) switchToSearchMode();
                    presenter.getSearchedMeal(query);
                };
                handler.postDelayed(searchRunnable, 500);
            }
        });

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchEditText.getText().toString().trim();
                if (query.length() >= MIN_SEARCH_CHARS) {
                    if (searchRunnable != null) handler.removeCallbacks(searchRunnable);
                    if (!isSearching) switchToSearchMode();
                    presenter.getSearchedMeal(query);
                    hideKeyboard();
                } else {
                    Toast.makeText(requireContext(), "Enter at least " + MIN_SEARCH_CHARS + " characters", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
    }

    private void switchToSearchMode() {
        isSearching = true;
        chipGroup.clearCheck();
        recyclerView.setAdapter(searchAdapter);
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
                (android.view.inputmethod.InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
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

    private void navigateToFilteredMeals(Bundle bundle) {
        FilteredMealsFragment fragment = new FilteredMealsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCategoriesFilterFetchLoading() { progressBar.setVisibility(View.VISIBLE); }
    @Override
    public void onIngredientsFetchLoading() { progressBar.setVisibility(View.VISIBLE); }
    @Override
    public void onAreaListFetchLoading() { progressBar.setVisibility(View.VISIBLE); }
    @Override
    public void onSearchedMaelFetchLoading() { progressBar.setVisibility(View.VISIBLE); }

    @Override
    public void onCategoriesFilterFetchSuccess(List<AllCategories> allCategories) {
        progressBar.setVisibility(View.GONE);
        allCategoriesList = allCategories;
        updateDisplayItemsFromCategories();
    }

    @Override
    public void onIngredientsFetchSuccess(List<Ingredient> ingredients) {
        progressBar.setVisibility(View.GONE);
        allIngredientsList = ingredients;
        updateDisplayItemsFromIngredients();
    }

    @Override
    public void onAreaFetchSuccess(List<Area> areas) {
        progressBar.setVisibility(View.GONE);
        allAreasList = areas;
        updateDisplayItemsFromAreas();
    }

    @Override
    public void onSearchedMaelFetchSuccess(List<Meal> meals) {
        progressBar.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);

        if (!isSearching) return;

        if (meals == null || meals.isEmpty()) {
            searchAdapter.clearMeals();
            tvError.setText("No meals found for: " + lastSearchQuery);
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        searchAdapter.setMeals(meals);
        recyclerView.post(() -> {
            if (recyclerView.getAdapter() != searchAdapter) recyclerView.setAdapter(searchAdapter);
            searchAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onCategoriesFilterListFetchError(String errMsg) { showError("Categories: " + errMsg); }
    @Override
    public void onIngredientsListFetchError(String errMsg) { showError("Ingredients: " + errMsg); }
    @Override
    public void onAreaListFetchError(String errMsg) { showError("Areas: " + errMsg); }
    @Override
    public void onSearchedMaelFetchError(String errMsg) {
        showError("Search error: " + errMsg);
        if (isSearching) revertToFilterView();
    }

    private void showError(String msg) {
        progressBar.setVisibility(View.GONE);
        tvError.setText(msg);
        tvError.setVisibility(View.VISIBLE);
    }

    private void updateDisplayItemsFromCategories() {
        currentDisplayItems.clear();
        for (AllCategories category : allCategoriesList) currentDisplayItems.add(new DisplayItem(category.getStrCategory()));
        adapter.setItems(currentDisplayItems);
        if (currentFilter == FilterType.CATEGORY && !isSearching) recyclerView.setAdapter(adapter);
    }

    private void updateDisplayItemsFromIngredients() {
        currentDisplayItems.clear();
        for (Ingredient ingredient : allIngredientsList) currentDisplayItems.add(new DisplayItem(ingredient.getName()));
        adapter.setItems(currentDisplayItems);
        if (currentFilter == FilterType.INGREDIENT && !isSearching) recyclerView.setAdapter(adapter);
    }

    private void updateDisplayItemsFromAreas() {
        currentDisplayItems.clear();
        for (Area area : allAreasList) currentDisplayItems.add(new DisplayItem(area.getStrArea()));
        adapter.setItems(currentDisplayItems);
        if (currentFilter == FilterType.AREA && !isSearching) recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(DisplayItem item) {
        Bundle bundle = new Bundle();
        switch (currentFilter) {
            case CATEGORY: bundle.putString("category_name", item.getName()); break;
            case INGREDIENT: bundle.putString("ingredient_name", item.getName()); break;
            case AREA: bundle.putString("area_name", item.getName()); break;
        }
        navigateToFilteredMeals(bundle);
    }

    @Override
    public void onMealClick(Meal meal) { navigateToMealDetails(meal); }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) handler.removeCallbacksAndMessages(null);
    }
}

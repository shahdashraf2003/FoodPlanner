package com.example.foodplanner.prsentation.search.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.area.model.Area;
import com.example.foodplanner.data.category.model.Category;
import com.example.foodplanner.data.common.model.DisplayItem;
import com.example.foodplanner.data.ingredient.model.Ingredient;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.filtered_meals.view.FilteredMealsFragment;
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsFragment;
import com.example.foodplanner.prsentation.search.presenter.SearchPresenter;
import com.example.foodplanner.prsentation.search.presenter.SearchPresenterImp;
import com.example.foodplanner.utils.NetworkConnectionObserver;
import com.example.foodplanner.utils.NoInternetDialog;
import com.google.android.material.chip.Chip;
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
    private FilterType currentFilter = null;

    private List<Category> allCategoriesList = new ArrayList<>();
    private List<Ingredient> allIngredientsList = new ArrayList<>();
    private List<Area> allAreasList = new ArrayList<>();
    private List<DisplayItem> currentDisplayItems = new ArrayList<>();

    private boolean isSearching = false;
    NetworkConnectionObserver networkObserver;

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

        setupChipListeners();
        setupSearchListener();

        NoInternetDialog noInternetDialog = new NoInternetDialog(requireContext());
        networkObserver = new NetworkConnectionObserver(requireContext(), new NetworkConnectionObserver.NetworkListener() {
            @Override
            public void onNetworkLost() {
                requireActivity().runOnUiThread(() -> noInternetDialog.showDialog());
            }
            @Override
            public void onNetworkAvailable() {
                requireActivity().runOnUiThread(() -> noInternetDialog.hideDialog());
            }
        });

        Chip chipClear = view.findViewById(R.id.chip_clear);
        chipClear.setOnClickListener(v -> {
            currentFilter = null;
            isSearching = false;
            searchEditText.setText("");
            chipGroup.clearCheck();
            searchEditText.setHint("Search recipes..");
            adapter.setItems(new ArrayList<>());
            searchAdapter.clearMeals();
            tvError.setVisibility(View.GONE);
            chipGroup.setOnCheckedChangeListener(null);
            chipGroup.clearCheck();
            setupChipListeners();
        });

        return view;
    }

    private void setupChipListeners() {
        Chip chipCategory = chipGroup.findViewById(R.id.chip_category);
        Chip chipIngredient = chipGroup.findViewById(R.id.chip_ingredient);
        Chip chipCountry = chipGroup.findViewById(R.id.chip_country);

        View.OnClickListener chipClickListener = v -> {
            tvError.setVisibility(View.GONE);
            isSearching = false;
            recyclerView.setAdapter(adapter);
            adapter.setItems(currentDisplayItems);

            if (v.getId() == R.id.chip_category) {
                currentFilter = FilterType.CATEGORY;
                searchEditText.setHint("Search by category");
                if (allCategoriesList.isEmpty()) presenter.getAllCategoriesList();
                else updateDisplayItemsFromCategories();
            } else if (v.getId() == R.id.chip_ingredient) {
                currentFilter = FilterType.INGREDIENT;
                searchEditText.setHint("Search by ingredient");
                if (allIngredientsList.isEmpty()) presenter.getAllIngredientsList();
                else updateDisplayItemsFromIngredients();
            } else if (v.getId() == R.id.chip_country) {
                currentFilter = FilterType.AREA;
                searchEditText.setHint("Search by country");
                if (allAreasList.isEmpty()) presenter.getAllAreasList();
                else updateDisplayItemsFromAreas();
            }
        };

        chipCategory.setOnClickListener(chipClickListener);
        chipIngredient.setOnClickListener(chipClickListener);
        chipCountry.setOnClickListener(chipClickListener);
    }


    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { tvError.setText(""); }
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim().toLowerCase();
                if (query.isEmpty()) {
                    if (isSearching) revertToFilterView();
                    return;
                }
                if (!isSearching) switchToSearchMode();

                List<DisplayItem> filteredItems = new ArrayList<>();

                if (currentFilter == FilterType.CATEGORY) {
                    for (Category c : allCategoriesList) {
                        if (c.getStrCategory().toLowerCase().contains(query))
                            filteredItems.add(new DisplayItem(c.getStrCategory(), c.getStrCategoryThumb()));
                    }
                    adapter.setItems(filteredItems);
                } else if (currentFilter == FilterType.INGREDIENT) {
                    for (Ingredient i : allIngredientsList) {
                        if (i.getName().toLowerCase().contains(query))
                            filteredItems.add(new DisplayItem(i.getName(), i.getImageUrl()));
                    }
                    adapter.setItems(filteredItems);
                } else if (currentFilter == FilterType.AREA) {
                    for (Area a : allAreasList) {
                        if (a.getStrArea().toLowerCase().contains(query))
                            filteredItems.add(new DisplayItem(a.getStrArea(), a.getFlagUrl()));
                    }
                    adapter.setItems(filteredItems);
                } else {
                    presenter.getSearchedMeal(query);
                }
            }
        });
    }

    private void switchToSearchMode() {
        isSearching = true;
        recyclerView.setAdapter(adapter);
    }

    private void revertToFilterView() {
        isSearching = false;
        recyclerView.post(() -> {
            recyclerView.setAdapter(adapter);
            adapter.setItems(currentDisplayItems);
        });
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
    public void onCategoriesFilterListFetchError(String errMsg) {}
    @Override
    public void onCategoriesFilterFetchLoading() { progressBar.setVisibility(View.VISIBLE); }
    @Override
    public void onIngredientsFetchLoading() { progressBar.setVisibility(View.VISIBLE); }
    @Override
    public void onAreaListFetchLoading() { progressBar.setVisibility(View.VISIBLE); }
    @Override
    public void onCategoriesFilterFetchSuccess(List<Category> allCategories) {
        progressBar.setVisibility(View.GONE);
        allCategoriesList = allCategories;
        updateDisplayItemsFromCategories();
    }
    @Override
    public void onIngredientsListFetchError(String errMsg) {}
    @Override
    public void onIngredientsFetchSuccess(List<Ingredient> ingredients) {
        progressBar.setVisibility(View.GONE);
        allIngredientsList = ingredients;
        updateDisplayItemsFromIngredients();
    }
    @Override
    public void onAreaListFetchError(String errMsg) {}
    @Override
    public void onAreaListFetchSuccess(List<Area> areas) {
        progressBar.setVisibility(View.GONE);
        allAreasList = areas;
        updateDisplayItemsFromAreas();
    }
    @Override
    public void onSearchedMaelFetchError(String errMsg) {
        progressBar.setVisibility(View.GONE);
        tvError.setText(errMsg);
        tvError.setVisibility(View.VISIBLE);
        searchAdapter.clearMeals();
    }
    @Override
    public void onSearchedMaelFetchLoading() { progressBar.setVisibility(View.VISIBLE); }
    @Override
    public void onSearchedMaelFetchSuccess(List<Meal> meals) {
        progressBar.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        if (!isSearching) return;
        if (meals == null || meals.isEmpty()) {
            tvError.setText("No meals found");
            tvError.setVisibility(View.VISIBLE);
            return;
        }
        searchAdapter.setMeals(meals);
        recyclerView.post(() -> {
            if (recyclerView.getAdapter() != searchAdapter)
                recyclerView.setAdapter(searchAdapter);
            searchAdapter.notifyDataSetChanged();
        });
    }

    private void updateDisplayItemsFromCategories() {
        currentDisplayItems.clear();
        for (Category category : allCategoriesList)
            currentDisplayItems.add(new DisplayItem(category.getStrCategory(), category.getStrCategoryThumb()));
        adapter.setItems(currentDisplayItems);
    }
    private void updateDisplayItemsFromIngredients() {
        currentDisplayItems.clear();
        for (Ingredient ingredient : allIngredientsList)
            currentDisplayItems.add(new DisplayItem(ingredient.getName(), ingredient.getImageUrl()));
        adapter.setItems(currentDisplayItems);
    }
    private void updateDisplayItemsFromAreas() {
        currentDisplayItems.clear();
        for (Area area : allAreasList)
            currentDisplayItems.add(new DisplayItem(area.getStrArea(), area.getFlagUrl()));
        adapter.setItems(currentDisplayItems);
    }

    @Override
    public void onItemClick(DisplayItem item) {
        if (currentFilter == null) return;
        Bundle bundle = new Bundle();
        if (currentFilter == FilterType.CATEGORY) bundle.putString("category_name", item.getName());
        else if (currentFilter == FilterType.INGREDIENT) bundle.putString("ingredient_name", item.getName());
        else if (currentFilter == FilterType.AREA) bundle.putString("area_name", item.getName());
        navigateToFilteredMeals(bundle);
    }

    @Override
    public void onMealClick(Meal meal) {
        Bundle bundle = new Bundle();
        bundle.putString("idMeal", meal.getIdMeal());
        MealDetailsFragment fragment = new MealDetailsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (networkObserver != null) networkObserver.unregister();
    }
}

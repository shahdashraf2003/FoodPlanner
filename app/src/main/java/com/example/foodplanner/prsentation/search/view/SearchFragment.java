package com.example.foodplanner.prsentation.search.view;

import static com.example.foodplanner.utils.SnackBarUtil.showSnack;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.area.model.Area;
import com.example.foodplanner.data.category.model.Category;
import com.example.foodplanner.data.common.DisplayItem;
import com.example.foodplanner.data.ingredient.model.Ingredient;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.filtered_meals.view.FilteredMealsFragment;
import com.example.foodplanner.prsentation.search.presenter.SearchPresenter;
import com.example.foodplanner.prsentation.search.presenter.SearchPresenterImp;
import com.example.foodplanner.utils.NetworkConnectionObserver;
import com.example.foodplanner.utils.NoInternetDialog;
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
    private Handler handler;
    private Runnable searchRunnable;
    private static final int MIN_SEARCH_CHARS = 2;
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
        handler = new Handler();

        setupChipListeners();
        setupSearchListener();

        if (allCategoriesList.isEmpty()) presenter.getAllCategoriesList();
        else updateDisplayItemsFromCategories();

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

        return view;
    }

    private void setupChipListeners() {
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            searchEditText.setText("");
            tvError.setVisibility(View.GONE);

            if (isSearching) {
                isSearching = false;
                recyclerView.setAdapter(adapter);
                adapter.setItems(currentDisplayItems);
            }

            if (checkedId == R.id.chip_category) {
                currentFilter = FilterType.CATEGORY;
                searchEditText.setHint("Search by category");
                if (allCategoriesList.isEmpty()) presenter.getAllCategoriesList();
                else updateDisplayItemsFromCategories();
            } else if (checkedId == R.id.chip_ingredient) {
                currentFilter = FilterType.INGREDIENT;
                searchEditText.setHint("Search by ingredient");
                if (allIngredientsList.isEmpty()) presenter.getAllIngredientsList();
                else updateDisplayItemsFromIngredients();
            } else if (checkedId == R.id.chip_country) {
                currentFilter = FilterType.AREA;
                searchEditText.setHint("Search by area");
                if (allAreasList.isEmpty()) presenter.getAllAreasList();
                else updateDisplayItemsFromAreas();
            } else {
                currentFilter = null;
                searchEditText.setHint("Search meals");
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
                    if (currentFilter != null) performLocalSearch(query);
                    else presenter.getSearchedMeal(query);
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
                    if (currentFilter != null) performLocalSearch(query);
                    else presenter.getSearchedMeal(query);
                } else {
                    showSnack(requireView().getRootView(), "Enter at least " + MIN_SEARCH_CHARS + " characters");
                }
                return true;
            }
            return false;
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

    private void performLocalSearch(String query) {
        query = query.toLowerCase();
        List<DisplayItem> filteredList = new ArrayList<>();
        if (currentFilter == FilterType.CATEGORY) {
            for (Category c : allCategoriesList)
                if (c.getStrCategory().toLowerCase().contains(query))
                    filteredList.add(new DisplayItem(c.getStrCategory(), c.getStrCategoryThumb()));
        } else if (currentFilter == FilterType.INGREDIENT) {
            for (Ingredient i : allIngredientsList)
                if (i.getName().toLowerCase().contains(query))
                    filteredList.add(new DisplayItem(i.getName(), i.getImageUrl()));
        } else if (currentFilter == FilterType.AREA) {
            for (Area a : allAreasList)
                if (a.getStrArea().toLowerCase().contains(query))
                    filteredList.add(new DisplayItem(a.getStrArea(), a.getFlagUrl()));
        }
        adapter.setItems(filteredList);
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
        tvError.setText("Search error: " + errMsg);
        tvError.setVisibility(View.VISIBLE);
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
        Bundle bundle = new Bundle();
        switch (currentFilter) {
            case CATEGORY: bundle.putString("category_name", item.getName()); break;
            case INGREDIENT: bundle.putString("ingredient_name", item.getName()); break;
            case AREA: bundle.putString("area_name", item.getName()); break;
        }
        navigateToFilteredMeals(bundle);
    }

    @Override
    public void onMealClick(Meal meal) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) handler.removeCallbacksAndMessages(null);
        if (networkObserver != null) networkObserver.unregister();
    }
}

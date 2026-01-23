package com.example.foodplanner.prsentation.search.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.all_categories.AllCategories;
import com.example.foodplanner.data.models.common.DisplayItem;
import com.example.foodplanner.prsentation.search.presenter.SearchPresenter;
import com.example.foodplanner.prsentation.search.presenter.SearchPresenterImp;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements GridAdapter.OnItemClickListener, SearchView {

    private RecyclerView recyclerView;
    private GridAdapter adapter;
    private ChipGroup chipGroup;

    private SearchPresenter presenter;

    private enum FilterType { CATEGORY, INGREDIENT, AREA }
    private FilterType currentFilter = FilterType.CATEGORY;

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
        Log.d("TAG", "onCreateView: "+presenter.toString());
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chip_category) {
                currentFilter = FilterType.CATEGORY;
                presenter.getAllCategoriesList();
            } else if (checkedId == R.id.chip_ingredient) {
                currentFilter = FilterType.INGREDIENT;
                presenter.getAllIngredientsList();
            } else if (checkedId == R.id.chip_country) {
                currentFilter = FilterType.AREA;
                presenter.getAllAreasList();
            }
        });

        chipGroup.check(R.id.chip_category);

        return view;
    }

    @Override
    public void onCategoriesFilterListFetchError(String errMsg) {}
    @Override
    public void onCategoriesFilterFetchLoading() {}
    @Override
    public void onCategoriesFilterFetchSuccess(java.util.List<AllCategories> allCategories) {
        java.util.List<DisplayItem> items = new ArrayList<>();
        for (AllCategories c : allCategories) {
            items.add(new DisplayItem(c.getStrCategory()));
        }
        adapter.setItems(items);
    }

    @Override
    public void onIngredientsListFetchError(String errMsg) {}
    @Override
    public void onIngredientsFetchLoading() {}
    @Override
    public void onIngredientsFetchSuccess(java.util.List<com.example.foodplanner.data.models.ingredient.Ingredient> ingredients) {
        java.util.List<DisplayItem> items = new ArrayList<>();
        for (com.example.foodplanner.data.models.ingredient.Ingredient ing : ingredients) {
            items.add(new DisplayItem(ing.getName()));
        }
        adapter.setItems(items);
    }

    @Override
    public void onAreaListFetchError(String errMsg) {}

    @Override
    public void onAreaListFetchLoading() {

    }

    @Override
    public void onAreaFetchSuccess(java.util.List<com.example.foodplanner.data.models.area.Area> areas) {
        java.util.List<DisplayItem> items = new ArrayList<>();
        for (com.example.foodplanner.data.models.area.Area a : areas) {
            items.add(new DisplayItem(a.getStrArea()));
        }
        adapter.setItems(items);
    }

    @Override
    public void onItemClick(DisplayItem item) {}
}

package com.example.foodplanner.prsentation.home.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.foodplanner.R;
import com.example.foodplanner.data.category.model.Category;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.filtered_meals.view.FilteredMealsFragment;
import com.example.foodplanner.prsentation.home.presenter.HomePresenter;
import com.example.foodplanner.prsentation.home.presenter.HomePresenterImp;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView, CategoryOnClickListener {

    private ImageView ivMeal;
    private TextView tvName, tvDesc;
    private ProgressBar imageProgress;
    private RecyclerView recyclerViewCategories;
    private CategoryAdapter categoryAdapter;
    private HomePresenter homePresenter;
    private View mealCardRoot;
    private TextView tvError;
    private ProgressBar progressLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        View mealCardView = view.findViewById(R.id.meal_card_include);
        ivMeal = mealCardView.findViewById(R.id.iv_meal);
        tvName = mealCardView.findViewById(R.id.tv_name);
        tvDesc = mealCardView.findViewById(R.id.tv_desc);
        imageProgress = mealCardView.findViewById(R.id.imageProgress);
        mealCardRoot = view.findViewById(R.id.mealCardRoot);

        tvError = view.findViewById(R.id.tvErrorFiltered);
        progressLoading = view.findViewById(R.id.progressBarFiltered);

        recyclerViewCategories = view.findViewById(R.id.recyclerViewCategories);
        categoryAdapter = new CategoryAdapter(this);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewCategories.setAdapter(categoryAdapter);

        homePresenter = new HomePresenterImp(requireContext(), this);
        loadRandomMeal();
        loadCategories();

        return view;
    }

    private void loadRandomMeal() {
        showLoading(true);
        homePresenter.getRandomMeal();
    }

    private void loadCategories() {
        homePresenter.getAllCategories();
    }

    private void showLoading(boolean isLoading) {
        progressLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mealCardRoot.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }

    private void showError(String message) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
        mealCardRoot.setVisibility(View.GONE);
        progressLoading.setVisibility(View.GONE);
    }

    @Override
    public void onRandomMealFetchLoading() {
        showLoading(true);
    }

    @Override
    public void onRandomMealFetchError(String errMsg) {
        showError(errMsg);
    }

    @Override
    public void onRandomMealFetchSuccess(List<Meal> meals) {
        if (meals == null || meals.isEmpty()) {
            showError("No meal found");
            return;
        }

        Meal meal = meals.get(0);
        tvName.setText(meal.getStrMeal());
        tvDesc.setText(meal.getStrInstructions());

        imageProgress.setVisibility(View.VISIBLE);
        ivMeal.setVisibility(View.INVISIBLE);

        Glide.with(requireContext())
                .load(meal.getStrMealThumb())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageProgress.setVisibility(View.GONE);
                        ivMeal.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imageProgress.setVisibility(View.GONE);
                        ivMeal.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(ivMeal);

        mealCardRoot.setVisibility(View.VISIBLE);
        mealCardRoot.setOnClickListener(v -> openMealDetails(meal));
        tvError.setVisibility(View.GONE);
        progressLoading.setVisibility(View.GONE);
    }

    private void openMealDetails(Meal meal) {
        FilteredMealsFragment fragment = new FilteredMealsFragment();
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
    public void onAllCategoriesFetchLoading() {
        showLoading(true);
    }

    @Override
    public void onAllCategoriesFetchError(String errMsg) {
        showError(errMsg);
    }

    @Override
    public void onAllCategoriesFetchSuccess(List<Category> categories) {
        categoryAdapter.setCategoryList(categories);
        showLoading(false);
    }

    @Override
    public void onCategoryClick(Category category) {
        Bundle bundle = new Bundle();
        bundle.putString("category_name", category.getStrCategory());
        FilteredMealsFragment fragment = new FilteredMealsFragment();
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
}

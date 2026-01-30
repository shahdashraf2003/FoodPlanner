package com.example.foodplanner.prsentation.home.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsFragment;
import com.example.foodplanner.utils.NetworkConnectionObserver;
import com.example.foodplanner.utils.NoInternetDialog;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView, CategoryOnClickListener {

    private ImageView ivMeal;
    private TextView tvName, tvDesc;
    private ProgressBar progressMealCard, progressCategories, progressLoading;
    private TextView tvErrorMealCard, tvErrorCategories;
    private RecyclerView recyclerViewCategories;
    private CategoryAdapter categoryAdapter;
    private HomePresenter homePresenter;
    private View mealCardRoot;

    NetworkConnectionObserver networkObserver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mealCardRoot = view.findViewById(R.id.mealCardRoot);
        View mealCardView = view.findViewById(R.id.meal_card_include);
        ivMeal = mealCardView.findViewById(R.id.iv_meal);
        tvName = mealCardView.findViewById(R.id.tv_name);
        tvDesc = mealCardView.findViewById(R.id.tv_desc);
        progressMealCard = view.findViewById(R.id.progressMealCard);
        tvErrorMealCard = view.findViewById(R.id.tvErrorMealCard);

        recyclerViewCategories = view.findViewById(R.id.recyclerViewCategories);
        progressCategories = view.findViewById(R.id.progressCategories);
        tvErrorCategories = view.findViewById(R.id.tvErrorCategories);

        progressLoading = new ProgressBar(requireContext());
        categoryAdapter = new CategoryAdapter(this);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewCategories.setAdapter(categoryAdapter);

        homePresenter = new HomePresenterImp(requireContext(), this);
        NoInternetDialog noInternetDialog = new NoInternetDialog(requireContext());

        networkObserver = new NetworkConnectionObserver(requireContext(), new NetworkConnectionObserver.NetworkListener() {
            @Override
            public void onNetworkLost() {
              requireActivity().runOnUiThread(noInternetDialog::showDialog);
            }
            @Override
            public void onNetworkAvailable() {
                requireActivity().runOnUiThread(noInternetDialog::hideDialog);
            }
        });

        loadRandomMeal();
        loadCategories();

        return view;
    }

    private void loadRandomMeal() {
        showMealCardLoading(true);
        homePresenter.getRandomMeal();
    }

    private void loadCategories() {
        showCategoriesLoading(true);
        homePresenter.getAllCategories();
    }

    private void showMealCardLoading(boolean isLoading) {
        progressMealCard.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mealCardRoot.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        tvErrorMealCard.setVisibility(View.GONE);
    }

    private void showMealCardError(String message) {
        tvErrorMealCard.setVisibility(View.VISIBLE);
        tvErrorMealCard.setText(message);
        progressMealCard.setVisibility(View.GONE);
        mealCardRoot.setVisibility(View.GONE);
    }

    private void showCategoriesLoading(boolean isLoading) {
        progressCategories.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerViewCategories.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        tvErrorCategories.setVisibility(View.GONE);
    }

    private void showCategoriesError(String message) {
        tvErrorCategories.setVisibility(View.VISIBLE);
        tvErrorCategories.setText(message);
        progressCategories.setVisibility(View.GONE);
        recyclerViewCategories.setVisibility(View.GONE);
    }

    private void showMeal(Meal meal) {
        tvName.setText(meal.getStrMeal());
        tvDesc.setText(meal.getStrInstructions());

        progressMealCard.setVisibility(View.VISIBLE);
        ivMeal.setVisibility(View.INVISIBLE);

        Glide.with(requireContext())
                .load(meal.getStrMealThumb())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressMealCard.setVisibility(View.GONE);
                        ivMeal.setVisibility(View.VISIBLE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressMealCard.setVisibility(View.GONE);
                        ivMeal.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(ivMeal);

        mealCardRoot.setVisibility(View.VISIBLE);
        tvErrorMealCard.setVisibility(View.GONE);
        mealCardRoot.setOnClickListener(v -> openMealDetails(meal));
    }

    private void showCategories(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            showCategoriesError("No categories found");
        } else {
            categoryAdapter.setCategoryList(categories);
            recyclerViewCategories.setVisibility(View.VISIBLE);
            tvErrorCategories.setVisibility(View.GONE);
            progressCategories.setVisibility(View.GONE);
        }
    }

    private void openMealDetails(Meal meal) {
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
    public void onRandomMealFetchLoading() {
        showMealCardLoading(true);
    }

    @Override
    public void onRandomMealFetchError(String errMsg) {
        if (errMsg != null && errMsg.toLowerCase().contains("unable to resolve host")) {
            showMealCardError("No internet connection. Please check your network and try again.");
        } else {
            showMealCardError("Something went wrong. Please try again.");
        }
    }




    @Override
    public void onRandomMealFetchSuccess(Meal meal) {
        showMeal(meal);
    }

    @Override
    public void onAllCategoriesFetchLoading() {
        showCategoriesLoading(true);
    }

    @Override
    public void onAllCategoriesFetchError(String errMsg) {
        if (errMsg != null && errMsg.toLowerCase().contains("unable to resolve host")) {
            showCategoriesError("No internet connection. Please check your network and try again.");
        } else {
            showCategoriesError("Failed to load categories. Please try again later.");
        }
    }

    @Override
    public void onAllCategoriesFetchSuccess(List<Category> categories) {
        showCategories(categories);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (networkObserver != null) {
            networkObserver.unregister();
        }
    }
}

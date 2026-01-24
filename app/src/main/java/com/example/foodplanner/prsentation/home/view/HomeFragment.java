package com.example.foodplanner.prsentation.home.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.foodplanner.data.category.model.Category;
import com.example.foodplanner.prsentation.filtered_meals.view.FilteredMealsFragment;
import com.example.foodplanner.prsentation.home.presenter.HomePresenter;
import com.example.foodplanner.prsentation.home.presenter.HomePresenterImp;
import com.example.foodplanner.R;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsFragment;

import java.util.List;


public class HomeFragment extends Fragment implements HomeView ,CategoryOnClickListener{
public static String TAG="Home Fra";
ImageView iv_meal;
TextView name , desc;
HomePresenter homePresenter;
    RecyclerView recyclerViewCategories;
    CategoryAdapter categoryAdapter;

    ProgressBar imageProgress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_home, container, false);
        View mealCardView = view.findViewById(R.id.meal_card_include);        iv_meal=mealCardView.findViewById(R.id.iv_meal);
        name=mealCardView.findViewById(R.id.tv_name);
        desc=mealCardView.findViewById(R.id.tv_desc);
        imageProgress=mealCardView.findViewById(R.id.imageProgress);
        Log.d(TAG, "iv_meal = " + iv_meal);
        Log.d(TAG, "name = " + name);
        Log.d(TAG, "desc = " + desc);
        homePresenter = new HomePresenterImp(requireContext(),this);
        homePresenter.getRandomMeal();


        recyclerViewCategories = view.findViewById(R.id.recyclerViewCategories);
        categoryAdapter = new CategoryAdapter(this);

        recyclerViewCategories.setLayoutManager(
                new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        );
        recyclerViewCategories.setAdapter(categoryAdapter);

        homePresenter.getAllCategories();

        return view;

    }

    @Override
    public void onRandomMealFetchError(String errMsg) {

    }

    @Override
    public void onRandomMealFetchLoading() {

    }

    @Override
    public void onRandomMealFetchSuccess(List<Meal> meals) {

        Meal meal = meals.get(0);

        name.setText(meal.getStrMeal());
        desc.setText(meal.getStrInstructions());

        String mealId = meal.getIdMeal();

        imageProgress.setVisibility(View.VISIBLE);
        iv_meal.setVisibility(View.INVISIBLE);

        Glide.with(requireContext())
                .load(meal.getStrMealThumb())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(
                            GlideException e,
                            Object model,
                            Target<Drawable> target,
                            boolean isFirstResource
                    ) {
                        imageProgress.setVisibility(View.GONE);
                        iv_meal.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(
                            Drawable resource,
                            Object model,
                            Target<Drawable> target,
                            DataSource dataSource,
                            boolean isFirstResource
                    ) {
                        imageProgress.setVisibility(View.GONE);
                        iv_meal.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(iv_meal);

        View mealCardRoot = getView().findViewById(R.id.mealCardRoot);
        mealCardRoot.setOnClickListener(v -> {
            MealDetailsFragment fragment = new MealDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("idMeal", mealId);
            fragment.setArguments(bundle);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();


        });
    }


    @Override
    public void onAllCategoriesFetchError(String errMsg) {


    }

    @Override
    public void onAllCategoriesFetchLoading() {

    }

    @Override
    public void onAllCategoriesFetchSuccess(List<Category> categories) {
        categoryAdapter.setCategoryList(categories);

    }


    @Override
    public void onCategoryClick(Category category) {
        Bundle bundle = new Bundle();
        bundle.putString("category_name", category.getStrCategory());
        FilteredMealsFragment fragment = new FilteredMealsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
}
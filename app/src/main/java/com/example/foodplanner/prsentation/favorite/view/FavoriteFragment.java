package com.example.foodplanner.prsentation.favorite.view;

import static com.example.foodplanner.utils.SnackBarUtil.showSnack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.favorite.presenter.FavPresenter;
import com.example.foodplanner.prsentation.favorite.presenter.FavPresenterImpl;


public class FavoriteFragment extends Fragment implements FavView, OnFavoriteClickListener {

    private FavoriteAdapter favAdapter;
    private FavPresenter favPresenter;
    private   RecyclerView rvFavMeals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavMeals = view.findViewById(R.id.rvFavMeals);
        rvFavMeals.setLayoutManager(new LinearLayoutManager(requireContext()));

        favAdapter = new FavoriteAdapter(this);
        rvFavMeals.setAdapter(favAdapter);

        favPresenter = new FavPresenterImpl(requireContext(), this);
        favPresenter.getFavMeals().observe(
                getViewLifecycleOwner(), meals -> {
            favAdapter.setList(meals);
        });

        return view;
    }

    @Override
    public void onMealClick(Meal meal) {
        favPresenter.deleteFavMeal(meal);

    }



    @Override
    public void onMealDeleted() {
        showSnack(requireView().getRootView(),
                "Meal removed"
               );

    }

}

package com.example.foodplanner.prsentation.favorite.view;

import static com.example.foodplanner.utils.SnackBarUtil.showSnack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.favorite.presenter.FavPresenter;
import com.example.foodplanner.prsentation.favorite.presenter.FavPresenterImpl;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


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
        favPresenter.getFavMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> favAdapter.setList(meals),
                        throwable -> Log.e("FavError", "Failed to load favorites", throwable) // onError
                );

        return view;
    }

    @Override
    public void onMealClick(Meal meal) {
        favPresenter.deleteFavMeal(meal)
                .andThen(favPresenter.getFavMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            favAdapter.setList(meals);
                            onMealDeleted();
                        },
                        throwable -> Log.e("FavError", "Failed to delete and reload", throwable)
                );
    }





    @Override
    public void onMealDeleted() {
        showSnack(requireView().getRootView(),
                "Meal removed"
               );

    }

    @Override
    public void showMessage(String message) {
        showSnack(requireView(), message);
    }

}

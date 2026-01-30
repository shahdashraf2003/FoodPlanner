package com.example.foodplanner.prsentation.favorite.view;

import static com.example.foodplanner.utils.SnackBarUtil.showSnack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.favorite.presenter.FavPresenter;
import com.example.foodplanner.prsentation.favorite.presenter.FavPresenterImpl;
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsFragment;

import java.util.List;




public class FavoriteFragment extends Fragment implements FavView, OnFavoriteClickListener {

    private FavoriteAdapter favAdapter;
    private FavPresenter favPresenter;
    private RecyclerView rvFavMeals;
    private LinearLayout layoutEmptyFav;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavMeals = view.findViewById(R.id.rvFavMeals);
        rvFavMeals.setLayoutManager(new LinearLayoutManager(requireContext()));

        favAdapter = new FavoriteAdapter(this);
        rvFavMeals.setAdapter(favAdapter);
        layoutEmptyFav = view.findViewById(R.id.layoutEmptyFav);


        favPresenter = new FavPresenterImpl(requireContext(), this);
        favPresenter.getFavMeals();

        return view;
    }

    @Override
    public void onMealClick(Meal meal) {
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
    public void onMealDelelteClick(Meal meal) {
        favPresenter.deleteFavMeal(meal);
    }
    @Override
    public void showMeals(List<Meal> meals) {
        if (meals == null || meals.isEmpty()) {
            layoutEmptyFav.setVisibility(View.VISIBLE);
            rvFavMeals.setVisibility(View.GONE);
        } else {
            layoutEmptyFav.setVisibility(View.GONE);
            rvFavMeals.setVisibility(View.VISIBLE);
            favAdapter.setList(meals);
        }
    }

    @Override
    public void onNoMealsFounded() {
        layoutEmptyFav.setVisibility(View.VISIBLE);
        rvFavMeals.setVisibility(View.GONE);
    }

    @Override
    public void onMealDeleted() {
        favPresenter.getFavMeals();


    }

    @Override
    public void showMessage(String message) {
        showSnack(requireView(), message);
    }

}

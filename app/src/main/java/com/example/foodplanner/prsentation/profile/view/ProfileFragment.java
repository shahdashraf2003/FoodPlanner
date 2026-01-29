package com.example.foodplanner.prsentation.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.foodplanner.R;
import com.example.foodplanner.data.auth.local.SessionManager;
import com.example.foodplanner.data.auth.model.UserModel;
import com.example.foodplanner.data.meal.datasource.local.LocalMealsDao;
import com.example.foodplanner.database.AppDB;
import com.example.foodplanner.prsentation.auth.login.view.LoginActivity;
import com.example.foodplanner.prsentation.calender.view.CalenderFragment;
import com.example.foodplanner.prsentation.favorite.view.FavoriteFragment;
import com.example.foodplanner.prsentation.profile.presenter.ProfilePresenter;
import com.example.foodplanner.prsentation.profile.presenter.ProfilePresenterImp;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileFragment extends Fragment implements ProfileView {

    private Button btnFavourite, btnPlanned, btnLogout;
    private ProfilePresenter presenter;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnFavourite = view.findViewById(R.id.btnFavourite);
        btnPlanned = view.findViewById(R.id.btnPlanned);
        btnLogout = view.findViewById(R.id.btnLogout);

        sessionManager = new SessionManager(requireContext());
        LocalMealsDao localMealsDao = AppDB.getInstance(requireContext()).localMealsDao();
        presenter = new ProfilePresenterImp(this, requireContext(), localMealsDao);

        if(sessionManager.isGuest()) {
            showGuestMode();
        } else {
            presenter.loadUser();
        }


        btnLogout.setOnClickListener(v -> {
            if (!sessionManager.isGuest()) {
                String userId = sessionManager.getUserId();
                if (userId != null) {
                    presenter.uploadAndClearMeals(userId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> {
                                        Log.d("SYNC", "Uploading meals for user: " + userId);
                                        if (getActivity() != null) {
                                            getActivity().finishAffinity(); }

                                    },
                                    throwable -> {
                                        showMessage("Logout failed: " + throwable.getMessage());
                                    }
                            );
                } else {
                    sessionManager.logout()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> {

                                    }
                            );
                }
            } else {
                openLoginScreen();
            }
        });




        btnFavourite.setOnClickListener(v -> {
            FavoriteFragment fragment = new FavoriteFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();

        });

        btnPlanned.setOnClickListener(v -> {
            CalenderFragment fragment = new CalenderFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();

        });

        return view;
    }

    @Override
    public void showUser(UserModel user) {
        btnFavourite.setVisibility(View.VISIBLE);
        btnPlanned.setVisibility(View.VISIBLE);
        btnLogout.setText("Logout");
    }

    @Override
    public void showGuestMode() {
        btnLogout.setText("Login");
        btnFavourite.setVisibility(View.GONE);
        btnPlanned.setVisibility(View.GONE);
    }

    @Override
    public void openLoginScreen() {
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {}
}

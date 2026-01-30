package com.example.foodplanner.prsentation;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodplanner.prsentation.calender.view.CalenderFragment;
import com.example.foodplanner.prsentation.favorite.view.FavoriteFragment;
import com.example.foodplanner.prsentation.home.view.HomeFragment;
import com.example.foodplanner.R;
import com.example.foodplanner.prsentation.profile.view.ProfileFragment;
import com.example.foodplanner.prsentation.search.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager manager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        HomeFragment home = new HomeFragment();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.frame_layout, home, "home_fragment");
        transaction.commit();

        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.home_item) {
                        replaceFragment(new HomeFragment(), false);

                    } else if (item.getItemId() == R.id.search_item) {
                        replaceFragment(new SearchFragment(), false);
                    } else if (item.getItemId() == R.id.calender_item) {
                        replaceFragment(new CalenderFragment(), false);
                    } else if (item.getItemId() == R.id.fav_item) {
                        replaceFragment(new FavoriteFragment(), false);
                    } else if (item.getItemId() == R.id.profile_item) {
                        replaceFragment(new ProfileFragment(), false);
                    }
                    return true;
                }
        );

    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);

        if (!addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

}
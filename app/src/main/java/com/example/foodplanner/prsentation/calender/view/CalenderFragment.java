package com.example.foodplanner.prsentation.calender.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.model.Meal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalenderFragment extends Fragment implements OnCalenderedMealClickListener {

    private CalendarView calendarView;
    private TextView tvSelectedDate;
    private RecyclerView rvCalMeals;
    private CalenderAdapter calenderAdapter;
    private MealRepo mealRepo;
    private String selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = view.findViewById(R.id.calendarView);
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        rvCalMeals = view.findViewById(R.id.rvCalMeals);
        rvCalMeals.setLayoutManager(new LinearLayoutManager(requireContext()));
        calenderAdapter = new CalenderAdapter(this);
        rvCalMeals.setAdapter(calenderAdapter);

        mealRepo = new MealRepo(requireContext());

        Calendar today = Calendar.getInstance();
        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today.getTime());
        tvSelectedDate.setText("Meals for " + selectedDate);
        loadMealsForDate(selectedDate);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar selectedCal = Calendar.getInstance();
            selectedCal.set(year, month, dayOfMonth);
            selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedCal.getTime());
            tvSelectedDate.setText("Meals for " + selectedDate);
            loadMealsForDate(selectedDate);
        });
    }

    private void loadMealsForDate(String date) {
        mealRepo.getCalendarMeals().observe(getViewLifecycleOwner(), meals -> {
            List<Meal> filtered = meals.stream()
                    .filter(meal -> date.equals(meal.getCalendarDate()))
                    .toList();
            calenderAdapter.setList(filtered);
        });
    }



    @Override
    public void onCalenderedMealClick(Meal meal) {
        mealRepo.removeCalenderedMeal(meal);
    }
}

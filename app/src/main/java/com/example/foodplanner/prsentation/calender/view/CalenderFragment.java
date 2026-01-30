package com.example.foodplanner.prsentation.calender.view;

import android.os.Bundle;
import android.util.Log;
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
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.calender.presenter.CalenderPresenter;
import com.example.foodplanner.prsentation.calender.presenter.CalenderPresenterImp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalenderFragment extends Fragment
        implements OnCalenderedMealClickListener, CalenderView {

    private CalendarView calendarView;
    private TextView tvSelectedDate;
    private RecyclerView rvCalMeals;
    private CalenderAdapter adapter;
    private CalenderPresenter presenter;
    private String selectedDate;
    private TextView tvNoMeals;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = view.findViewById(R.id.calendarView);
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        rvCalMeals = view.findViewById(R.id.rvCalMeals);

        rvCalMeals.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CalenderAdapter(this);
        rvCalMeals.setAdapter(adapter);
        tvNoMeals = view.findViewById(R.id.tvNoMeals);


        presenter = new CalenderPresenterImp(requireContext(), this);

        Calendar today = Calendar.getInstance();
        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(today.getTime());
        tvSelectedDate.setText("Meals for " + selectedDate);

        presenter.getCalendarMeals(selectedDate);

        calendarView.setOnDateChangeListener((v, y, m, d) -> {
            Calendar c = Calendar.getInstance();
            c.set(y, m, d);
            selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(c.getTime());
            tvSelectedDate.setText("Meals for " + selectedDate);
            presenter.getCalendarMeals(selectedDate);
        });
    }

    @Override
    public void showMeals(List<Meal> meals) {
        adapter.setList(meals);
        rvCalMeals.setVisibility(View.VISIBLE);
        tvNoMeals.setVisibility(View.GONE);
    }


    @Override
    public void onNoMealsFounded() {
        adapter.setList(new ArrayList<>());
        rvCalMeals.setVisibility(View.GONE);
        tvNoMeals.setVisibility(View.VISIBLE);
    }


    @Override
    public void showError(String msg) {
        Log.e("DEBUG", msg);
    }

    @Override
    public void onMealRemoved(Meal meal) {
        presenter.getCalendarMeals(selectedDate);
    }

    @Override
    public void onCalenderedMealClick(Meal meal) {
        presenter.removeMealFromCalendar(meal);
    }
}

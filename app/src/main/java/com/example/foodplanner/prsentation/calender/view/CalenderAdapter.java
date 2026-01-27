package com.example.foodplanner.prsentation.calender.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.meal.model.loacl.LocalMeal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.CalViewHolder> {

    private List<LocalMeal> meals = new ArrayList<>();
    private OnCalenderedMealClickListener listener;
    private Context context;

    public CalenderAdapter(OnCalenderedMealClickListener listener) {
        this.listener = listener;
    }

    public void setList(List<LocalMeal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_calender, parent, false);
        return new CalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalViewHolder holder, int position) {
        LocalMeal meal = meals.get(position);
        holder.tvName.setText(meal.getStrMeal());
        holder.tvCategory.setText(meal.getStrCategory());
        Glide.with(holder.itemView)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.placeholder)
                .into(holder.ivPoster);

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCalenderedMealClick(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals == null ? 0 : meals.size();
    }

    static class CalViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPoster;
        TextView tvName, tvCategory;
        FloatingActionButton btnDelete;

        public CalViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_cal_poster);
            tvName = itemView.findViewById(R.id.tv_cal_name);
            tvCategory = itemView.findViewById(R.id.tv_cal_category);
            btnDelete = itemView.findViewById(R.id.fab_remove_cal_Meals);
        }
    }
}

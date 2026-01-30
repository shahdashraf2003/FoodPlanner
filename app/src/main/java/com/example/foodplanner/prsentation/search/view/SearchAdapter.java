package com.example.foodplanner.prsentation.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.meal.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Meal> meals = new ArrayList<>();
    private OnMealClickListener onMealClickListener;

    public SearchAdapter(OnMealClickListener onMealClickListener) {
        this.onMealClickListener = onMealClickListener;
    }



    public void setMeals(List<Meal> meals) {
        this.meals = meals != null ? meals : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void clearMeals() {
        meals.clear();
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filtered_meal_card, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mealImage;
        private final TextView mealName;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onMealClickListener != null) {
                    onMealClickListener.onMealClick(meals.get(position));
                }
            });
        }

        public void bind(Meal meal) {
            mealName.setText(meal.getStrMeal());

            Glide.with(itemView.getContext())
                    .load(meal.getStrMealThumb())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .centerCrop()
                    .into(mealImage);
        }
    }


}
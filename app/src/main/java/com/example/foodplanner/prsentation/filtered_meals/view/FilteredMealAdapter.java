package com.example.foodplanner.prsentation.filtered_meals.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.mealsfilterby.model.MealFilterBy;

import java.util.ArrayList;
import java.util.List;

public class FilteredMealAdapter extends RecyclerView.Adapter<FilteredMealAdapter.MealViewHolder> {

        private List<MealFilterBy> mealList = new ArrayList<>();
        private MealOnClickListener mealOnClickListener;

        public FilteredMealAdapter(MealOnClickListener mealOnClickListener) {
            this.mealOnClickListener = mealOnClickListener;
        }

        public void setMealList(List<MealFilterBy> mealList) {
            if (mealList != null) {
                this.mealList = mealList;
                notifyDataSetChanged();
            }
        }



        @NonNull
        @Override
        public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.filtered_meal_card, parent, false);
            return new MealViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
            MealFilterBy meal = mealList.get(position);
            holder.bind(meal);
        }

        @Override
        public int getItemCount() {
            return mealList != null ? mealList.size() : 0;
        }

        class MealViewHolder extends RecyclerView.ViewHolder {

            private final ImageView mealImageView;
            private final TextView mealNameTextView;

            public MealViewHolder(@NonNull View itemView) {
                super(itemView);

                mealImageView = itemView.findViewById(R.id.mealImage);
                mealNameTextView = itemView.findViewById(R.id.mealName);

                itemView.setOnClickListener(v -> {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mealOnClickListener != null) {
                        mealOnClickListener.onMealClick(mealList.get(position), position);
                    }
                });


            }

            public void bind(@NonNull MealFilterBy meal) {
                mealNameTextView.setText(meal.getStrMeal());

                Glide.with(itemView.getContext())
                        .load(meal.getStrMealThumb())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(mealImageView);
            }
        }

        public interface MealOnClickListener {
            void onMealClick(MealFilterBy meal, int position);


        }

}

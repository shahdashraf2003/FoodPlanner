
package com.example.foodplanner.prsentation.favorite.view;
import android.content.Context;
import android.util.Log;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {

    private List<Meal> meals ;
    private OnFavoriteClickListener listener;
    private Context context;

    public FavoriteAdapter(OnFavoriteClickListener listener) {

        this.listener = listener;
    }

    public void setList(List<Meal> meals) {
        this.meals = meals;
        Log.d("Fav", "setList: "+meals.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_favorite, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.tvName.setText(meal.getStrMeal());
        holder.tvCategory.setText(meal.getStrCategory());
        Glide.with(holder.itemView)
                .load(meal.getStrMealThumb())
                .into(holder.ivPoster);


        holder.btnDelete.setOnClickListener(v -> {
            listener.onMealDelelteClick(meal);
        });
        holder.itemView.setOnClickListener(v -> {
            listener.onMealClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return meals == null ? 0 : meals.size();
    }

    static class FavViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPoster;
        TextView tvName, tvCategory;
        FloatingActionButton btnDelete;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.iv_fav_poster);
            tvName = itemView.findViewById(R.id.tv_fav_name);
            tvCategory = itemView.findViewById(R.id.tv_fav_category);
            btnDelete = itemView.findViewById(R.id.fab_remove_Meals);
        }
    }
}


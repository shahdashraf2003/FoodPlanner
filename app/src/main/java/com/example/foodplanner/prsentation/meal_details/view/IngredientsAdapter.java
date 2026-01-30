package com.example.foodplanner.prsentation.meal_details.view;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.Target;
import com.example.foodplanner.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<String> ingredients;
    private List<String> measures;

    public IngredientsAdapter(List<String> ingredients, List<String> measures) {
        this.ingredients = ingredients;
        this.measures = measures;
    }

    public void setIngredients(List<String> ingredients, List<String> measures) {
        this.ingredients = ingredients;
        this.measures = measures;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ingredient = ingredients.get(position);
        String measure = measures.get(position);

        holder.tvIngredient.setText(ingredient);
        holder.tvMeasure.setText(measure);

        holder.progressBar.setVisibility(View.VISIBLE);
        holder.ivIngredient.setVisibility(View.INVISIBLE);

        Glide.with(holder.ivIngredient.getContext())
                .load("https://www.themealdb.com/images/ingredients/" +
                        ingredient.toLowerCase().replaceAll("\\s+", "_") +
                        "-medium.png")
                .listener(new com.bumptech.glide.request.RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(
                            @Nullable GlideException e,
                            Object model,
                            Target<Drawable> target,
                            boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.ivIngredient.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(
                            Drawable resource,
                            Object model,
                            Target<Drawable> target,
                            DataSource dataSource,
                            boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.ivIngredient.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.ivIngredient);
    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredient, tvMeasure;
        ImageView ivIngredient;
        ProgressBar progressBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredient = itemView.findViewById(R.id.tv_ingredient);
            tvMeasure = itemView.findViewById(R.id.tv_measure);
            ivIngredient = itemView.findViewById(R.id.iv_ingredient);
            progressBar = itemView.findViewById(R.id.progressIngredient);
        }
    }
}

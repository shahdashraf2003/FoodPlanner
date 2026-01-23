package com.example.foodplanner.prsentation.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.category.Category;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList = new ArrayList<>();
    private  CategoryOnClickListener categoryOnClickListener;

    public CategoryAdapter(CategoryOnClickListener categoryOnClickListener) {
        this.categoryList=new ArrayList<>();
        this.categoryOnClickListener = categoryOnClickListener;
    }

    public void setCategoryList(List<Category> categoryList) {
        if (categoryList != null) {
            this.categoryList = categoryList;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(categoryList.get(position));
    }

    @Override
    public int getItemCount() {

        return categoryList != null ? categoryList.size() : 0;
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView categoryImageView;
        private final TextView categoryNameTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImageView = itemView.findViewById(R.id.iv_category);
            categoryNameTextView = itemView.findViewById(R.id.tv_category_name);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && categoryOnClickListener != null) {
                    categoryOnClickListener.onCategoryClick(categoryList.get(position));
                }
            });
        }

        public void bind(@NonNull Category category) {


            categoryNameTextView.setText(category.getStrCategory());

            Glide.with(itemView.getContext())
                    .load(category.getStrCategoryThumb())
                    .centerCrop()
                    .into(categoryImageView);
        }
    }

    public interface CategoryOnClickListener {
        void onCategoryClick(Category category);
    }
}

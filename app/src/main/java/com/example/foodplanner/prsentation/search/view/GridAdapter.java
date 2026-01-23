package com.example.foodplanner.prsentation.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.common.DisplayItem;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private List<DisplayItem> items;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(DisplayItem item);
    }

    public GridAdapter(List<DisplayItem> items, OnItemClickListener listener) {
        this.items = items != null ? items : new ArrayList<>();
        this.listener = listener;
    }

    public void setItems(List<DisplayItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DisplayItem item = items.get(position);
        holder.bind(item);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_item_name);
        }

        void bind(DisplayItem item) {
            nameTextView.setText(item.getName());
        }
    }
}
package com.example.mentalhealthtracker.WebSupportArticles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthtracker.R;
import com.example.mentalhealthtracker.WebSupportArticles.db.WebSupportCategory;

import java.util.List;

public class WebSupportCategoryAdapter extends RecyclerView.Adapter<WebSupportCategoryAdapter.WebSupportCategoryHolder>  {
    private List<WebSupportCategory> categories;
    private OnWebSupportCategoryClicked sListener;

    public WebSupportCategoryAdapter(List<WebSupportCategory> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public WebSupportCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.web_support_category_card, parent, false
        );
        return new WebSupportCategoryAdapter.WebSupportCategoryHolder(v, sListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WebSupportCategoryHolder holder, int position) {
        WebSupportCategory indexedCategory = this.categories.get(position);
        holder.categoryTitle.setText(indexedCategory.getCategoryName());
        holder.categoryDescription.setText(indexedCategory.getCategoryDescription());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<WebSupportCategory> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public void setOnClickListener(WebSupportCategoryAdapter.OnWebSupportCategoryClicked listener) {
        this.sListener = listener;
    }

    public interface OnWebSupportCategoryClicked {
        void onWebSupportCategoryClicked(int position);
    }

    public static class WebSupportCategoryHolder extends RecyclerView.ViewHolder {
        public TextView categoryTitle;
        public TextView categoryDescription;

        public WebSupportCategoryHolder(@NonNull View itemView,
                                        OnWebSupportCategoryClicked listener) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.webSupportCategoryTitle);
            categoryDescription = itemView.findViewById(R.id.webSupportCategoryDescription);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onWebSupportCategoryClicked(position);
                    }
                }
            });
        }
    }
}

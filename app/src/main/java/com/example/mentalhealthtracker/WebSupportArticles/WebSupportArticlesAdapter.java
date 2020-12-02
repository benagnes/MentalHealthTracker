package com.example.mentalhealthtracker.WebSupportArticles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthtracker.R;
import com.example.mentalhealthtracker.WebSupportArticles.db.WebSupportArticle;

import java.util.ArrayList;
import java.util.List;

public class WebSupportArticlesAdapter extends
        RecyclerView.Adapter<WebSupportArticlesAdapter.WebSupportArticleHolder>
        implements Filterable {
    private List<WebSupportArticle> allArticles;
    private List<WebSupportArticle> filteredArticles;
    private OnWebSupportArticleClicked sListener;
    private static final int MAX_ARTICLE_DESC_SIZE = 100;

    public WebSupportArticlesAdapter(List<WebSupportArticle> articles) {
        this.allArticles = articles;
        this.filteredArticles = new ArrayList<>(articles);
    }

    @NonNull
    @Override
    public WebSupportArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.web_support_article_card, parent, false
        );

        return new WebSupportArticlesAdapter.WebSupportArticleHolder(v, sListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WebSupportArticleHolder holder, int position) {
        WebSupportArticle indexedArticle = this.filteredArticles.get(position);
        String articleDescription = indexedArticle.getArticleContent();

        if (articleDescription.length() > MAX_ARTICLE_DESC_SIZE) {
            articleDescription = articleDescription.substring(0, MAX_ARTICLE_DESC_SIZE - 1);
            articleDescription = articleDescription + " ...";
        }

        holder.articleDescription.setText(articleDescription);
        holder.articleTitle.setText(indexedArticle.getArticleTitle());
    }

    public void setArticles(List<WebSupportArticle> articles) {
        this.allArticles = articles;
        this.filteredArticles = new ArrayList<>(articles);
        notifyDataSetChanged();
    }

    public WebSupportArticle getArticleAtIndex(int index) {
        return filteredArticles.get(index);
    }

    public void setOnClickListener(WebSupportArticlesAdapter.OnWebSupportArticleClicked listener) {
        this.sListener = listener;
    }

    @Override
    public int getItemCount() {
        return filteredArticles.size();
    }

    @Override
    public Filter getFilter() {
        return articleFilter;
    }

    private final Filter articleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<WebSupportArticle> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allArticles);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (WebSupportArticle a : allArticles) {
                    if (a.getArticleTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(a);
                    } else if (a.getArticleContent().toLowerCase().contains(filterPattern)) {
                        filteredList.add(a);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredArticles.clear();

            @SuppressWarnings("unchecked")
            List<WebSupportArticle> articleList = (List<WebSupportArticle>) results.values;
            filteredArticles.addAll(articleList);

            notifyDataSetChanged();
        }
    };

    public interface OnWebSupportArticleClicked {
        void onWebSupportArticleClicked(int position);
    }

    public static class WebSupportArticleHolder extends RecyclerView.ViewHolder {
        public TextView articleTitle;
        public TextView articleDescription;

        public WebSupportArticleHolder(@NonNull View itemView, OnWebSupportArticleClicked listener) {
            super(itemView);

            articleTitle = itemView.findViewById(R.id.webSupportArticleTitle);
            articleDescription = itemView.findViewById(R.id.webSupportArticleContent);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onWebSupportArticleClicked(position);
                    }
                }
            });
        }
    }
}

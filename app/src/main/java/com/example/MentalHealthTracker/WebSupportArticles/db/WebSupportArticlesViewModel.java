package com.example.MentalHealthTracker.WebSupportArticles.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WebSupportArticlesViewModel extends AndroidViewModel {
    private WebSupportArticlesDatabase db;
    private LiveData<List<WebSupportArticle>> articles;
    private int categoryId;

    public WebSupportArticlesViewModel(@NonNull Application application) {
        super(application);
        this.db = WebSupportArticlesDatabase.getInstance(application);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void loadArticles() {
        this.articles = db.articleDao().getSupportArticles(this.categoryId);
    }

    public LiveData<List<WebSupportArticle>> getArticles() {
        return articles;
    }
}

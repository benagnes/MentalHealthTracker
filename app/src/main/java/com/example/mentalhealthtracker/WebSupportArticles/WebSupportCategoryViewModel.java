package com.example.mentalhealthtracker.WebSupportArticles;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mentalhealthtracker.WebSupportArticles.db.WebSupportArticlesDatabase;
import com.example.mentalhealthtracker.WebSupportArticles.db.WebSupportCategory;

import java.util.List;

public class WebSupportCategoryViewModel extends AndroidViewModel {
    private WebSupportArticlesDatabase db;
    private LiveData<List<WebSupportCategory>> categories;

    public WebSupportCategoryViewModel(@NonNull Application application) {
        super(application);
        this.db = WebSupportArticlesDatabase.getInstance(application);
        this.categories = db.categoryDao().getAllWebSupportCategories();
    }

    public LiveData<List<WebSupportCategory>> getCategories() {
        return categories;
    }

    public void setCategories(LiveData<List<WebSupportCategory>> categories) {
        this.categories = categories;
    }
}

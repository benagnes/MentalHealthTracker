package com.example.MentalHealthTracker.WebSupportArticles.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WebSupportArticleDao {
    @Query("SELECT * FROM web_support_article WHERE category=:categoryId")
    LiveData<List<WebSupportArticle>> getSupportArticles(final int categoryId);

    @Insert
    void insertAll(WebSupportArticle... articles);
}

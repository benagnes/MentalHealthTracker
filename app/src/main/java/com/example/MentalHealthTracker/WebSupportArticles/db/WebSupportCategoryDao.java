package com.example.MentalHealthTracker.WebSupportArticles.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WebSupportCategoryDao {
    @Query("SELECT * FROM web_support_category")
    LiveData<List<WebSupportCategory>> getAllWebSupportCategories();

    @Insert
    void insertAll(WebSupportCategory... categories);
}

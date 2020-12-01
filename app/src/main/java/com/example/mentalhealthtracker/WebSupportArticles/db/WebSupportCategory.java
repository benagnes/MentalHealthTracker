package com.example.mentalhealthtracker.WebSupportArticles.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "web_support_category")
public class WebSupportCategory {
    @PrimaryKey
    private int webSupportCategoryId;
    private String categoryName;
    private String categoryDescription;

    public WebSupportCategory(int webSupportCategoryId, String categoryName, String categoryDescription) {
        this.webSupportCategoryId = webSupportCategoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public int getWebSupportCategoryId() {
        return webSupportCategoryId;
    }

    public void setWebSupportCategoryId(int webSupportCategoryId) {
        this.webSupportCategoryId = webSupportCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}

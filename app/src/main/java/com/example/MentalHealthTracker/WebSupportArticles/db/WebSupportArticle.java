package com.example.MentalHealthTracker.WebSupportArticles.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "web_support_article",
        foreignKeys = @ForeignKey(
                entity = WebSupportCategory.class,
                parentColumns = "webSupportCategoryId",
                childColumns = "category",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(value = {"category"})
        }
)
public class WebSupportArticle {
    @PrimaryKey(autoGenerate = true)
    private int articleId;
    private int category;
    private String articleTitle;
    private String articleContent;

    public WebSupportArticle(int category, String articleTitle, String articleContent) {
        this.category = category;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}

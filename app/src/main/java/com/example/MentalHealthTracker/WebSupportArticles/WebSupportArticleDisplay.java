package com.example.MentalHealthTracker.WebSupportArticles;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.MentalHealthTracker.R;

public class WebSupportArticleDisplay extends AppCompatActivity {
    private static final String appBarTitle = "Article";
    private static final String TAG = "ArticleDisplay";
    public static final String ARTICLE_TITLE_REF = "ARTICLE_TITLE_REF";
    public static final String ARTICLE_CONTENT_REF = "ARTICLE_CONTENT_REF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_support_article_display);

        ActionBar actionBar = getSupportActionBar();
        TextView articleTitle = findViewById(R.id.articleFullTitle);
        TextView articleContent = findViewById(R.id.articleFullContent);
        Bundle extras = getIntent().getExtras();

        if (actionBar != null) {
            actionBar.setTitle(appBarTitle);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
        }

        // No extras passed, we need to handle this improper usage of this activity
        if (extras == null) {
            Log.e(TAG, "No extras passed to this activity, however some were expected");
            return;
        }

        articleTitle.setText(extras.getString(ARTICLE_TITLE_REF));
        articleContent.setText(extras.getString(ARTICLE_CONTENT_REF));
    }
}
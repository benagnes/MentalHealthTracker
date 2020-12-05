package com.example.MentalHealthTracker.WebSupportArticles;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.MentalHealthTracker.R;
import com.example.MentalHealthTracker.WebSupportArticles.db.WebSupportArticle;
import com.example.MentalHealthTracker.WebSupportArticles.db.WebSupportArticlesViewModel;

import java.util.ArrayList;

public class WebSupportArticleView extends AppCompatActivity {
    // attributes
    private static final String TAG = "WebSupportArticles";
    private static final String appBarTitle = "Articles";
    public static final String CATEGORY_REF = "CATEGORY_REF";

    private WebSupportArticlesAdapter adapter;
    private WebSupportArticlesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_support_article_view);

        ActionBar mainActionBar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        int webSupportCategoryId;

        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // No extras passed, we need to handle this improper usage of this activity
        if (extras == null) {
            Log.e(TAG, "No extras passed to this activity, however some were expected");
            return;
        }

        try {
            String activityIdValue = extras.getString(CATEGORY_REF);
            webSupportCategoryId = Integer.parseInt(activityIdValue);
        } catch (NumberFormatException eNumberFormat) {
            Log.e(TAG, "Failed parsing category id, received: " +
                    extras.getString(CATEGORY_REF));
            return;
        } catch (NullPointerException eNullPtr) {
            Log.e(TAG, "No value received for category id");
            return;
        }

        // Setting up activity view
        RecyclerView activitiesView = findViewById(R.id.webSupportArticles);
        activitiesView.setHasFixedSize(true);
        RecyclerView.LayoutManager activityLayoutManager = new LinearLayoutManager(this);
        adapter = new WebSupportArticlesAdapter(new ArrayList<>());
        activitiesView.setLayoutManager(activityLayoutManager);
        activitiesView.setAdapter(adapter);

        // Setting up recycler
        viewModel = new ViewModelProvider(this).get(WebSupportArticlesViewModel.class);
        viewModel.setCategoryId(webSupportCategoryId);
        viewModel.loadArticles();
        viewModel.getArticles().observe(this, articles -> {
            // Activities were loaded
            adapter.setArticles(articles);
        });
        adapter.setOnClickListener(this::selectArticle);
    }

    // Open a Mindfulness activity
    private void selectArticle(int position) {
        Intent articleActivity = new Intent(this, WebSupportArticleDisplay.class);

        WebSupportArticle chosenArticle = adapter.getArticleAtIndex(position);

        // Passing data to next activity
        articleActivity.putExtra(
                WebSupportArticleDisplay.ARTICLE_TITLE_REF,
                chosenArticle.getArticleTitle()
        );
        articleActivity.putExtra(
                WebSupportArticleDisplay.ARTICLE_CONTENT_REF,
                chosenArticle.getArticleContent()
        );

        startActivity(articleActivity);
    }

    // Called when the menu is about to be created
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_search_menu, menu);

        // Find and set up the search view
        MenuItem searchItem = menu.findItem(R.id.articleSearch);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView)
                searchItem.getActionView();

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView
                .OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
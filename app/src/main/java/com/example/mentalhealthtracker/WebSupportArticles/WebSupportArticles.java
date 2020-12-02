package com.example.mentalhealthtracker.WebSupportArticles;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthtracker.R;
import com.example.mentalhealthtracker.WebSupportArticles.db.WebSupportCategory;

import java.util.ArrayList;
import java.util.Objects;

public class WebSupportArticles extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Web Support Articles";
    private WebSupportCategoryAdapter adapter;
    private WebSupportCategoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_support_articles);

        ActionBar mainActionBar = getSupportActionBar();

        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView categoriesView = findViewById(R.id.webSupportCategories);
        categoriesView.setHasFixedSize(true);
        RecyclerView.LayoutManager activityLayoutManager = new LinearLayoutManager(this);
        adapter = new WebSupportCategoryAdapter(new ArrayList<>());
        categoriesView.setLayoutManager(activityLayoutManager);
        categoriesView.setAdapter(adapter);

        // Setting up recycler
        viewModel = new ViewModelProvider(this).get(WebSupportCategoryViewModel.class);
        viewModel.getCategories().observe(this, activities -> {
            // Activities were loaded
            adapter.setCategories(activities);
        });
        adapter.setOnClickListener(this::selectCategory);
    }

    // Open the articles for this category
    private void selectCategory(int position) {
        Intent articleActivity = new Intent(this, WebSupportArticleView.class);

        // Get the id for the category that was chosen
        WebSupportCategory category = Objects.requireNonNull(
                viewModel.getCategories().getValue().get(position)
        );
        articleActivity.putExtra(WebSupportArticleView.CATEGORY_REF,
                Integer.toString(category.getWebSupportCategoryId()));

        startActivity(articleActivity);
    }
}

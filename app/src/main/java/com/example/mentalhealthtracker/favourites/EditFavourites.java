package com.example.mentalhealthtracker.favourites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthtracker.R;
import com.example.mentalhealthtracker.Yoga;

public class EditFavourites extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Edit Favourites";

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_favourites);

        ActionBar mainActionBar = getSupportActionBar();

        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Defaults for favourite resources
        Button favourites1Button = (Button) findViewById(R.id.selectFavourite1Button);
        favourites1Button.setOnClickListener(v -> selectFavourite());

        Button favourites2Button = (Button) findViewById(R.id.selectFavourite2Button);
        favourites2Button.setOnClickListener(v -> selectFavourite());

        Button favourites3Button = (Button) findViewById(R.id.selectFavourite3Button);
        favourites3Button.setOnClickListener(v -> selectFavourite());
    }

    public void selectFavourite() {
        Intent intent = new Intent(this, SelectFavourites.class);
        startActivity(intent);
    }

}

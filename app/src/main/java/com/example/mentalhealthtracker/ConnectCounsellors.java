package com.example.mentalhealthtracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ConnectCounsellors extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Connect with Counsellors";

    TextView connectCounsellorsInfo;
    Button CMHAButton;
    Button here2talkButton;
    Button crisisLineButton;
    Button youthspaceButton;
    TextView resourceInfo;
    Button backButton;



    public void hideButtons() {
        CMHAButton.setVisibility(View.INVISIBLE);
        here2talkButton.setVisibility(View.INVISIBLE);
        crisisLineButton.setVisibility(View.INVISIBLE);
        youthspaceButton.setVisibility(View.INVISIBLE);
    }

    public void showButtons() {
        CMHAButton.setVisibility(View.VISIBLE);
        here2talkButton.setVisibility(View.VISIBLE);
        crisisLineButton.setVisibility(View.VISIBLE);
        youthspaceButton.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_counsellors);

        connectCounsellorsInfo = findViewById(R.id.connectCounsellorsInfoTextView);
        CMHAButton = findViewById(R.id.cmhaButton);
        here2talkButton = findViewById(R.id.here2talkButton);
        crisisLineButton = findViewById(R.id.crisisLineButton);
        youthspaceButton = findViewById(R.id.youthspaceButton);
        resourceInfo = findViewById(R.id.resourceInfoTextView);
        backButton = findViewById(R.id.backButton);


        CMHAButton.setText(R.string.cmha);
        CMHAButton.setOnClickListener(v -> {
            hideButtons();
            //connectCounsellorsInfo.setVisibility(View.INVISIBLE);
            connectCounsellorsInfo.setText(R.string.cmha);
            resourceInfo.setVisibility(View.VISIBLE);
            resourceInfo.setText(R.string.cmhaResourceInfo);
            backButton.setVisibility(View.VISIBLE);
        });

        here2talkButton.setText(R.string.here2talk);
        here2talkButton.setOnClickListener(v -> {
            hideButtons();
            //connectCounsellorsInfo.setVisibility(View.INVISIBLE);
            connectCounsellorsInfo.setText(R.string.here2talk);
            resourceInfo.setVisibility(View.VISIBLE);
            resourceInfo.setText(R.string.here2talkResourceInfo);
        });

        crisisLineButton.setText(R.string.crisisLine);
        crisisLineButton.setOnClickListener(v -> {
            hideButtons();
            //connectCounsellorsInfo.setVisibility(View.INVISIBLE);
            connectCounsellorsInfo.setText(R.string.crisisLine);
            resourceInfo.setVisibility(View.VISIBLE);
            resourceInfo.setText(R.string.crisisLineResourceInfo);
        });

        youthspaceButton.setText(R.string.youthspace);
        youthspaceButton.setOnClickListener(v -> {
            hideButtons();
            //connectCounsellorsInfo.setVisibility(View.INVISIBLE);
            connectCounsellorsInfo.setText(R.string.youthspace);
            resourceInfo.setVisibility(View.VISIBLE);
            resourceInfo.setText(R.string.youthspaeResourceInfo);
        });



        backButton.setText(R.string.back);
        backButton.setOnClickListener(v -> {
            resourceInfo.setVisibility(View.INVISIBLE);
            //connectCounsellorsInfo.setVisibility(View.VISIBLE);
            connectCounsellorsInfo.setText(R.string.connectCounsellorsInfoText);
            showButtons();
        });


        ActionBar mainActionBar = getSupportActionBar();

        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }


    }
}
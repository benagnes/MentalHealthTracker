package com.example.MentalHealthTracker.mood_tracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.MentalHealthTracker.R;

public class MoodTracker extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Mood Tracker";

    // Mood Database handler
    private MoodDBhandler DBhandler;

    // Support attributes to pass entries into database
    Button recordMood;
    private TextView selected_dateView;
    private String selected_date;
    private int dateInt;
    private String entered_mood;
    private String entered_energy;
    private String entered_social_meter;
    private String entered_productivity;

    // Textview's to display mood, energy, social meter, and productivity entry
    private TextView savedEntry;
    private TextView recorded_mood;
    private TextView recorded_energy;
    private TextView recorded_socialMeter;
    private TextView recorded_productivity;

    // pop-up windows to choose mood options
    PopupWindow MoodWindow;
    PopupWindow EnergyWindow;
    PopupWindow SocialMeterWindow;
    PopupWindow ProductivityWindow;

    // methods
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_tracker);

        ActionBar mainActionBar = getSupportActionBar();
        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }

        DBhandler = new MoodDBhandler(this, null);

        // Configure recorded entry TextView's
        recorded_mood = findViewById(R.id.ViewMood);
        recorded_mood.setVisibility(View.INVISIBLE);

        recorded_energy = findViewById(R.id.ViewEnergy);
        recorded_energy.setVisibility(View.INVISIBLE);

        recorded_socialMeter = findViewById(R.id.ViewSocialMeter);
        recorded_socialMeter.setVisibility(View.INVISIBLE);

        recorded_productivity = findViewById(R.id.ViewProductivity);
        recorded_productivity.setVisibility(View.INVISIBLE);

        // Configure calendar and selected date
        CalendarView calendar = findViewById(R.id.moodCalendar);
        calendar.setMaxDate(System.currentTimeMillis() - 1000);
        calendar.setOnDateChangeListener(
                (view, year, month, dayOfMonth) -> {
                    // month index starts with 0
                    String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
                    String dateForInt = dayOfMonth + (month + "1") + year;
                    dateInt = Integer.parseInt(dateForInt);
                    String msg = "Date Selected: " + Date;
                    // Display selected date
                    selected_dateView.setText(msg);
                    selected_date = Date;
                    recordMood.setVisibility(View.VISIBLE);
                    if (isMoodRecordedForSelectedDate()) {
                        recordMood.setText(R.string.changeMood);
                        Mood mood = DBhandler.findHandler(selected_date);
                        savedEntry.setVisibility(View.VISIBLE);
                        recorded_mood.setText("Mood: " + mood.getMood());
                        recorded_mood.setVisibility(View.VISIBLE);
                        recorded_energy.setText("Energy: " + mood.getEnergy());
                        recorded_energy.setVisibility(View.VISIBLE);
                        recorded_socialMeter.setText("Social Meter: " + mood.getSocial_meter());
                        recorded_socialMeter.setVisibility(View.VISIBLE);
                        recorded_productivity.setText("Productivity: " + mood.getProductivity());
                        recorded_productivity.setVisibility(View.VISIBLE);
                    }
                    else {
                        recordMood.setText(R.string.recordMood);
                        savedEntry.setVisibility(View.INVISIBLE);
                        recorded_mood.setVisibility(View.INVISIBLE);
                        recorded_energy.setVisibility(View.INVISIBLE);
                        recorded_socialMeter.setVisibility(View.INVISIBLE);
                        recorded_productivity.setVisibility(View.INVISIBLE);
                    }
                });
        selected_dateView = findViewById(R.id.selectDate);

        // Initialize pop-up windows
        MoodWindow = new PopupWindow(this);
        LinearLayout moodLayout = new LinearLayout(this);

        EnergyWindow = new PopupWindow(this);
        LinearLayout energyLayout = new LinearLayout(this);

        SocialMeterWindow = new PopupWindow(this);
        LinearLayout socialMeterLayout = new LinearLayout(this);

        ProductivityWindow = new PopupWindow(this);
        LinearLayout productivityLayout = new LinearLayout(this);

        savedEntry = findViewById(R.id.savedMoodHeader);
        savedEntry.setVisibility(View.INVISIBLE);

        // Configure mood pop-up panel
        recordMood = findViewById(R.id.recordMoodButton);
        recordMood.setVisibility(View.INVISIBLE);
        recordMood.setOnClickListener(v -> {
            MoodWindow.setWidth(700);
            MoodWindow.setHeight(800);
            MoodWindow.showAtLocation(moodLayout, Gravity.CENTER, 0, 0);
        });

        Button confirm_mood = new Button(this);
        confirm_mood.setText(R.string.confirmMood);
        confirm_mood.setVisibility(View.INVISIBLE);
        confirm_mood.setOnClickListener(v -> {
            MoodWindow.dismiss();
            confirm_mood.setVisibility(View.INVISIBLE);

            EnergyWindow.setWidth(700);
            EnergyWindow.setHeight(800);
            EnergyWindow.showAtLocation(moodLayout, Gravity.CENTER, 0, 0);
        });

        Button happyMood = new Button(this);
        happyMood.setText(R.string.happyMood);
        happyMood.setOnClickListener(v -> {
            entered_mood = happyMood.getText().toString();
            confirm_mood.setVisibility(View.VISIBLE);
        });

        Button sadMood = new Button(this);
        sadMood.setText(R.string.sadMood);
        sadMood.setOnClickListener(v -> {
            entered_mood = sadMood.getText().toString();
            confirm_mood.setVisibility(View.VISIBLE);
        });

        Button relaxedMood = new Button(this);
        relaxedMood.setText(R.string.relaxedMood);
        relaxedMood.setOnClickListener(v -> {
            entered_mood = relaxedMood.getText().toString();
            confirm_mood.setVisibility(View.VISIBLE);
        });

        Button stressedMood = new Button(this);
        stressedMood.setText(R.string.stressedMood);
        stressedMood.setOnClickListener(v -> {
            entered_mood = stressedMood.getText().toString();
            confirm_mood.setVisibility(View.VISIBLE);
        });

        Button angryMood = new Button(this);
        angryMood.setText(R.string.angryMood);
        angryMood.setOnClickListener(v -> {
            entered_mood = angryMood.getText().toString();
            confirm_mood.setVisibility(View.VISIBLE);
        });

        LinearLayout.LayoutParams mood_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        moodLayout.setOrientation(LinearLayout.VERTICAL);
        moodLayout.addView(happyMood, mood_params);
        moodLayout.addView(sadMood, mood_params);
        moodLayout.addView(relaxedMood, mood_params);
        moodLayout.addView(stressedMood, mood_params);
        moodLayout.addView(angryMood, mood_params);
        moodLayout.addView(confirm_mood, mood_params);
        MoodWindow.setContentView(moodLayout);

        // configure Energy pop-up panel
        Button confirm_energy = new Button(this);
        confirm_energy.setText(R.string.confirmEnergy);
        confirm_energy.setVisibility(View.INVISIBLE);
        confirm_energy.setOnClickListener( v -> {
            EnergyWindow.dismiss();
            confirm_energy.setVisibility(View.INVISIBLE);

            SocialMeterWindow.setWidth(700);
            SocialMeterWindow.setHeight(800);
            SocialMeterWindow.showAtLocation(productivityLayout, Gravity.CENTER, 0, 0);
        });

        Button sleepEnergy = new Button(this);
        sleepEnergy.setText(R.string.sleepEnergy);
        sleepEnergy.setOnClickListener(v -> {
            entered_energy = sleepEnergy.getText().toString();
            confirm_energy.setVisibility(View.VISIBLE);
        });

        Button lowEnergy = new Button(this);
        lowEnergy.setText(R.string.lowEnergy);
        lowEnergy.setOnClickListener(v -> {
            entered_energy = lowEnergy.getText().toString();
            confirm_energy.setVisibility(View.VISIBLE);
        });

        Button normalEnergy = new Button(this);
        normalEnergy.setText(R.string.normalEnergy);
        normalEnergy.setOnClickListener( v -> {
            entered_energy = normalEnergy.getText().toString();
            confirm_energy.setVisibility(View.VISIBLE);
        });

        Button highEnergy = new Button(this);
        highEnergy.setText(R.string.highEnergy);
        highEnergy.setOnClickListener( v -> {
            entered_energy = highEnergy.getText().toString();
            confirm_energy.setVisibility(View.VISIBLE);
        });

        Button insaneEnergy = new Button(this);
        insaneEnergy.setText(R.string.insaneEnergy);
        insaneEnergy.setOnClickListener( v -> {
            entered_energy = insaneEnergy.getText().toString();
            confirm_energy.setVisibility(View.VISIBLE);
        });

        LinearLayout.LayoutParams energy_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        energyLayout.setOrientation(LinearLayout.VERTICAL);
        energyLayout.addView(sleepEnergy, energy_params);
        energyLayout.addView(lowEnergy, energy_params);
        energyLayout.addView(normalEnergy, energy_params);
        energyLayout.addView(highEnergy, energy_params);
        energyLayout.addView(insaneEnergy, energy_params);
        energyLayout.addView(confirm_energy, energy_params);
        EnergyWindow.setContentView(energyLayout);

        // configure Social Meter pop-up panel
        Button confirm_social_meter = new Button(this);
        confirm_social_meter.setText(R.string.confirmSocialMeter);
        confirm_social_meter.setVisibility(View.INVISIBLE);
        confirm_social_meter.setOnClickListener( v -> {
            SocialMeterWindow.dismiss();
            confirm_social_meter.setVisibility(View.INVISIBLE);

            ProductivityWindow.setWidth(700);
            ProductivityWindow.setHeight(800);
            ProductivityWindow.showAtLocation(productivityLayout, Gravity.CENTER, 0, 0);
        });

        Button soloSocial = new Button(this);
        soloSocial.setText(R.string.soloSocial);
        soloSocial.setOnClickListener( v -> {
            entered_social_meter = soloSocial.getText().toString();
            confirm_social_meter.setVisibility(View.VISIBLE);
        });

        Button drainedSocial = new Button(this);
        drainedSocial.setText(R.string.drainedSocial);
        drainedSocial.setOnClickListener( v -> {
            entered_social_meter = drainedSocial.getText().toString();
            confirm_social_meter.setVisibility(View.VISIBLE);
        });

        Button undecidedSocial = new Button(this);
        undecidedSocial.setText(R.string.undecidedSocial);
        undecidedSocial.setOnClickListener( v -> {
            entered_social_meter = undecidedSocial.getText().toString();
            confirm_social_meter.setVisibility(View.VISIBLE);
        });

        Button socialSocial = new Button(this);
        socialSocial.setText(R.string.socialSocial);
        socialSocial.setOnClickListener( v -> {
            entered_social_meter = socialSocial.getText().toString();
            confirm_social_meter.setVisibility(View.VISIBLE);
        });

        Button partySocial = new Button(this);
        partySocial.setText(R.string.partySocial);
        partySocial.setOnClickListener( v -> {
            entered_social_meter = partySocial.getText().toString();
            confirm_social_meter.setVisibility(View.VISIBLE);
        });

        LinearLayout.LayoutParams social_meter_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        socialMeterLayout.setOrientation(LinearLayout.VERTICAL);
        socialMeterLayout.addView(soloSocial, social_meter_params);
        socialMeterLayout.addView(drainedSocial, social_meter_params);
        socialMeterLayout.addView(undecidedSocial, social_meter_params);
        socialMeterLayout.addView(socialSocial, social_meter_params);
        socialMeterLayout.addView(partySocial, social_meter_params);
        socialMeterLayout.addView(confirm_social_meter, social_meter_params);
        SocialMeterWindow.setContentView(socialMeterLayout);

        // configure Productivity Meter pop-up panel
        Button confirm_productivity = new Button(this);
        confirm_productivity.setText(R.string.confirmProductivity);
        confirm_productivity.setVisibility(View.INVISIBLE);
        confirm_productivity.setOnClickListener( v -> {
            ProductivityWindow.dismiss();
            confirm_productivity.setVisibility(View.INVISIBLE);
            if(addMood()) {
                getMood();
            }
        });

        Button unproductive = new Button(this);
        unproductive.setText(R.string.unproductive);
        unproductive.setOnClickListener( v -> {
            entered_productivity = unproductive.getText().toString();
            confirm_productivity.setVisibility(View.VISIBLE);
        });

        Button distracted = new Button(this);
        distracted.setText(R.string.distracted);
        distracted.setOnClickListener( v -> {
            entered_productivity = distracted.getText().toString();
            confirm_productivity.setVisibility(View.VISIBLE);
        });

        Button mediocre = new Button(this);
        mediocre.setText(R.string.mediocore);
        mediocre.setOnClickListener( v -> {
            entered_productivity = mediocre.getText().toString();
            confirm_productivity.setVisibility(View.VISIBLE);
        });

        Button productive = new Button(this);
        productive.setText(R.string.productive);
        productive.setOnClickListener( v -> {
            entered_productivity = productive.getText().toString();
            confirm_productivity.setVisibility(View.VISIBLE);
        });

        Button accomplished = new Button(this);
        accomplished.setText(R.string.accomplished);
        accomplished.setOnClickListener( v -> {
            entered_productivity = accomplished.getText().toString();
            confirm_productivity.setVisibility(View.VISIBLE);
        });

        LinearLayout.LayoutParams productivity_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        productivityLayout.setOrientation(LinearLayout.VERTICAL);
        productivityLayout.addView(distracted, productivity_params);
        productivityLayout.addView(unproductive, productivity_params);
        productivityLayout.addView(mediocre, productivity_params);
        productivityLayout.addView(productive, productivity_params);
        productivityLayout.addView(accomplished, productivity_params);
        productivityLayout.addView(confirm_productivity, productivity_params);
        ProductivityWindow.setContentView(productivityLayout);
    }

    public boolean addMood() {
        boolean success;
        if (isMoodRecordedForSelectedDate()) {
            Mood MoodToUpdate = new Mood(dateInt, selected_date, entered_mood, entered_energy,
                    entered_social_meter, entered_productivity);
            success = DBhandler.updateHandler(MoodToUpdate);
        }
        else {
            Mood MoodToAdd = new Mood(dateInt, selected_date, entered_mood,
                    entered_energy, entered_social_meter, entered_productivity);
            DBhandler.addHandler(MoodToAdd);
            recordMood.setText(R.string.changeMood);
            success = true;
        }
        return success;
    }

    @SuppressLint("SetTextI18n")
    public void getMood() {
        Mood mood = DBhandler.findHandler(selected_date);

        savedEntry.setVisibility(View.VISIBLE);
        recorded_mood.setText("Mood: " + mood.getMood());
        recorded_mood.setVisibility(View.VISIBLE);
        recorded_energy.setText("Energy: " + mood.getEnergy());
        recorded_energy.setVisibility(View.VISIBLE);
        recorded_socialMeter.setText("Social Meter: " + mood.getSocial_meter());
        recorded_socialMeter.setVisibility(View.VISIBLE);
        recorded_productivity.setText("Productivity: " + mood.getProductivity());
        recorded_productivity.setVisibility(View.VISIBLE);
    }

    public boolean isMoodRecordedForSelectedDate() {
        Mood mood = DBhandler.findHandler(selected_date);
        return mood != null;
    }

    // When the app stops, close the DB connection and dismiss any open pop-up windows
    @Override
    protected void onStop() {
        super.onStop();
        DBhandler.closeConnection();
        MoodWindow.dismiss();
        EnergyWindow.dismiss();
        SocialMeterWindow.dismiss();
        ProductivityWindow.dismiss();
    }
}
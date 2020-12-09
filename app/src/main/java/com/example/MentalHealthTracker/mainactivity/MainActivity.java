package com.example.MentalHealthTracker.mainactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.MentalHealthTracker.FavouriteResources.Favourite;
import com.example.MentalHealthTracker.FavouriteResources.FavouriteDBHandler;
import com.example.MentalHealthTracker.FavouriteResources.ResourceToString;
import com.example.MentalHealthTracker.FavouriteResources.Resources;
import com.example.MentalHealthTracker.moreresources.MoreResources;
import com.example.MentalHealthTracker.R;
import com.example.MentalHealthTracker.Statistics.ViewStatistics;
import com.example.MentalHealthTracker.WebSupportArticles.WebSupportArticles;
import com.example.MentalHealthTracker.Statistics.Statistics;
import com.example.MentalHealthTracker.Statistics.StatisticsDBHandler;
import com.example.MentalHealthTracker.breathingexercises.BreathingExercises;
import com.example.MentalHealthTracker.connectwithcounsellors.ConnectWithCounsellors;
import com.example.MentalHealthTracker.exercises.Exercises;
import com.example.MentalHealthTracker.meditation.Meditation;
import com.example.MentalHealthTracker.mindfulness.Mindfulness;
import com.example.MentalHealthTracker.mood_tracker.MoodTracker;
import com.example.MentalHealthTracker.music.PlayMusic;
import com.example.MentalHealthTracker.yoga.Yoga;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    // attributes
    public static final String ACTIVITY_REF = "MAIN_ACTIVITY";

    private TextView welcomeMsg;

    // Favourite resource buttons and database support
    private FavouriteDBHandler favouriteDBhandler;
    private PopupWindow editFavouritesWindow;
    private PopupWindow selectFavouriteWindow;
    private Favourite favourite1;
    private Favourite favourite2;
    private Favourite favourite3;
    private int favouriteToChange;
    private Resources selectedResource;

    // Resource statistics database handler and support
    private StatisticsDBHandler statisticsDBHandler;

    // methods
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize favourites db handler and favourite resources support
        favouriteDBhandler = new FavouriteDBHandler(this, null);
        editFavouritesWindow = new PopupWindow(this);
        LinearLayout editFavouriteLayout = new LinearLayout(this);
        selectFavouriteWindow = new PopupWindow(this);
        LinearLayout selectFavouriteLayout = new LinearLayout(this);
        favouriteToChange = 0;
        selectedResource = null;

        // Initialize statistics db handler and add any missing resources to database
        statisticsDBHandler = StatisticsDBHandler.getInstance(this);
        initializeStatisticsDatabase();

        // set welcome message based on time of day
        welcomeMsg = findViewById(R.id.welcomeMsg);
        setWelcomeMsg();

        // Button that links to 'Do Yoga' component
        Button doYogaButton = findViewById(R.id.yogaButton);
        doYogaButton.setOnClickListener(v -> openYogaActivity());

        // Button that links to 'Play Music' component
        Button playMusicButton = findViewById(R.id.playMusicButton);
        playMusicButton.setOnClickListener(v -> openPlayMusicActivity());

        // Button that links to 'Mood Tracker' component
        Button moodTrackerButton = findViewById(R.id.moodTrackerButton);
        moodTrackerButton.setOnClickListener(v -> openMoodTracker());

        // Button that links to 'Breathing Exercises' component
        Button breathingExButton = findViewById(R.id.breathingExButton);
        breathingExButton.setOnClickListener(v -> openBreathingExercises());

        // Button that links to 'Mindfulness' component
        Button mindfulnessButton = findViewById(R.id.mindfulnessButton);
        mindfulnessButton.setOnClickListener(v -> openMindfulness());

        // Button that links to 'Meditation' component
        Button exercisesButton = findViewById(R.id.exercisesButton);
        exercisesButton.setOnClickListener(v -> openExercises());

        // Button that links to 'More Resources' component
        Button moreResourcesButton = findViewById(R.id.moreResourcesButton);
        moreResourcesButton.setOnClickListener(v -> openMoreResources());

        // Buttons that link to favourite resources
        Button favourite1Button = findViewById(R.id.favourite1Button);
        Button favourite2Button = findViewById(R.id.favourite2Button);
        Button favourite3Button = findViewById(R.id.favourite3Button);

        favourite1Button.setOnClickListener(v -> OpenFavourite(1));
        favourite2Button.setOnClickListener(v -> OpenFavourite(2));
        favourite3Button.setOnClickListener(v -> OpenFavourite(3));

        favourite1 = favouriteDBhandler.findHandler(1);
        favourite2 = favouriteDBhandler.findHandler(2);
        favourite3 = favouriteDBhandler.findHandler(3);

        // get pre-set fav resource or set to default -> set text for button
        if (favourite1 != null) {
            favourite1Button.setText(ResourceToString.getResourceNameFromID(favourite1.getResource()));
        }
        else { favourite1Button.setText(R.string.playmusic); }

        if (favourite2 != null) {
            favourite2Button.setText(ResourceToString.getResourceNameFromID(favourite2.getResource()));
        }
        else { favourite2Button.setText(R.string.exercises); }

        if (favourite3 != null) {
            favourite3Button.setText(ResourceToString.getResourceNameFromID(favourite3.getResource()));
        }
        else { favourite3Button.setText(R.string.moodtracker); }

        // Button that links to 'Edit Favourites' pop-up selection
        Button editFavouritesButton = findViewById(R.id.editFavouritesButton);
        editFavouritesButton.setOnClickListener(v -> {
            editFavouritesWindow.setWidth(500);
            editFavouritesWindow.setHeight(600);
            editFavouritesWindow.showAtLocation(editFavouriteLayout, Gravity.CENTER, 0, 0);
        });

        LinearLayout.LayoutParams favourite_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        editFavouriteLayout.setOrientation(LinearLayout.VERTICAL);

        Button selectFavourite1 = new Button(this);
        selectFavourite1.setText(R.string.selectFavourite1);
        selectFavourite1.setOnClickListener( v -> {
            favouriteToChange = 1;
            selectFavouriteWindow.setWidth(500);
            selectFavouriteWindow.setHeight(1800);
            selectFavouriteWindow.showAtLocation(selectFavouriteLayout, Gravity.CENTER, 0, 0);
        });

        Button selectFavourite2 = new Button(this);
        selectFavourite2.setText(R.string.selectFavourite2);
        selectFavourite2.setOnClickListener( v -> {
            favouriteToChange = 2;
            selectFavouriteWindow.setWidth(500);
            selectFavouriteWindow.setHeight(1800);
            selectFavouriteWindow.showAtLocation(selectFavouriteLayout, Gravity.CENTER, 0, 0);
        });

        Button selectFavourite3 = new Button(this);
        selectFavourite3.setText(R.string.selectFavourite3);
        selectFavourite3.setOnClickListener( v -> {
            favouriteToChange = 3;
            selectFavouriteWindow.setWidth(500);
            selectFavouriteWindow.setHeight(1800);
            selectFavouriteWindow.showAtLocation(selectFavouriteLayout, Gravity.CENTER, 0, 0);
        });

        Button confirmFavourites = new Button(this);
        confirmFavourites.setText(R.string.confirmFavs);
        confirmFavourites.setOnClickListener( v -> editFavouritesWindow.dismiss());

        editFavouriteLayout.addView(selectFavourite1, favourite_params);
        editFavouriteLayout.addView(selectFavourite2, favourite_params);
        editFavouriteLayout.addView(selectFavourite3, favourite_params);
        editFavouriteLayout.addView(confirmFavourites, favourite_params);
        editFavouritesWindow.setContentView(editFavouriteLayout);

        // configure 'select favourite' pop-up
        LinearLayout.LayoutParams selectFavourite_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        selectFavouriteLayout.setOrientation(LinearLayout.VERTICAL);

        Button confirmFavouriteSelection = new Button(this);
        confirmFavouriteSelection.setText(R.string.confirmSelection);
        confirmFavouriteSelection.setVisibility(View.INVISIBLE);
        confirmFavouriteSelection.setOnClickListener( v -> {
            selectFavouriteWindow.dismiss();
            if (favouriteDBhandler.findHandler(favouriteToChange) != null) {
                boolean updateFavourite= favouriteDBhandler.updateHandler(favouriteToChange, selectedResource.toString());
                if (!updateFavourite) {
                    return;
                }
            }
            else {
                Favourite favouriteToAdd = new Favourite(favouriteToChange, selectedResource.toString());
                favouriteDBhandler.addHandler(favouriteToAdd);
            }

            if (favouriteToChange == 1) {
                favourite1Button.setText(ResourceToString.getResourceNameFromID(selectedResource.toString()));
                favourite1 = favouriteDBhandler.findHandler(1);
            }
            else if (favouriteToChange == 2) {
                favourite2Button.setText(ResourceToString.getResourceNameFromID(selectedResource.toString()));
                favourite2 = favouriteDBhandler.findHandler(2);
            }
            else if (favouriteToChange == 3) {
                favourite3Button.setText(ResourceToString.getResourceNameFromID(selectedResource.toString()));
                favourite3 = favouriteDBhandler.findHandler(3);
            }
            confirmFavouriteSelection.setVisibility(View.INVISIBLE);
        });

        Button selectYoga = new Button(this);
        selectYoga.setText(R.string.yoga);
        selectYoga.setOnClickListener(v -> {
            selectedResource = Resources.Yoga;
            confirmFavouriteSelection.setVisibility(View.VISIBLE);
        });

        Button selectPlayMusic = new Button(this);
        selectPlayMusic.setText(R.string.playmusic);
        selectPlayMusic.setOnClickListener(v -> {
            selectedResource = Resources.PlayMusic;
            confirmFavouriteSelection.setVisibility(View.VISIBLE);
        });

        Button selectMoodTracker = new Button(this);
        selectMoodTracker.setText(R.string.moodtracker);
        selectMoodTracker.setOnClickListener(v -> {
            selectedResource = Resources.MoodTracker;
            confirmFavouriteSelection.setVisibility(View.VISIBLE);
        });

        Button selectBreathingExercises = new Button(this);
        selectBreathingExercises.setText(R.string.breathingexercises);
        selectBreathingExercises.setOnClickListener(v -> {
            selectedResource = Resources.BreathingExercises;
            confirmFavouriteSelection.setVisibility(View.VISIBLE);
        });

        Button selectMindfulness= new Button(this);
        selectMindfulness.setText(R.string.mindfulness);
        selectMindfulness.setOnClickListener(v -> {
            selectedResource = Resources.Mindfulness;
            confirmFavouriteSelection.setVisibility(View.VISIBLE);
        });

        Button selectExercises = new Button(this);
        selectExercises.setText(R.string.exercises);
        selectExercises.setOnClickListener(v -> {
            selectedResource = Resources.Exercises;
            confirmFavouriteSelection.setVisibility(View.VISIBLE);
        });

        Button selectWebSupportArticles = new Button(this);
        selectWebSupportArticles.setText(R.string.websupportarticles);
        selectWebSupportArticles.setOnClickListener(v -> {
            selectedResource = Resources.WebSupportArticles;
            confirmFavouriteSelection.setVisibility(View.VISIBLE);
        });

        Button selectMeditation = new Button(this);
        selectMeditation.setText(R.string.meditation);
        selectMeditation.setOnClickListener(v -> {
            selectedResource = Resources.Meditation;
            confirmFavouriteSelection.setVisibility(View.VISIBLE);
        });

        Button selectConnectCounsellors = new Button(this);
        selectConnectCounsellors.setText(R.string.counsellors);
        selectConnectCounsellors.setOnClickListener(v -> {
            selectedResource = Resources.ConnectCounsellors;
            confirmFavouriteSelection.setVisibility(View.VISIBLE);
        });

        Button selectViewStatistics = new Button(this);
        selectViewStatistics.setText(R.string.statistics);
        selectViewStatistics.setOnClickListener(v -> {
            selectedResource = Resources.ViewStatistics;
            confirmFavouriteSelection.setVisibility(View.VISIBLE);
        });

        Button cancelSelection = new Button(this);
        cancelSelection.setText(R.string.cancelSelection);
        cancelSelection.setOnClickListener( v -> selectFavouriteWindow.dismiss());

        selectFavouriteLayout.addView(selectYoga, selectFavourite_params);
        selectFavouriteLayout.addView(selectPlayMusic, selectFavourite_params);
        selectFavouriteLayout.addView(selectMoodTracker, selectFavourite_params);
        selectFavouriteLayout.addView(selectBreathingExercises, selectFavourite_params);
        selectFavouriteLayout.addView(selectMindfulness, selectFavourite_params);
        selectFavouriteLayout.addView(selectExercises, selectFavourite_params);
        selectFavouriteLayout.addView(selectWebSupportArticles, selectFavourite_params);
        selectFavouriteLayout.addView(selectMeditation, selectFavourite_params);
        selectFavouriteLayout.addView(selectConnectCounsellors, selectFavourite_params);
        selectFavouriteLayout.addView(selectViewStatistics, selectFavourite_params);
        selectFavouriteLayout.addView(cancelSelection, selectFavourite_params);
        selectFavouriteLayout.addView(confirmFavouriteSelection, selectFavourite_params);
        selectFavouriteWindow.setContentView(selectFavouriteLayout);
    }

    public void initializeStatisticsDatabase() {
        statisticsDBHandler.addHandler(new Statistics(R.string.yoga, 0));
        statisticsDBHandler.addHandler(new Statistics(R.string.playmusic, 0));
        statisticsDBHandler.addHandler(new Statistics(R.string.moodtracker, 0));
        statisticsDBHandler.addHandler(new Statistics(R.string.breathingexercises, 0));
        statisticsDBHandler.addHandler(new Statistics(R.string.mindfulness, 0));
        statisticsDBHandler.addHandler(new Statistics(R.string.exercises, 0));
        statisticsDBHandler.addHandler(new Statistics(R.string.counsellors, 0));
        statisticsDBHandler.addHandler(new Statistics(R.string.meditation, 0));
        statisticsDBHandler.addHandler(new Statistics(R.string.websupportarticles, 0));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setWelcomeMsg() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        LocalDateTime currentTime = LocalDateTime.now();
        String hourofDay = currentTime.format(formatter);
        int hour = Integer.parseInt(hourofDay);
        if (hour >= 4 && hour < 12 ) {
            welcomeMsg.setText(R.string.welcomeMessage_1);
        }
        else if (hour >= 12 && hour < 18) {
            welcomeMsg.setText(R.string.welcomeMessage_2);
        }
        else {
            welcomeMsg.setText(R.string.welcomeMessage_3);
        }
    }

    public void openYogaActivity() {
        statisticsDBHandler.updateHandler(R.string.yoga);
        Intent intent = new Intent(this, Yoga.class);
        startActivity(intent);
    }

    public void openPlayMusicActivity() {
        statisticsDBHandler.updateHandler(R.string.playmusic);
        Intent intent = new Intent(this, PlayMusic.class);
        startActivity(intent);
    }

    public void openMoodTracker() {
        statisticsDBHandler.updateHandler(R.string.moodtracker);
        Intent intent = new Intent(this, MoodTracker.class);
        startActivity(intent);
    }

    public void openBreathingExercises() {
        statisticsDBHandler.updateHandler(R.string.breathingexercises);
        Intent intent = new Intent(this, BreathingExercises.class);
        startActivity(intent);
    }

    public void openMindfulness() {
        statisticsDBHandler.updateHandler(R.string.mindfulness);
        Intent intent = new Intent(this, Mindfulness.class);
        startActivity(intent);
    }

    public void openExercises() {
        statisticsDBHandler.updateHandler(R.string.exercises);
        Intent intent = new Intent(this, Exercises.class);
        startActivity(intent);
    }

    public void openMoreResources() {
        Intent intent = new Intent(this, MoreResources.class);
        startActivity(intent);
    }

    public void OpenFavourite(int favouriteNum) {
        switch(favouriteNum) {
            case 1:
                Intent intent;
                if (favourite1 == null) { // default to Play Music
                    statisticsDBHandler.updateHandler(R.string.playmusic);
                    intent = new Intent(this, PlayMusic.class);
                }
                else {
                    statisticsDBHandler.updateHandler(ResourceToString.getResourceNameFromID(favourite1.getResource()));
                    Class<?> fav1 = StringtoActivity(favourite1.getResource());
                    intent = new Intent(this, fav1);
                }
                startActivity(intent);
                break;
            case 2:
                Intent intent2;
                if (favourite2 == null) {
                    statisticsDBHandler.updateHandler(R.string.exercises);
                    intent2 = new Intent(this, Exercises.class);
                }
                else {
                    statisticsDBHandler.updateHandler(ResourceToString.getResourceNameFromID(favourite2.getResource()));
                    Class<?> fav2 = StringtoActivity(favourite2.getResource());
                    intent2 = new Intent(this, fav2);
                }
                startActivity(intent2);
                break;
            case 3:
                Intent intent3;
                if (favourite3 == null) {
                    statisticsDBHandler.updateHandler(R.string.moodtracker);
                    intent3 = new Intent(this, MoodTracker.class);
                }
                else {
                    statisticsDBHandler.updateHandler(ResourceToString.getResourceNameFromID(favourite3.getResource()));
                    Class<?> fav3 = StringtoActivity(favourite3.getResource());
                    intent3 = new Intent(this, fav3);
                }
                startActivity(intent3);
                break;
            default:
        }
    }

    public Class<?> StringtoActivity(String resource) {
        if (resource.equals(Resources.Yoga.toString())) {
            return Yoga.class;
        }
        else if (resource.equals(Resources.PlayMusic.toString())) {
            return PlayMusic.class;
        }
        else if (resource.equals(Resources.MoodTracker.toString())) {
            return MoodTracker.class;
        }
        else if (resource.equals(Resources.BreathingExercises.toString())) {
            return BreathingExercises.class;
        }
        else if (resource.equals(Resources.Mindfulness.toString())) {
            return Mindfulness.class;
        }
        else if (resource.equals(Resources.Exercises.toString())) {
            return Exercises.class;
        }
        else if (resource.equals(Resources.WebSupportArticles.toString())) {
            return WebSupportArticles.class;
        }
        else if (resource.equals(Resources.Meditation.toString())) {
            return Meditation.class;
        }
        else if (resource.equals(Resources.ConnectCounsellors.toString())) {
            return ConnectWithCounsellors.class;
        }
        else if (resource.equals(Resources.ViewStatistics.toString())) {
            return ViewStatistics.class;
        }
        else {
            return null;
        }
    }

    // When the app stops, close the DB connection and dismiss any open pop-up windows
    @Override
    protected void onStop() {
        super.onStop();
        favouriteDBhandler.closeConnection();
        editFavouritesWindow.dismiss();
        selectFavouriteWindow.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
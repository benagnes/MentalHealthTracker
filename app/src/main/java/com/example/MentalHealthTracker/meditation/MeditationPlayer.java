package com.example.MentalHealthTracker.meditation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.MentalHealthTracker.R;
import com.example.MentalHealthTracker.meditation.db.MeditationStep;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;
import java.util.Objects;

public class MeditationPlayer extends AppCompatActivity {
    private static final String TAG = "MeditationActivity";
    private static final String appBarTitle = "Meditation Routine";
    public static final String ROUTINE_REF = "ROUTINE_REF";
    private static final int timerUpdateDuration = 1000;

    private CountDownTimer timer;
    private MeditationPlayerViewModel viewModel;

    private TextView mTimer;
    private TextView mTitle;
    private TextView mDescription;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_player);

        ActionBar mainActionBar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        int meditationRoutineId;

        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // No extras passed, we need to handle this improper usage of this activity
        if (extras == null) {
            Log.e(TAG, "No extras passed to this activity, however some were expected");
            cleanUpAndClose();
            return;
        }

        try {
            String routineIdValue = extras.getString(ROUTINE_REF);
            meditationRoutineId = Integer.parseInt(routineIdValue);
        } catch (NumberFormatException eNumberFormat) {
            Log.e(TAG, "Failed parsing meditation routine id, received: " +
                    extras.getString(ROUTINE_REF));
            cleanUpAndClose();
            return;
        } catch (NullPointerException eNullPtr) {
            Log.e(TAG, "No value received for the meditation routine id");
            cleanUpAndClose();
            return;
        }

        // Assign UI components
        mTimer = findViewById(R.id.meditationStepTimer);
        mTitle = findViewById(R.id.meditationStepTitle);
        mDescription = findViewById(R.id.meditationStepDescription);
        mImage = findViewById(R.id.meditationStepImage);
        FloatingActionButton skipButton = findViewById(R.id.meditationSkipButton);
        skipButton.setOnClickListener((l) -> {
            stopStepTimer();
            moveToStep(viewModel.getCurrentStep() + 1, true);
        });

        // Setup the actual UI with steps
        viewModel = new ViewModelProvider(this).get(MeditationPlayerViewModel.class);
        viewModel.setRoutineId(meditationRoutineId);
        viewModel.loadSteps();
        viewModel.getSteps().observe(this, steps -> {
            if (viewModel.getState() == MeditationPlayerState.NOT_STARTED) {
                viewModel.setCurrentStep(0);
                moveToStep(0, true);
                viewModel.setState(MeditationPlayerState.STARTED);
            } else {
                moveToStep(viewModel.getCurrentStep(), false);
            }
        });
    }

    // Initializes the UI on the screen for this particular step based on the data
    // in the view model.
    @SuppressLint("SetTextI18n")
    private void initializeStepWithViewModel() {
        MeditationStep currentStep = Objects.requireNonNull(viewModel.getSteps().getValue()).get(
                viewModel.getCurrentStep());

        if (currentStep == null) {
            Log.e(TAG, "Meditation step with id " + viewModel.getCurrentStep() + " was NULL");
            return;
        }

        mTitle.setText(currentStep.getTitle());
        mDescription.setText(currentStep.getDescription());
        mImage.setImageResource(currentStep.getImage());

        updateTimer();
    }

    // Move to a step, if the step is not within the bounds of the steps list we complete the
    // activity.
    private void moveToStep(int step, boolean updateTimeLeft) {
        int maxSteps = Objects.requireNonNull(viewModel.getSteps().getValue()).size();

        if (step < 0 || step >= maxSteps) {
            cleanUpAndClose();
            return;
        }

        MeditationStep currentStep = Objects.requireNonNull(viewModel.getSteps().getValue())
                .get(step);
        viewModel.setCurrentStep(step);

        if (updateTimeLeft)
            viewModel.setCurrentTime(currentStep.getTime());
        else
            viewModel.setCurrentTime(viewModel.getCurrentTime());

        initializeStepWithViewModel();
        startStepTimer();
    }

    // Start the timer count down for this step
    private void startStepTimer() {
        timer = new CountDownTimer(viewModel.getCurrentTime(), timerUpdateDuration) {
            @Override
            public void onTick(long millisUntilFinished) {
                viewModel.setCurrentTime(millisUntilFinished);
                updateTimer();
            }

            @Override
            public void onFinish() {
                moveToStep(viewModel.getCurrentStep() + 1, true);
            }
        }.start();
    }

    // Update the text on the timer
    private void updateTimer() {
        int minutes = (int) (viewModel.getCurrentTime() / 60000);
        int seconds = (int) viewModel.getCurrentTime() % 60000 / 1000;
        mTimer.setText(String.format(Locale.getDefault(),"%dm %02ds", minutes, seconds));
    }

    // Stop the count down timer for this step
    private void stopStepTimer() {
        timer.cancel();
    }

    // Return to the parent activity
    private void cleanUpAndClose() {
        finish();
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "State: " + viewModel.getState().toString());
        if (viewModel.getState() == MeditationPlayerState.PAUSED) {
            moveToStep(viewModel.getCurrentStep(), false);
            viewModel.setState(MeditationPlayerState.STARTED);
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        viewModel.setState(MeditationPlayerState.PAUSED);
        stopStepTimer();
        super.onStop();
    }
}
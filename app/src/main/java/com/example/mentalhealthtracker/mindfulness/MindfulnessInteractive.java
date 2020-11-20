package com.example.mentalhealthtracker.mindfulness;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.example.mentalhealthtracker.R;
import com.example.mentalhealthtracker.mindfulness.db.MindfulnessStep;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;
import java.util.Objects;

// This activity will only render and work properly when you pass the ACTIVITY_REF
// key to this activity in your intent.
public class MindfulnessInteractive extends AppCompatActivity {
    private MindfulnessStepViewModel viewModel;
    private CountDownTimer timer;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mNumber;
    private TextView mTimer;

    private static final String TAG = "MindfulnessActivity";
    public static final String ACTIVITY_REF = "MINDFULNESS_ACTIVITY";
    private static final int timerUpdateDuration = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfulness_interactive);

        ActionBar actionBar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        int mindfulnessActivityId;

        // No extras passed, we need to handle this improper usage of this activity
        if (extras == null) {
            Log.e(TAG, "No extras passed to this activity, however some were expected");
            cleanUpAndClose();
            return;
        }

        try {
            String activityIdValue = extras.getString(ACTIVITY_REF);
            mindfulnessActivityId = Integer.parseInt(activityIdValue);
        } catch (NumberFormatException eNumberFormat) {
            Log.e(TAG, "Failed parsing mindfulness activity id, received: " +
                    extras.getString(ACTIVITY_REF));
            cleanUpAndClose();
            return;
        } catch (NullPointerException eNullPtr) {
            Log.e(TAG, "No value received for the mindfulness activity id");
            cleanUpAndClose();
            return;
        }

        if (actionBar != null) {
            actionBar.setTitle("Mindfulness Activity");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
        }

        // References to UI components
        mTitle = findViewById(R.id.mStepTitle);
        mDescription = findViewById(R.id.mStepDescription);
        mTimer = findViewById(R.id.mStepTimerText);
        mNumber = findViewById(R.id.mStepNumber);
        FloatingActionButton mSkipNextButton = findViewById(R.id.mStepSkipButton);
        mSkipNextButton.setOnClickListener((l) -> {
            stopStepTimer();
            moveToStep(viewModel.getCurrentStep() + 1, true);
        });

        // Setup the actual UI with steps
        viewModel = new ViewModelProvider(this).get(MindfulnessStepViewModel.class);
        viewModel.setStepsForActivity(mindfulnessActivityId);
        viewModel.getSteps().observe(this, steps -> {
            if (viewModel.getState() == MindfulnessStepViewModel
                    .InteractiveActivityState.NOT_STARTED) {
                viewModel.setCurrentStep(0);
                moveToStep(0, true);
                viewModel.setState(MindfulnessStepViewModel.InteractiveActivityState.STARTED);
            } else {
                moveToStep(viewModel.getCurrentStep(), false);
            }
        });
    }

    // Initializes the UI on the screen for this particular step based on the data
    // in the view model.
    @SuppressLint("SetTextI18n")
    private void initializeStepWithViewModel() {
        MindfulnessStep currentStep = Objects.requireNonNull(viewModel.getSteps().getValue()).get(
                viewModel.getCurrentStep());

        if (currentStep == null) {
            Log.e(TAG, "Mindfulness step with id " + viewModel.getCurrentStep() + " was NULL");
            return;
        }

        mTitle.setText(currentStep.getTitle());
        mDescription.setText(currentStep.getDescription());
        mNumber.setText(Integer.toString(currentStep.getNumber()));

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

        MindfulnessStep currentStep = Objects.requireNonNull(viewModel.getSteps().getValue()).get(
                step);
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopStepTimer();
    }
}
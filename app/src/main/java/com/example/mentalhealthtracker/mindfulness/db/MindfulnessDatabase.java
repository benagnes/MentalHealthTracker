package com.example.mentalhealthtracker.mindfulness.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = { MindfulnessActivity.class, MindfulnessStep.class }, version = 1, exportSchema = false)
public abstract class MindfulnessDatabase extends RoomDatabase {
    private static MindfulnessDatabase instance;
    public abstract MindfulnessActivityDao activityDao();
    public abstract MindfulnessStepDao stepDao();

    public static synchronized MindfulnessDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                     MindfulnessDatabase.class,
                    "mindfulness_db"
            ).fallbackToDestructiveMigration()
                    .addCallback(initCallback)
                    .build();
        }

        return instance;
    }

    private static final RoomDatabase.Callback initCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                instance.activityDao().insertAll(
                        new MindfulnessActivity(
                                0,
                                "Walking Meditation",
                                "A form of meditation useful for concentration and awareness"
                        ),
                        new MindfulnessActivity(
                                1,
                                "Past, Present, Future",
                                "Take a big picture view of the past, present, and future. Stay rooted in the present moment, and avoid thinking about the past or future too much"
                        )
                );

                instance.stepDao().insertAll(
                        // Walking Meditation
                        new MindfulnessStep(0,
                                1,
                                30 * 1000,
                                "Getting Started",
                                "Find an open,quiet space in which you can move around comfortably without distractions (inside or outside)"
                        ),
                        new MindfulnessStep(0,
                                2,
                                20 * 1000,
                                "Find A Path",
                                "Choose to follow a particular path around your environment"
                        ),
                        new MindfulnessStep(0,
                                3,
                                45 * 1000,
                                "Relax",
                                "Close your eyes and spend a few moments standing. Notice the connection between you and the earth."
                        ),
                        new MindfulnessStep(0,
                                4,
                                10 * 1000,
                                "Open Your Eyes",
                                "Open your eyes, with your gaze soft and aimed at the ground ahead of you. Very slowly, begin to walk forward"
                        ),
                        new MindfulnessStep(0,
                                5,
                                10 * 1000,
                                "Open Your Eyes",
                                "Open your eyes, with your gaze soft and aimed at the ground ahead of you. Very slowly, begin to walk forward. Maintain awareness for each part of the process of walking."
                        ),
                        new MindfulnessStep(0,
                                6,
                                5 * 1000,
                                "Completed!",
                                "Good job on completing this, hopefully you feel relaxed!"
                        ),
                        // Past Present Future
                        new MindfulnessStep(1,
                                1,
                                20 * 1000,
                                "Getting Started",
                                "Take a comfortable seat in a quite place."
                        ),
                        new MindfulnessStep(1,
                                2,
                                60 * 1000,
                                "Relax",
                                "Take a comfortable seat in a quite place. Close your eyes and breath deeply. Stay present and pay attention to your breath. Take note any time your mind wonders of."
                        ),
                        new MindfulnessStep(1,
                                3,
                                60 * 1000,
                                "Take Notices Of Your Thoughts",
                                "Anytime you notice your mind lingering, say the word future, and return to breath awareness. Come back as quickly as you can. Repeat as often as you need to."
                        ),
                        new MindfulnessStep(1,
                                4,
                                5 * 1000,
                                "Completed!",
                                "Good job on completing this, hopefully you feel relaxed!"
                        )
                );
            });
        }
    };
}

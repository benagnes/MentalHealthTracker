package com.example.mentalhealthtracker.meditation.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mentalhealthtracker.R;

import java.util.concurrent.Executors;

@Database(entities = {MeditationRoutine.class, MeditationStep.class}, version = 1, exportSchema = false)
public abstract class MeditationDatabase extends RoomDatabase {
    private static MeditationDatabase instance;
    public abstract MeditationRoutineDao routineDao();
    public abstract  MeditationStepDao stepDao();

    public static synchronized MeditationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MeditationDatabase.class,
                    "meditation_db"
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
                instance.routineDao().insertAll(
                        new MeditationRoutine(
                                0,
                                "Beginner Meditation Routine",
                                "This is a very basic meditation routine, perfect for beginners."
                        )
                );

                instance.stepDao().insertAll(
                        new MeditationStep(
                                0,
                                0,
                                20 * 1000,
                                "Get Started",
                                "Start by sitting down cross-legged, or in an upright chair position. Whichever is most comfortable to you.",
                                R.mipmap.meditationpose
                        ),
                        new MeditationStep(
                                0,
                                1,
                                20 * 1000,
                                "The Right Posture",
                                "Make sure your back is straight, but keep it relaxed. Align your head and neck with your spine. Put your hands either on your legs (palms down) or on your lap.",
                                R.mipmap.meditationpose
                        ),
                        new MeditationStep(
                                0,
                                2,
                                400 * 1000,
                                "Meditate",
                                "Close your eyes and concentrate on your breathing. Take a big initial deep breath, and try to not let anything disturb you for the next little while.",
                                R.mipmap.meditationpose
                        ),
                        new MeditationStep(
                                0,
                                2,
                                10 * 1000,
                                "Finished",
                                "Your meditation exercise is finished, nice job!",
                                R.mipmap.meditationpose
                        )
                );
            });
        }
    };
}

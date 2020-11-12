package com.example.mentalhealthtracker.music.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mentalhealthtracker.R;

import java.util.concurrent.Executors;

@Database(entities = { Song.class }, version = 1, exportSchema = false)
public abstract class SongDatabase extends RoomDatabase {
    private static SongDatabase instance;

    public abstract SongDao songDao();

    public static synchronized SongDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    SongDatabase.class, "songs_db"
            ).fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                instance.songDao().insertAll(
                        new Song("Levity", "v e n n",
                                "https://i.scdn.co/image/ab67616d000048515f5e2c82b60159cfc694d5d1"
                                , R.raw.venn_levity),
                        new Song("Serenity", "Man from Mars",
                                "https://i.scdn.co/image/ab67616d00004851ec3d4294af8f3a6db05240b3",
                                R.raw.man_from_mars_serenity),
                        new Song("Lost Thoughts", "First Light Circus",
                                "https://i.scdn.co/image/ab67616d000048515c955d94bfda3da888865e28",
                                R.raw.first_light_circus_lost_thought)
                );
            });
        }
    };
}

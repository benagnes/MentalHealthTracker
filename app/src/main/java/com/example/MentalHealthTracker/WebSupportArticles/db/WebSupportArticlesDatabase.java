package com.example.MentalHealthTracker.WebSupportArticles.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = { WebSupportCategory.class, WebSupportArticle.class }, version = 1,
        exportSchema = false)
public abstract class WebSupportArticlesDatabase extends RoomDatabase {
    private static WebSupportArticlesDatabase instance;
    public abstract WebSupportCategoryDao categoryDao();
    public abstract WebSupportArticleDao articleDao();

    public static synchronized WebSupportArticlesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    WebSupportArticlesDatabase.class,
                    "web_support_db"
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
                // Stress, Anxiety, Insomnia, Depression, Anger
                instance.categoryDao().insertAll(
                        new WebSupportCategory(
                                0,
                                "Stress",
                                "For stressful circumstances"
                        ),
                        new WebSupportCategory(
                                1,
                                "Anxiety",
                                "For feelings of anxiety"
                        ),
                        new WebSupportCategory(
                                2,
                                "Insomnia",
                                "For difficulty sleeping"
                        ),
                        new WebSupportCategory(
                                3,
                                "Depression",
                                "Resources on depression"
                        ),
                        new WebSupportCategory(
                                4,
                                "Anger",
                                "For feelings of anger"
                        )
                );

                // The articles
                instance.articleDao().insertAll(
                        new WebSupportArticle(
                                0,
                                "Sample Article 0",
                                "Some sample article content"
                        ),
                        new WebSupportArticle(
                                0,
                                "Sample Article 1",
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis risus at nisi varius finibus eget quis metus. Cras accumsan augue turpis, nec faucibus eros mattis sit amet. Integer cursus dui et diam bibendum lobortis sit amet et nibh. Mauris aliquam orci nibh, eu iaculis ipsum facilisis sit amet. Nullam quis maximus lacus. Integer aliquam congue nunc, id malesuada leo cursus non. Vestibulum ultrices arcu vel quam posuere bibendum. Maecenas sodales ornare ligula eget suscipit. Sed ullamcorper justo ac felis iaculis, sit amet gravida lectus sodales."
                        )
                );
            });
        }
    };
}

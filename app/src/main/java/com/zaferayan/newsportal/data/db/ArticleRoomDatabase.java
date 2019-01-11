package com.zaferayan.newsportal.data.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.zaferayan.newsportal.data.dao.ArticleDao;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class ArticleRoomDatabase extends RoomDatabase {
    private static volatile ArticleRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDBCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    public static ArticleRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArticleRoomDatabase.class, "article_database")
                            .addCallback(sRoomDBCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ArticleDao articleDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ArticleDao mDao;

        PopulateDbAsync(ArticleRoomDatabase db) {
            mDao = db.articleDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
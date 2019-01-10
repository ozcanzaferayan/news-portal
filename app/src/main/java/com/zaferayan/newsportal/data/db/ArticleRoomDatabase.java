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
import com.zaferayan.newsportal.ui.topHeadlines.model.Source;

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
            Source source = new Source("al-jazeera-english", "Al Jazeera English");
            mDao.insert(new Article(
                    source,
                    "Trump to make case for wall in TV address; Democrats to respond",
                    "http://www.aljazeera.com/news/2019/01/trump-case-wall-tv-address-democrats-respond-190108211603353.html",
                    "https://www.aljazeera.com/mritems/Images/2019/1/8/4533f07da2944d6b9d2ec321f094f4a9_18.jpg",
                    "2019-01-08T22:42:00Z"
            ));
            mDao.insert(new Article(
                    source,
                    "Is the US facing an immigration crisis?",
                    "http://www.aljazeera.com/programmes/insidestory/2019/01/facing-immigration-crisis-190109180923590.html",
                    null,
                    "2019-01-09T21:22:05.450547Z"
            ));
            //http://www.aljazeera.com/programmes/insidestory/2019/01/facing-immigration-crisis-190109180923590.html
            //http://www.aljazeera.com/programmes/insidestory/2019/01/facing-immigration-crisis-190109180923590.html
            return null;
        }
    }

}
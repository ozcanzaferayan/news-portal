package com.zaferayan.newsportal.data.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.zaferayan.newsportal.data.dao.ArticleDao;
import com.zaferayan.newsportal.data.db.ArticleRoomDatabase;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;

import java.util.List;

public class ArticleRepository {

    private ArticleDao mArticleDao;
    private LiveData<List<Article>> mAllArticles;
    private List<Article> mAllArticlesSync;

    public ArticleRepository(Application application) {
        ArticleRoomDatabase db = ArticleRoomDatabase.getDatabase(application);
        mArticleDao = db.articleDao();
        mAllArticles = mArticleDao.getAllArticlesAsync();
        mAllArticlesSync = mArticleDao.getAllArticles();
    }

    public LiveData<List<Article>> getAllArticlesAsync() {
        return mAllArticles;
    }

    public List<Article> getmAllArticlesSync() {
        return mAllArticlesSync;
    }


    public void insert(Article Article) {
        new insertAsyncTask(mArticleDao).execute(Article);
    }

    public void delete(Article Article) {
        new deleteAsyncTask(mArticleDao).execute(Article);
    }

    private static class insertAsyncTask extends AsyncTask<Article, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        insertAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Article, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        deleteAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
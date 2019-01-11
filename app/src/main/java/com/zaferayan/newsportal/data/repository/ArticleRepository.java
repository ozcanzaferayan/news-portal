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


    public void insert(Article article) {
        new insertAsyncTask(mArticleDao).execute(article);
    }


    public void insertAll(List<Article> articles) {
        new insertAllAsyncTask(mArticleDao).execute(articles);
    }

    public void delete(Article article) {
        new deleteAsyncTask(mArticleDao).execute(article);
    }


    public void addToSaved(Article article) {
        new updateAsyncTask(mArticleDao).execute(new UpdateWillSaveArticleParams(article, true));
    }


    public void removeFromSaved(Article article) {
        new updateAsyncTask(mArticleDao).execute(new UpdateWillSaveArticleParams(article, false));
    }

    public LiveData<List<Article>> getArticlesBySource(String sourceId) {
        return mArticleDao.getAllArticlesBySource(sourceId);
    }

    public LiveData<List<Article>> getSavedArticles(String sourceId) {
        return mArticleDao.getSavedArticles(sourceId);
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

    private static class UpdateWillSaveArticleParams {
        Article article;
        boolean willSaving;

        UpdateWillSaveArticleParams(Article article, boolean willSaving) {
            this.article = article;
            this.willSaving = willSaving;
        }
    }

    private static class updateAsyncTask extends AsyncTask<UpdateWillSaveArticleParams, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        updateAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(UpdateWillSaveArticleParams... params) {
            mAsyncTaskDao.update(params[0].article.getUrl(), params[0].willSaving);
            return null;
        }
    }

    private static class insertAllAsyncTask extends AsyncTask<List<Article>, Void, Void> {
        private ArticleDao mAsyncTaskDao;

        insertAllAsyncTask(ArticleDao mArticleDao) {
            mAsyncTaskDao = mArticleDao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Article>... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}
package com.zaferayan.newsportal.ui.savedArticles.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.view.View;
import com.zaferayan.newsportal.data.repository.ArticleRepository;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;
import com.zaferayan.newsportal.ui.webview.WebViewActivity;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {

    private ArticleRepository mRepository;

    private LiveData<List<Article>> mAllArticles;

    public ArticleViewModel(Application application) {
        super(application);
        mRepository = new ArticleRepository(application);
        mAllArticles = mRepository.getAllArticlesAsync();
    }

    public LiveData<List<Article>> getAllArticles() {
        return mAllArticles;
    }


    public LiveData<List<Article>> getArticlesBySource(String sourceId) {
        return mRepository.getArticlesBySource(sourceId);
    }

    public void insert(Article Article) {
        mRepository.insert(Article);
    }

    public void delete(Article Article) {
        mRepository.delete(Article);
    }

    public View.OnClickListener saveArticle(final Article article, final boolean isSaved) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSaved)
                    mRepository.removeFromSaved(article);
                else
                    mRepository.addToSaved(article);
            }
        };
    }

    public void navigateToWebView(Article article) {

        Intent intent = new Intent(getApplication(), WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_URL, article.getUrl());
        intent.putExtra(WebViewActivity.EXTRA_TITLE, article.getTitle());
        getApplication().startActivity(intent);
    }

    public LiveData<List<Article>> getSavedArticles(String sourceId) {
        return mAllArticles = mRepository.getSavedArticles(sourceId);
    }
}
package com.zaferayan.newsportal.ui.topHeadlines.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.zaferayan.newsportal.data.repository.ArticleRepository;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {

    private ArticleRepository mRepository;

    private LiveData<List<Article>> mAllArticles;

    public ArticleViewModel(Application application) {
        super(application);
        mRepository = new ArticleRepository(application);
        mAllArticles = mRepository.getAllArticles();
    }

    public LiveData<List<Article>> getAllArticles() {
        return mAllArticles;
    }

    public void insert(Article Article) {
        mRepository.insert(Article);
    }

    public void delete(Article Article) {
        mRepository.delete(Article);
    }
}
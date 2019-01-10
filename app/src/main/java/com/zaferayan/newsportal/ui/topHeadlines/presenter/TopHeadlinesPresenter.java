package com.zaferayan.newsportal.ui.topHeadlines.presenter;

import android.support.annotation.NonNull;
import android.view.View;
import com.zaferayan.newsportal.data.repository.ArticleRepository;
import com.zaferayan.newsportal.di.DependencyInjector;
import com.zaferayan.newsportal.ui.topHeadlines.contract.TopHeadlinesContract;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;
import com.zaferayan.newsportal.ui.topHeadlines.model.TopHeadlinesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class TopHeadlinesPresenter extends TopHeadlinesContract.Presenter {
    private final DependencyInjector dependencyInjector;
    private final TopHeadlinesPresenter self;
    private final String sourceId;
    private TopHeadlinesContract.View view;

    public TopHeadlinesPresenter(TopHeadlinesContract.View view, DependencyInjector dependencyInjector, String sourceId) {
        this.view = view;
        this.dependencyInjector = dependencyInjector;
        this.self = this;
        this.sourceId = sourceId;
    }

    @Override
    protected void loadList() {
        final Call<TopHeadlinesResponse> responseCall = dependencyInjector.getNewsService().getTopHeadlines(sourceId);
        final List<Article> storedArticles = dependencyInjector.getArticleRepository().getmAllArticlesSync();
        responseCall.enqueue(new Callback<TopHeadlinesResponse>() {
            @Override
            public void onResponse(@NonNull Call<TopHeadlinesResponse> call, @NonNull Response<TopHeadlinesResponse> response) {
                view.hideListLoading();
                if (response.code() != 200) {
                    String messageText = response.code() == 504
                            ? "Network not available"
                            : response.message();
                    view.showListError(new Exception(messageText));
                    return;
                }
                TopHeadlinesResponse sourcesResponse = response.body();
                if (sourcesResponse == null) return;
                List<Article> articles = sourcesResponse.getArticles();
                view.loadList(articles, storedArticles, self);
            }

            @Override
            public void onFailure(@NonNull Call<TopHeadlinesResponse> call, @NonNull Throwable t) {
                view.hideListLoading();
                view.showListError(new Exception(t));
            }
        });
    }

    @Override
    public void loadListWithProgressDialog() {
        view.showListLoading();
        loadList();
    }

    @Override
    public void loadListFromSwipeRefresh() {
        loadList();
    }

    @Override
    public void loadEmptyList() {
        view.loadEmptyList();
    }

    @Override
    public View.OnClickListener addOrDeleteArticle(final Article article, final boolean isStored) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleRepository repo = dependencyInjector.getArticleRepository();
                if (isStored) repo.delete(article);
                else repo.insert(article);
                loadList();
            }
        };
    }

    @Override
    public void navigateToWebView(Article article) {
        view.navigateToWebView(article);
    }


    @Override
    public void onDestroy() {
        this.view = null;
    }
}
